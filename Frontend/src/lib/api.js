import { cookies } from "next/headers";

const API_URL = process.env.API_URL;

/**
 * This file is used by the server side components like pages which are seen in Next js and this file attaches the cookie token to all calls to the backend
 */

async function apiFetch(path, options = {}) {
  const cookieStore = cookies();
  const token = (await cookieStore).get("jwt")?.value;

  const res = await fetch(`${API_URL}${path}`, {
    ...options,
    headers: {
      "Content-Type": "application/json",
      ...(token ? { Cookie: `jwt=${token}` } : {}),
      ...(options.headers || {}),
    },
    // This line disables caching so that fresh data is always gathered
    next: { revalidate: 0 },
  });

  if (!res.ok) {
    throw new Error(`API error ${res.status} on ${path}`);
  }

  return res.json();
}

export const getApplications = () => apiFetch("/applications");

export const getApplication = (id) => apiFetch("/applications/${id}");
