import useAuth from "@/hooks/use-auth";
import { GalleryVerticalEnd } from "lucide-react";
import { useEffect } from "react";
import { Navigate, Outlet } from "react-router-dom";

const UserInfoInputForm = () => {
  const { auth, refresh } = useAuth();

  useEffect(() => {
    refresh();
  }, []);

  if (auth?.user) {
    return <Navigate to="/home/analytics" replace />;
  }

  return (
    <div className="bg-muted flex min-h-svh flex-col items-center justify-center gap-6 p-6 md:p-10">
      <div className="flex w-full max-w-sm flex-col gap-6">
        <a href="#" className="flex items-center gap-2 self-center font-medium">
          <div className="bg-primary text-primary-foreground flex size-6 items-center justify-center rounded-md">
            <GalleryVerticalEnd className="size-4" />
          </div>
          xTemp inc.
        </a>
        <Outlet />
      </div>
    </div>
  );
};

export default UserInfoInputForm;
