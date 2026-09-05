"use client";

import { useState } from "react";
import { useRouter } from "next/navigation";
import { createApplication } from "../lib/clientApi";

const EMPTY_APPLICATION = {
  company: "",
  role: "",
  location: "",
  jobUrl: "",
  jobDescription: "",
  notes: "",
};

export default function NewApplicationForm() {
  const router = useRouter();
  const [form, setForm] = useState(EMPTY_APPLICATION);
  const [error, setError] = useState("");
  const [loading, setLoading] = useState(false);

  const handleChange = (e) => {
    setForm({ ...form, [e.target.name]: e.target.value });
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    setLoading(true);
    setError("");
    try {
      const { data } = await createApplication(form);
      router.push(`/applications/${data.id}`);
    } catch (err) {
      setError(err.response?.data?.message || "Failed to create application.");
    } finally {
      setLoading(false);
    }
  };

  return (
    <form onSubmit={handleSubmit} className="application-form">
      {error && <p className="error-msg">{error}</p>}

      <label htmlFor="company">Company</label>
      <input
        id="company"
        name="company"
        value={form.company}
        onChange={handleChange}
        required
        autoFocus
      />

      <label htmlFor="role">Role</label>
      <input
        id="role"
        name="role"
        value={form.role}
        onChange={handleChange}
        required
      />

      <label htmlFor="location">Location</label>
      <input
        id="location"
        name="location"
        value={form.location}
        onChange={handleChange}
      />

      <label htmlFor="jobUrl">Job posting URL</label>
      <input
        id="jobUrl"
        name="jobUrl"
        value={form.jobUrl}
        onChange={handleChange}
      />

      <label htmlFor="jobDescription">Job description</label>
      <textarea
        id="jobDescription"
        name="jobDescription"
        rows={10}
        value={form.jobDescription}
        onChange={handleChange}
        placeholder="Paste the full job description here — used for AI matching."
      />

      <label htmlFor="notes">Notes</label>
      <textarea
        id="notes"
        name="notes"
        rows={3}
        value={form.notes}
        onChange={handleChange}
      />

      <button type="submit" disabled={loading}>
        {loading ? "Saving..." : "Save application"}
      </button>
    </form>
  );
}
