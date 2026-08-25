import { getApplications } from "../../lib/api";
import KanbanBoard from "../../components/KanbanBoard";

//async used because this page runs on the server and the data is fetched before the html get sents to the browser

export default async function DashboardPage() {
  let applications = [];
  let error = "";

  try {
    applications = await getApplications();
  } catch {
    error = "Could not load applications. Is the Spring Boot backend running?";
  }

  return (
    <div className="page">
      <h1 className="page-title">Application Pipeline</h1>
      {error ? (
        <p className="error-msg">{error}</p>
      ) : (
        <KanbanBoard applications={applications} />
      )}
    </div>
  );
}
