import { redirect } from "next/navigation";

// Visiting / redirects immediately to /dashboard.
// Middleware will then redirect to /login if unauthenticated.
export default function Home() {
  redirect("/dashboard");
}
