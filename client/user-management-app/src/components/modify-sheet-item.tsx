import { useState } from "react";
import { PencilIcon } from "lucide-react";
import { z } from "zod";
import { useForm } from "react-hook-form";
import { zodResolver } from "@hookform/resolvers/zod";
import {
  Sheet,
  SheetContent,
  SheetHeader,
  SheetTitle,
  SheetDescription,
  SheetTrigger,
  SheetFooter,
  SheetClose,
} from "@/components/ui/sheet";
import { Input } from "@/components/ui/input";
import { Label } from "@/components/ui/label";
import { Button } from "@/components/ui/button";
import { DropdownMenuItem } from "./ui/dropdown-menu";
import type { User } from "@/pages/home/user/columns";
import { Switch } from "@/components/ui/switch";
import { Form, FormField, FormMessage } from "@/components/ui/form";

const modifyUserSchema = z.object({
  username: z.string().min(4, "Username must be at least 4 characters."),
  password: z.string().min(8, "Password must be at least 8 characters."),
  disabled: z.boolean(),
});

type ModifyFormValues = z.infer<typeof modifyUserSchema>;

export type ModifyUserProp = {
  username: string;
  password: string;
  disabled: boolean;
};

type ModifySheetProps = {
  user: User;
  onModify: (userId: string, user: ModifyUserProp) => void;
  closeMenu: () => void;
};

const ModifySheetItem = ({ user, onModify, closeMenu }: ModifySheetProps) => {
  const [open, setOpen] = useState(false);

  const form = useForm<ModifyFormValues>({
    resolver: zodResolver(modifyUserSchema),
    defaultValues: {
      username: user.username,
      password: user.password,
      disabled: user.disabled,
    },
  });

  const handleSave = async (values: ModifyFormValues) => {
    onModify(user.id, values);
    setOpen(false);
    closeMenu();
  };

  return (
    <Sheet
      open={open}
      onOpenChange={(o) => {
        setOpen(o);
        if (!o) closeMenu();
      }}
    >
      <SheetTrigger asChild>
        <DropdownMenuItem onSelect={(e) => e.preventDefault()}>
          <PencilIcon className="h-4 w-4" />
          Modify
        </DropdownMenuItem>
      </SheetTrigger>

      <SheetContent side="right" className="w-[400px] sm:w-[540px]">
        <SheetHeader>
          <SheetTitle>Edit User</SheetTitle>
          <SheetDescription>
            Make changes to the user here. Click save when you're done.
          </SheetDescription>
        </SheetHeader>

        <Form {...form}>
          <form
            onSubmit={form.handleSubmit(handleSave)}
            className="grid flex-1 auto-rows-min gap-6 px-4 py-6"
          >
            <div className="grid gap-3">
              <Label htmlFor="user-id">ID</Label>
              <Input id="user-id" value={user.id} disabled />
            </div>

            <FormField
              control={form.control}
              name="username"
              render={({ field }) => (
                <div className="grid gap-3">
                  <Label htmlFor="username">Username</Label>
                  <Input id="username" {...field} />
                  <FormMessage />
                </div>
              )}
            />

            <div className="grid gap-3">
              <Label htmlFor="role">Role</Label>
              <Input id="role" value={user.role} disabled />
            </div>

            <FormField
              control={form.control}
              name="password"
              render={({ field }) => (
                <div className="grid gap-3">
                  <Label htmlFor="password">Password</Label>
                  <Input id="password" type="text" {...field} />
                  <FormMessage />
                </div>
              )}
            />

            <FormField
              control={form.control}
              name="disabled"
              render={({ field }) => (
                <div className="grid gap-3">
                  <Label htmlFor="disabled">Disabled</Label>
                  <Switch id="disabled" checked={field.value} onCheckedChange={field.onChange} />
                  <FormMessage />
                </div>
              )}
            />

            <div className="grid gap-3">
              <Label htmlFor="createdAt">Created At</Label>
              <Input id="createdAt" value={user.createdAt} disabled />
            </div>

            <div className="grid gap-3">
              <Label htmlFor="updatedAt">Updated At</Label>
              <Input id="updatedAt" value={user.updatedAt} disabled />
            </div>

            <SheetFooter>
              <Button type="submit">Save Changes</Button>
              <SheetClose asChild>
                <Button variant="outline">Close</Button>
              </SheetClose>
            </SheetFooter>
          </form>
        </Form>
      </SheetContent>
    </Sheet>
  );
};

export default ModifySheetItem;
