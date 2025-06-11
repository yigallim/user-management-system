import { Badge } from "@/components/ui/badge";
import { BadgeCheckIcon, BanIcon } from "lucide-react";

type StatusCellProps = {
  disabled: boolean;
};

const StatusCell = ({ disabled }: StatusCellProps) => {
  return disabled ? (
    <Badge variant="destructive" className="flex items-center gap-1">
      <BanIcon className="h-4 w-4" />
      Disabled
    </Badge>
  ) : (
    <Badge
      variant="secondary"
      className="flex items-center gap-1 bg-blue-500 text-white dark:bg-blue-600"
    >
      <BadgeCheckIcon className="h-4 w-4" />
      Active
    </Badge>
  );
};

export default StatusCell;
