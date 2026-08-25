import ApplicationCard from "./ApplicationCard";

const COLUMNS = ["SAVED", "APPLIED", "INTERVIEW", "OFFER", "REJECTED"];
const LABELS = {
  SAVED: "Saved",
  APPLIED: "Applied",
  INTERVIEW: "Interview",
  OFFER: "Offer",
  REJECTED: "Rejected",
};

// Server Component — no interactivity needed, just display
export default function KanbanBoard({ applications }) {
  return (
    <div className="kanban-board">
      {COLUMNS.map((status) => {
        const column = applications.filter((a) => a.status === status);
        return (
          <div key={status} className="kanban-column">
            <h2 className="kanban-column-title">
              {LABELS[status]} ({column.length})
            </h2>
            <div className="kanban-column-cards">
              {column.map((app) => (
                <ApplicationCard key={app.id} application={app} />
              ))}
              {column.length === 0 && (
                <p className="kanban-empty">Nothing here yet.</p>
              )}
            </div>
          </div>
        );
      })}
    </div>
  );
}
