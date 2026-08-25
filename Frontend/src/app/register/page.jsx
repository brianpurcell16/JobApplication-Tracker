import RegisterForm from "../../components/RegisterForm";

export default function RegisterPage() {
  return (
    <div className="auth-page">
      <div className="auth-card">
        <h1> Job Tracker</h1>
        <h2>Create account</h2>
        <RegisterForm />
      </div>
    </div>
  );
}
