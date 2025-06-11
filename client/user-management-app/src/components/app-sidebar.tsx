import * as React from "react";
import { BarChart, GalleryVerticalEnd, Users } from "lucide-react";

import { TeamSwitcher } from "@/components/team-switcher";
import { NavMain } from "@/components/nav-main";
import { NavUser } from "@/components/nav-user";
import {
  Sidebar,
  SidebarContent,
  SidebarFooter,
  SidebarHeader,
  SidebarRail,
} from "@/components/ui/sidebar";
import useAuth from "@/hooks/use-auth";

const data = {
  teams: [
    {
      name: "xTemp inc.",
      logo: GalleryVerticalEnd,
      plan: "Enterprise",
    },
  ],
  navMain: [
    {
      title: "Analytics",
      url: "/home/analytics",
      icon: BarChart,
    },
    {
      title: "Users",
      url: "/home/users",
      icon: Users,
    },
  ],
};

export function AppSidebar({ ...props }: React.ComponentProps<typeof Sidebar>) {
  const { auth } = useAuth();

  return (
    <Sidebar collapsible="icon" {...props}>
      <SidebarHeader>
        <TeamSwitcher teams={data.teams} />
      </SidebarHeader>
      <SidebarContent>
        <NavMain items={data.navMain} />
      </SidebarContent>
      <SidebarFooter>
        <NavUser user={auth.user!} />
      </SidebarFooter>
      <SidebarRail />
    </Sidebar>
  );
}
