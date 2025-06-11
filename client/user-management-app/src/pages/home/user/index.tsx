import React from "react";
import { useQuery, useQueryClient } from "@tanstack/react-query";
import { DataTable } from "./data-table";
import { getUserColumns } from "./columns";
import type { PaginationState } from "@tanstack/react-table";
import { deleteUser, getPagedUsers, updateUser } from "@/api/user";
import { toast } from "sonner";
import type { ModifyUserProp } from "@/components/modify-sheet-item";

const UserComponent = () => {
  const queryClient = useQueryClient();
  const [pagination, setPagination] = React.useState<PaginationState>({
    pageIndex: 0,
    pageSize: 10,
  });

  const { data, isLoading } = useQuery({
    queryKey: ["users", pagination],
    queryFn: () => getPagedUsers(pagination.pageIndex, pagination.pageSize),
    staleTime: 60 * 1000,
  });

  const onModify = React.useCallback(async (userId: string, newUser: ModifyUserProp) => {
    console.log("newUser", newUser);
    const response = await updateUser(userId, newUser);
    if (response.data.status === "success") {
      queryClient.invalidateQueries({ queryKey: ["users"] });
      toast.success("User updated successfully", {
        position: "top-right",
        duration: 5000,
        closeButton: true,
      });
    }
  }, []);

  const onDelete = React.useCallback(async (userId: string) => {
    const response = await deleteUser(userId);
    if (response.data.status === "success") {
      queryClient.invalidateQueries({ queryKey: ["users"] });
      toast.success("User deleted successfully", {
        position: "top-right",
        duration: 5000,
        closeButton: true,
      });
    }
  }, []);

  const columns = React.useMemo(
    () =>
      getUserColumns({
        onModify,
        onDelete,
      }),
    [onModify, onDelete]
  );
  return (
    <DataTable
      columns={columns}
      data={data?.data.data.rows ?? []}
      rowCount={data?.data.data.count ?? 0}
      pagination={pagination}
      setPagination={setPagination}
      isLoading={isLoading}
    />
  );
};

export default UserComponent;
