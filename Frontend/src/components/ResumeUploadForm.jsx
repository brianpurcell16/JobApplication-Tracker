"use client";

import { useState } from "react";
import { uploadResume } from "../lib/clientApi";

export default function ResumeUploadForm() {
  const [file, setFile] = useState(null);
  const [status, setStatus] = useState("");
  const [loading, setLoading] = useState(false);

  const handleSubmit = async (e) => {
    e.preventDefault();
    if (!file) {
      setStatus("Please select a file to upload.");
      return;
    }

    setLoading(true);
    setStatus("");

    try {
      const { data } = await uploadResume(file);
      setStatus(
        `Resume with the name "${data.originalFilename}" uploaded successfully.`,
      );
    } catch (error) {
      setStatus("Error uploading resume.");
    } finally {
      setLoading(false);
    }
  };

  return (
    <form onSubmit={handleSubmit} className="application-form">
      <input
        type="file"
        accept="application/pdf"
        onChange={(e) => setFile(e.target.files[0])}
      />
      <button type="submit" disabled={!file || loading}>
        {loading ? "Uploading..." : "Upload Resume"}
      </button>
      {status && <p className="upload-status">{status}</p>}
    </form>
  );
}
