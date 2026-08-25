import { getApplication } from "../../../lib/api";
import ApplicationDetailClient from "../../../components/ApplicationDetailClient";
import MatchResultPanel from "../../../components/MatchResultPanel";

//params.id comes from the dynamic route [id] in the file path
export default async function ApplicationDetailPage({ params }) {
  let application;

  try {
    application = await getApplication(params.id);
  } catch (error) {
    console.error("Error fetching application:", error);
    return (
      <div className="page">
        <p className="error-msg">Application not found.</p>
      </div>
    );
  }

  return (
    <div className="page page-narrow">
      <h1 className="page-title">
        {application.role} at {application.company}
      </h1>

      {/* Client Component handles the status dropdown, delete, and AI match button */}
      <ApplicationDetailClient application={application} />

      {/* Display cached match result if one exists */}
      {application.matchScore != null && (
        <section className="detail-section">
          <h2>Last AI Match Result</h2>
          <MatchResultPanel
            result={{
              score: application.matchScore,
              summary: application.matchSummary,
              strengths: (application.matchStrengths || "")
                .split("\n")
                .filter(Boolean),
              gaps: (application.matchGaps || "").split("\n").filter(Boolean),
            }}
          />
        </section>
      )}
    </div>
  );
}
