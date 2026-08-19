"use client";

import Link from "next/link";
import { useRouter, usePathname } from "next/navigation";
import { logout } from "../lib/clientApi";

export default function Navbar() {
  const router = useRouter();
  const pathname = usePathname();

  //this stops the navbar from showing on login and register pages
  if (pathname === "/login" || pathname === "/register") {
    return null;
  }

  const handleLogout = async () => {
    await logout();
    router.push("/login");
  };

  const isActive = (path) => (pathname === path ? "active" : "");

  return (
    <nav className="navbar">
      <Link href="/dashboard" className="navbar-brand">
        Job Tracker
      </Link>
      <div className="navbar-links">
        <Link href="/dashboard" className={isActive("/dashboard")}>
          Dashboard
        </Link>
        <Link href="/new" className={isActive("/new")}>
          New Application
        </Link>
        <Link href="/resume" className={isActive("/resume")}>
          Resume
        </Link>
        <button onClick={handleLogout} className="btn-logout">
          Log out
        </button>
      </div>
    </nav>
  );
}
