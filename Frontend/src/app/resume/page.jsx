import ResumeUploadForm from "@/components/ResumeUploadForm";

export default function ResumePage() {
  return (
    <div className="page page-narrow">
      <h1 className="page-title">Your Resume</h1>
      <p>
        Please upload your resume in PDF format. This will be used by AI to
        compare your background with every application you upload and determine
        how well you fit by getting a score.
      </p>
      <ResumeUploadForm />
    </div>
  );
}
