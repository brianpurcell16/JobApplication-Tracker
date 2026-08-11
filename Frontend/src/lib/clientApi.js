import axios from "axios";

/**
 * This file iss used by client side components to do things like create, update and delete
 * withCredentials: true is used to tell the browser to always include the cookie
 */

const clientApi = axios.create({
  baseURL: process.env.NEXT_PUBLIC_API_URL,
  withCredentials: true,
});

export const login = (username, password) =>
  clientApi.post("/auth/login", { username, password });

export const register = (username, email, password) =>
  clientApi.post("/auth/register", { username, email, password });

export const logout = () => clientApi.post("/auth/logout");

export const createApplication = (data) =>
  clientApi.post("/applications", data);

export const updateApplication = (id, data) =>
  clientApi.put(`/applications/${id}`, data);

export const deleteApplication = (id) =>
  clientApi.delete(`/applications/${id}`);

export const runMatch = (applicationId) =>
  clientApi.post(`/match/${applicationId}`);

export const uploadResume = (file) => {
  const formData = new FormData();
  formData.append("file", file);
  return clientApi.post("/resume", formData, {
    headers: { "Content-Type": "multipart/form-data" },
  });
};
