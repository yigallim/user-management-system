import axios from "axios";
import { toast } from "sonner";
import router from "@/router";

const request = axios.create({
  baseURL: "http://localhost:8080",
  timeout: 1000 * 60,
});

let isRefreshing = false;
let failedQueue: Array<{ resolve: (value: any) => void; reject: (reason?: any) => void }> = [];

const processQueue = (error: Error | null, token: string | null = null) => {
  failedQueue.forEach((prom) => {
    if (error) {
      prom.reject(error);
    } else if (token) {
      prom.resolve(token);
    }
  });
  failedQueue = [];
};

request.interceptors.request.use(
  (config) => {
    const auth = localStorage.getItem("auth");
    if (auth) {
      const parsedAuth = JSON.parse(auth);
      if (parsedAuth.accessToken) {
        config.headers.Authorization = `Bearer ${parsedAuth.accessToken}`;
      }
    }
    return config;
  },
  (error) => {
    return Promise.reject(error);
  }
);

request.interceptors.response.use(
  (response) => {
    if (response.data?.status === "error") {
      const errorMessage = response.data.message || "An error occurred";
      toast.error(errorMessage, {
        position: "top-right",
        duration: 5000,
        closeButton: true,
      });
      return Promise.reject(response);
    }
    return response;
  },
  async (error) => {
    const originalRequest = error.config;

    if (
      error.response &&
      error.response.status === 401 &&
      error.response.data?.code === "unauthorized" &&
      error.response.data?.message === "Invalid or expired token" &&
      !originalRequest._retry
    ) {
      if (isRefreshing) {
        return new Promise(function (resolve, reject) {
          failedQueue.push({ resolve, reject });
        })
          .then((token) => {
            originalRequest.headers["Authorization"] = "Bearer " + token;
            return request(originalRequest);
          })
          .catch((err) => {
            return Promise.reject(err);
          });
      }

      originalRequest._retry = true;
      isRefreshing = true;

      try {
        const refreshResponse = await axios.post("http://localhost:8080/auth/refresh-token", null, {
          withCredentials: true,
        });

        if (refreshResponse.data.status === "success") {
          const newAccessToken = refreshResponse.data.data.accessToken;
          const newUser = refreshResponse.data.data.user;

          localStorage.setItem(
            "auth",
            JSON.stringify({ accessToken: newAccessToken, user: newUser })
          );
          originalRequest.headers["Authorization"] = `Bearer ${newAccessToken}`;

          processQueue(null, newAccessToken);
          return request(originalRequest);
        } else {
          localStorage.removeItem("auth");

          toast.error("Session expired. Please log in again.", {
            position: "top-right",
            duration: 5000,
            closeButton: true,
          });
          router.navigate("/login", { replace: true });
          return Promise.reject(error);
        }
      } catch (refreshError: any) {
        localStorage.removeItem("auth");
        processQueue(refreshError);
        toast.error(
          refreshError.response?.data?.message || "Session expired. Please log in again.",
          {
            position: "top-right",
            duration: 5000,
            closeButton: true,
          }
        );
        router.navigate("/login", { replace: true });
        return Promise.reject(refreshError);
      } finally {
        isRefreshing = false;
      }
    }

    let errorMessage = "Network error. Please check your connection.";
    if (error.response) {
      if (error.response.data?.message) {
        errorMessage = error.response.data.message;
      } else {
        errorMessage = `HTTP Error: ${error.response.status}`;
      }
    } else if (error.request) {
      if (error.code === "ECONNABORTED") {
        errorMessage = "Request timeout. Please try again.";
      } else {
        errorMessage = "Network error. Please check your connection.";
      }
    } else {
      errorMessage = error.message || "Request configuration error";
    }

    toast.error(errorMessage, {
      position: "top-right",
      duration: 5000,
      closeButton: true,
    });

    return Promise.reject(error);
  }
);

export default request;
