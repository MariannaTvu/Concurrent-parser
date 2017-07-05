package com.mariana.presentation;

import java.util.Objects;

/**
 * @author nicot
 */
public class JobIdDto {

    private final String jobId;

    public JobIdDto(String jobId) {
        this.jobId = jobId;
    }

    public String getJobId() {
        return jobId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        JobIdDto jobIdDto = (JobIdDto) o;
        return Objects.equals(jobId, jobIdDto.jobId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(jobId);
    }

    @Override
    public String toString() {
        return "JobIdDto{" +
                "jobId='" + jobId + '\'' +
                '}';
    }
}
