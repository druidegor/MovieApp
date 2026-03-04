from fastapi import FastAPI, Depends
from app.dependencies.auth import get_current_user

from app.api.v1 import users

app = FastAPI(title="MovieApp")

app.include_router(users.router)

@app.get("/")
async def f(usr = Depends(get_current_user)):
    pass