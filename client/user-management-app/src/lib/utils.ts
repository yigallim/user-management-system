import { clsx, type ClassValue } from "clsx";
import { twMerge } from "tailwind-merge";

export function cn(...inputs: ClassValue[]) {
  return twMerge(clsx(inputs));
}

export function formatDateTime(isoString: string) {
  const date = new Date(isoString);
  return date.toLocaleString();
}

export function delay(ms: number) {
  return new Promise((resolve) => setTimeout(resolve, ms));
}
