from fastapi import APIRouter, Depends, HTTPException, status
from fastapi.security import OAuth2PasswordRequestForm
from sqlalchemy.ext.asyncio import AsyncSession
from sqlalchemy import select
import jwt

from app.schemes.users import UserRead, UserCreate, RefreshRequest
from app.db.dependencies import get_async_db
from app.core.security import hash_password, verify_password, create_access_token, create_refresh_token
from app.models.users import User
from app.core.config import settings

router = APIRouter(prefix="/user", tags=["user"])

@router.post("/", response_model=UserRead, status_code=status.HTTP_201_CREATED)
async def create_user(user_data: UserCreate, db: AsyncSession = Depends(get_async_db)):
    user = await db.scalar(select(User).where(User.email == user_data.email))
    if user:
        raise HTTPException(status_code=status.HTTP_400_BAD_REQUEST,
                            detail="Email already registered")

    db_user = User(
        username=user_data.username,
        email=user_data.email,
        hashed_password=hash_password(user_data.password),
        role="user"
    )

    db.add(db_user)
    await db.commit()
    await db.refresh(db_user)
    return db_user

@router.post("/login")
async def login(form_data: OAuth2PasswordRequestForm = Depends(),
                db: AsyncSession = Depends(get_async_db)):

    user = await db.scalar(select(User).where(User.email == form_data.username, User.is_active == True))
    if not user or not verify_password(form_data.password, user.hashed_password):
        raise HTTPException(
            status_code=status.HTTP_401_UNAUTHORIZED,
            detail="Incorrect email or password",
            headers={"WWW-Authenticate": "Bearer"},
        )
    user_data = {"sub": str(user.id), "role": user.role}
    access_token = create_access_token(data=user_data)
    refresh_token = create_refresh_token(data=user_data)
    return {"access_token": access_token,"refresh_token": refresh_token, "token_type": "bearer"}


@router.post("/refresh-token")
async def refresh_token(refr_data: RefreshRequest, db: AsyncSession = Depends(get_async_db)):
    """
    Обновляет access токен при помощи refresh токена.
    """
    credentials_exception = HTTPException(
        status_code=status.HTTP_401_UNAUTHORIZED,
        detail="Could not validate refresh token",
        headers={"WWW-Authenticate": "Bearer"},
    )
    try:
        payload = jwt.decode(refr_data.refresh_token, settings.SECRET_KEY, algorithms=[settings.ALGORITHM])
        if payload.get("type") != "refresh":
            raise credentials_exception
        id_ = payload.get("sub")
        if id_ is None:
            raise credentials_exception
    except jwt.PyJWTError:
        raise credentials_exception
    user = await db.scalar(select(User).where(User.id == int(id_), User.is_active == True))
    if user is None:
        raise credentials_exception
    access_token = create_access_token(data={"sub": str(user.id), "role": user.role})
    return {"access_token": access_token, "token_type": "bearer"}