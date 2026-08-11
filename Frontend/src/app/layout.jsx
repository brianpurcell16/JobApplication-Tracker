import Navbar from "../components/Navbar";
import "../styles/global.css";

export const metadata = {
  title: "Job Application Tracker",
  description: "AI-powered job application tracker",
};

// Root layout — Server Component.
// Wraps every page. Navbar is rendered here so it appears on all routes.
export default function RootLayout({ children }) {
  return (
    <html lang="en">
      <body>
        <Navbar />
        <main className="main-content">{children}</main>
      </body>
    </html>
  );
}
