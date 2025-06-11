import { Checkbox } from "@/components/ui/checkbox";
import type { ColumnDef, Row } from "@tanstack/react-table";
import ActionCell from "./cell/action-cell";
import PasswordCell from "./cell/password-cell";
import RoleCell from "./cell/role-cell";
import StatusCell from "./cell/status-cell";
import { formatDateTime } from "@/lib/utils";
import type { ModifyUserProp } from "@/components/modify-sheet-item";
import type { Role } from "@/context/auth-provider";

export type User = {
  id: string;
  username: string;
  password: string;
  role: "admin" | "user";
  disabled: boolean;
  createdAt: string;
  updatedAt: string;
};

type UserColumnsProps = {
  onModify: (userId: string, newUser: ModifyUserProp) => void;
  onDelete: (userId: string) => void;
};

export const getUserColumns = ({ onModify, onDelete }: UserColumnsProps): ColumnDef<User>[] => [
  {
    id: "select",
    header: ({ table }) => (
      <Checkbox
        checked={
          table.getIsAllPageRowsSelected() || (table.getIsSomePageRowsSelected() && "indeterminate")
        }
        onCheckedChange={(value) => table.toggleAllPageRowsSelected(!!value)}
        aria-label="Select all"
      />
    ),
    cell: ({ row }) => (
      <Checkbox
        className="cursor-pointer"
        checked={row.getIsSelected()}
        onCheckedChange={(value) => row.toggleSelected(!!value)}
        aria-label="Select row"
      />
    ),
    enableSorting: false,
    enableHiding: false,
  },
  {
    accessorKey: "id",
    header: "ID",
  },
  {
    accessorKey: "username",
    header: "Username",
  },
  {
    accessorKey: "password",
    header: "Password",
    cell: ({ getValue }) => <PasswordCell value={getValue() as string} />,
  },
  {
    accessorKey: "role",
    header: "Role",
    cell: ({ getValue }) => <RoleCell role={getValue() as Role} />,
  },
  {
    accessorKey: "disabled",
    header: "Status",
    cell: ({ getValue }) => <StatusCell disabled={getValue() as boolean} />,
  },
  {
    accessorKey: "createdAt",
    header: "Created At",
    cell: ({ getValue }) => formatDateTime(getValue() as string),
  },
  {
    accessorKey: "updatedAt",
    header: "Updated At",
    cell: ({ getValue }) => formatDateTime(getValue() as string),
  },
  {
    id: "actions",
    enableHiding: false,
    cell: ({ row }) => (
      <ActionCell row={row as Row<User>} onDelete={onDelete} onModify={onModify} />
    ),
  },
];
