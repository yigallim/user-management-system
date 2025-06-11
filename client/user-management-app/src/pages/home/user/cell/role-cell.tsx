import { Badge } from "@/components/ui/badge";
import type { Role } from "@/context/auth-provider";
import { ShieldIcon, UserIcon } from "lucide-react";

type RoleCellProps = {
  role: Role;
};

const RoleCell = ({ role }: RoleCellProps) => {
  return role === "admin" ? (
    <Badge className="flex items-center gap-1">
      <ShieldIcon className="h-4 w-4" />
      Admin
    </Badge>
  ) : (
    <Badge variant="secondary" className="flex items-center gap-1">
      <UserIcon className="h-4 w-4" />
      User
    </Badge>
  );
};

export default RoleCell;
