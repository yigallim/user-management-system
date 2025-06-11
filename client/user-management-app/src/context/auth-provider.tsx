import { createContext, useState, useEffect } from "react";

export type Role = "admin" | "user";

export type User = {
  id: string;
  username: string;
  role: Role;
  createdAt: string;
  updatedAt: string;
};

export type AuthProps = {
  accessToken?: string;
  user?: User;
  refreshToken?: string;
};

export type AuthContextType = {
  auth: AuthProps;
  setAuth: React.Dispatch<React.SetStateAction<AuthProps>>;
  refresh: () => void;
};

export const AuthContext = createContext<AuthContextType>({
  auth: {},
  setAuth: () => {},
  refresh: () => {},
});

type AuthProviderProps = {
  children: React.ReactNode;
};

const AuthProvider = ({ children }: AuthProviderProps) => {
  const [auth, setAuth] = useState<AuthProps>(() => {
    try {
      const storedAuth = localStorage.getItem("auth");
      return storedAuth ? JSON.parse(storedAuth) : {};
    } catch (error) {
      console.error("Failed to parse auth from localStorage:", error);
      return {};
    }
  });

  useEffect(() => {
    try {
      localStorage.setItem("auth", JSON.stringify(auth));
    } catch (error) {
      console.error("Failed to save auth to localStorage:", error);
    }
  }, [auth]);

  const refresh = () => {
    try {
      const storedAuth = localStorage.getItem("auth");
      const parsed = storedAuth ? JSON.parse(storedAuth) : {};
      setAuth(parsed);
    } catch (error) {
      console.error("Failed to refresh auth from localStorage:", error);
      setAuth({});
    }
  };

  return <AuthContext.Provider value={{ auth, setAuth, refresh }}>{children}</AuthContext.Provider>;
};

export default AuthProvider;
