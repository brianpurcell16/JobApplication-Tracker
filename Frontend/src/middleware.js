import { NextResponse } from "next/server";

/**
 * This file is used to check the cookie before any data is displayed and if the cookie isnt present it will return the user to the login page
 */

export function middleware(request) {
  const token = request.cookies.get("jwt");

  if (!token) {
    const loginUrl = new URL("/login", request.url);
    return NextResponse.redirect(loginUrl);
  }

  return NextResponse.next();
}

//login and register are not protected routes as they are needed for users to use to access the protected routes once they are authenicated

export const config = {
  matcher: ["/dashboard", "/applications/:path*", "/new", "/resume"],
};
