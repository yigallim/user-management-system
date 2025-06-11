import type { ModifyUserProp } from "@/components/modify-sheet-item";
import request from "./api";

export const getUsers = () => {
  return request.get("/user");
};

export const getPagedUsers = (pageIndex: number, pageSize: number) => {
  return request.get(`/user/paged?pageIndex=${pageIndex + 1}&pageSize=${pageSize}`);
};

export const deleteUser = (userId: string) => {
  return request.delete(`/user/${userId}`);
};

export const updateUser = (userId: string, newUser: ModifyUserProp) => {
  console.log("newUser", newUser);
  return request.put(`/user/${userId}`, newUser);
};
