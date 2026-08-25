import Link from "next/link";

// Server Component
function scoreColour(score) {
  if (score == null) {
    return "score-badge--none";
  } else if (score >= 75) {
    return "score-badge--high";
  } else if (score >= 50) {
    return "score-badge--mid";
  } else {
    return "score-badge--low";
  }
}

export default function ApplicationCard({ application }) {
  const { id, company, role, location, status, matchScore } = application;

  return (
    <Link href={`/applications/${id}`} className="application-card">
      <div className="application-card-header">
        <h3>{role}</h3>
        <span className={`score-badge ${scoreColour(matchScore)}`}>
          {matchScore != null ? `${matchScore}%` : "Not scored yet"}
        </span>
      </div>
      <p className="application-company">{company}</p>
      {location && <p className="application-meta">{location}</p>}
      <span className={`status-badge status--${status.toLowerCase()}`}>
        {status}
      </span>
    </Link>
  );
}
