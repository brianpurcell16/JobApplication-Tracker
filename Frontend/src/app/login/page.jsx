//This file is a standard pratice when using next js as the page is just a server component with not much content but then it imports a client component like the login form imported in this file
import LoginForm from "../../components/LoginForm";

//this is the shell that renders the client form inside it
export default function LoginPage() {
  return (
    <div className="auth-page">
      <div className="auth-card">
        <h1> Job Tracker</h1>
        <h2>Sign in</h2>
        <LoginForm />
      </div>
    </div>
  );
}
