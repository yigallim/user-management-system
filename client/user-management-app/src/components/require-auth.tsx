import { Navigate, Outlet, useLocation } from "react-router-dom";
import useAuth from "@/hooks/use-auth";
import { useEffect } from "react";

const RequireAuth = () => {
  const { auth, refresh } = useAuth();
  const location = useLocation();

  useEffect(() => {
    refresh();
  }, []);

  return auth?.user ? <Outlet /> : <Navigate to="/login" state={{ from: location }} replace />;
};

export default RequireAuth;
