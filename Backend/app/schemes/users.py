from pydantic import BaseModel, Field, EmailStr, ConfigDict
from datetime import datetime

class UserCreate(BaseModel):
    username: str = Field(description="Username пользователя.", max_length=128, min_length=3)
    email: EmailStr = Field(description="Email пользователя.", max_length=256, min_length=5)
    password: str = Field(description="Пароль пользователя.", min_length=8, max_length=128)

class UserRead(BaseModel):
    id: int
    username: str
    email: str
    role: str
    created_at: datetime
    is_active: bool

    model_config = ConfigDict(from_attributes=True)

class RefreshRequest(BaseModel):
    refresh_token: str = Field(description="Refresh token.")