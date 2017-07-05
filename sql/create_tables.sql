CREATE TABLE value_count
(
  job_id VARCHAR(255) NOT NULL,
  value  VARCHAR(255) NOT NULL,
  count  INT          NOT NULL,
  PRIMARY KEY (job_id, value)
);

CREATE INDEX jobIdIndex
  ON value_count (job_id);
