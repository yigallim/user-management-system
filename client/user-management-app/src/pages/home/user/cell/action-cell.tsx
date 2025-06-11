import React from "react";
import ConfirmDeleteItem from "@/components/confirm-delete-item";
import ModifySheetItem, { type ModifyUserProp } from "@/components/modify-sheet-item";
import { Button } from "@/components/ui/button";
import {
  DropdownMenu,
  DropdownMenuContent,
  DropdownMenuLabel,
  DropdownMenuTrigger,
} from "@/components/ui/dropdown-menu";
import type { User } from "../columns";
import type { Row } from "@tanstack/react-table";
import { MoreHorizontal } from "lucide-react";

type ActionCellProps = {
  row: Row<User>;
  onModify: (userId: string, user: ModifyUserProp) => void;
  onDelete: (userId: string) => void;
};

const ActionCell = ({ row, onModify, onDelete }: ActionCellProps) => {
  const user = row.original;
  const [menuOpen, setMenuOpen] = React.useState(false);

  const closeMenu = () => {
    setMenuOpen(false);
  };

  return (
    <DropdownMenu open={menuOpen} onOpenChange={setMenuOpen}>
      <DropdownMenuTrigger asChild>
        <Button variant="ghost" className="h-8 w-8 p-0">
          <span className="sr-only">Open Actions Menu</span>
          <MoreHorizontal />
        </Button>
      </DropdownMenuTrigger>
      <DropdownMenuContent align="end">
        <DropdownMenuLabel>Actions</DropdownMenuLabel>
        <ModifySheetItem user={user} onModify={onModify} closeMenu={closeMenu} />
        <ConfirmDeleteItem onDelete={() => onDelete(user.id)} closeMenu={closeMenu} />
      </DropdownMenuContent>
    </DropdownMenu>
  );
};

export default ActionCell;
