from sqlalchemy.ext.asyncio import async_sessionmaker, create_async_engine, AsyncSession
from app.core.config import settings

engine = create_async_engine(settings.APP_DATABASE_URL)

async_session_maker = async_sessionmaker(bind=engine, expire_on_commit=False, class_=AsyncSession)