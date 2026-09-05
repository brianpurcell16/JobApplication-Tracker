"use client";

import { useState } from "react";
import { useRouter } from "next/navigation";
import {
  updateApplication,
  deleteApplication,
  runMatch,
} from "../lib/clientApi";
import MatchResultPanel from "./MatchResultPanel";

const STATUSES = ["Applied", "Interview", "Offered", "Rejected", "SAVED"];

export default function ApplicationDetailClient({ application }) {
  const router = useRouter();
  const [matchResult, setMatchResult] = useState(null);
  const [matching, setMatching] = useState(false);
  const [matchError, setMatchError] = useState("");

  const handleStatusChange = async (event) => {
    await updateApplication(application.id, {
      ...application,
      status: event.target.value,
    });
    router.refresh();
  };

  const handleDelete = async () => {
    if (confirm("Are you sure you want to delete this application?")) {
      await deleteApplication(application.id);
      router.push("/applications");
    }
  };

  const handleRunMatch = async () => {
    setMatching(true);
    setMatchError("");
    try {
      const { data } = await runMatch(application.id);
      setMatchResult(data);
    } catch (error) {
      setMatchError(error.response?.data?.message || "Failed to run match.");
    } finally {
      setMatching(false);
    }
  };

  return (
    <div>
      <div className="detail-meta-row">
        <label htmlFor="status">Status:</label>
        <select
          id="status"
          value={application.status}
          onChange={handleStatusChange}
        >
          {STATUSES.map((status) => (
            <option key={status} value={status}>
              {status}
            </option>
          ))}
        </select>
        <button onClick={handleDelete} className="btn btn-danger">
          Delete
        </button>
      </div>

      <section className="detail-section">
        <h2>Job description</h2>
        <p className="job-description-text">
          {application.jobDescription || "No job description available."}
        </p>
      </section>

      <section className="detail-section">
        <h2>Run Match</h2>
        <button
          onClick={handleRunMatch}
          disabled={matching}
          className="btn-primary"
        >
          {matching ? "Matching with Claude..." : "Run Match"}
        </button>
        {matchError && <p className="error-msg">{matchError}</p>}
        {matchResult && <MatchResultPanel matchResult={matchResult} />}
      </section>
    </div>
  );
}
