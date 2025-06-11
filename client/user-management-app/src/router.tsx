import { createBrowserRouter } from "react-router-dom";
import { Navigate } from "react-router-dom";
import RequireAuth from "@/components/require-auth";
import { LoginForm } from "@/pages/login-form";
import { SignupForm } from "@/pages/signup-form";
import Home from "@/pages/home";
import Analytics from "@/pages/home/analytics";
import User from "@/pages/home/user";
import UserInfoInputForm from "@/components/user-info-form";

const router = createBrowserRouter([
  {
    element: <UserInfoInputForm />,
    children: [
      { path: "/login", element: <LoginForm /> },
      { path: "/signup", element: <SignupForm /> },
    ],
  },
  {
    element: <RequireAuth />,
    children: [
      {
        path: "/home",
        element: <Home />,
        children: [
          { path: "analytics", element: <Analytics /> },
          { path: "users", element: <User /> },
        ],
      },
    ],
  },
  {
    path: "*",
    element: <Navigate to="/login" />,
  },
]);

export default router;
