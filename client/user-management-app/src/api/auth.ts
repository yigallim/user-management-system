import request from "./api";

type UserCredentials = {
  username: string;
  password: string;
};

export const login = (credentials: UserCredentials) => {
  return request.post("/auth/login", credentials, { withCredentials: true });
};

export const signup = (credentials: UserCredentials) => {
  return request.post("/auth/register", credentials, { withCredentials: true });
};

export const logout = () => {
  return request.post("/auth/logout", null, { withCredentials: true });
};
