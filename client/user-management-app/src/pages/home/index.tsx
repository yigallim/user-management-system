import { AppSidebar } from "@/components/app-sidebar";
import {
  Breadcrumb,
  BreadcrumbItem,
  BreadcrumbList,
  BreadcrumbPage,
  BreadcrumbSeparator,
} from "@/components/ui/breadcrumb";
import { Separator } from "@/components/ui/separator";
import { SidebarInset, SidebarProvider, SidebarTrigger } from "@/components/ui/sidebar";
import React from "react";
import { Outlet, useLocation } from "react-router-dom";

const Home = () => {
  const location = useLocation();
  const pathnames = location.pathname.split("/").filter(Boolean);

  return (
    <SidebarProvider>
      <AppSidebar />
      <SidebarInset className="flex-1 flex flex-col min-w-0">
        <header className="flex h-16 shrink-0 items-center gap-2 border-b px-4">
          <SidebarTrigger className="-ml-1" />
          <Separator orientation="vertical" className="mr-2 data-[orientation=vertical]:h-4" />
          <Breadcrumb>
            <BreadcrumbList>
              {pathnames.map((segment, index) => {
                const fullPath = "/" + pathnames.slice(0, index + 1).join("/");
                const formattedSegment = segment
                  .replace(/-/g, " ")
                  .replace(/\b\w/g, (c) => c.toUpperCase());

                return (
                  <React.Fragment key={fullPath}>
                    <BreadcrumbItem>
                      <BreadcrumbPage>{formattedSegment}</BreadcrumbPage>
                    </BreadcrumbItem>
                    {index < pathnames.length - 1 && <BreadcrumbSeparator />}
                  </React.Fragment>
                );
              })}
            </BreadcrumbList>
          </Breadcrumb>
        </header>

        <div className="p-4 flex-1 flex flex-col">
          <Outlet />
        </div>
      </SidebarInset>
    </SidebarProvider>
  );
};

export default Home;
