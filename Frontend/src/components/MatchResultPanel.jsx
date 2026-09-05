export default function MatchResultPanel({ matchResult }) {
  if (!matchResult) {
    return null;
  }
  const { score, summary, strengths, gaps } = matchResult;

  return (
    <div className="match-panel">
      <div className="match-panel-score">
        <span className="match-score-number">{score}</span>
        <span className="match-score-label">Match Score</span>
      </div>
      <p className="match-summary">{summary}</p>
      <div className="match-columns">
        <div className="match-column">
          <h3>Strengths</h3>
          <ul>
            {strengths.map((strength, index) => (
              <li key={index}>{strength}</li>
            ))}
          </ul>
        </div>
        <div className="match-column">
          <h3>Gaps</h3>
          <ul>
            {gaps.map((gap, index) => (
              <li key={index}>{gap}</li>
            ))}
          </ul>
        </div>
      </div>
    </div>
  );
}
