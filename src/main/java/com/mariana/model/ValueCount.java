package com.mariana.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Index;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import java.util.Objects;

import static com.mariana.model.ValueCount.JPQL_FIND_ALL_BY_JOB_ID;

/**
 * @author nicot
 */
@Entity
@IdClass(ValueCountKey.class)
@Table(name = "value_count",
        indexes = @Index(name = "jobIdIndex", columnList = "job_id")
)
@NamedQueries(
        @NamedQuery(
                name = JPQL_FIND_ALL_BY_JOB_ID,
                query = "select w from ValueCount w where w.jobId = :jobId"
        )
)
public class ValueCount {

    public static final String JPQL_FIND_ALL_BY_JOB_ID = "WordCount.findAllByJobId";

    @Id
    @Column(name = "job_id", nullable = false)
    private String jobId;

    @Id
    @Column(name = "value", nullable = false)
    private String value;

    @Column(name = "count", nullable = false)
    private int count;

    public ValueCount(String jobId, String value, int count) {
        this.jobId = jobId;
        this.value = value;
        this.count = count;
    }

    ValueCount() {
    }

    public String getJobId() {
        return jobId;
    }

    public String getValue() {
        return value;
    }

    public int getCount() {
        return count;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ValueCount wordCount = (ValueCount) o;
        return count == wordCount.count &&
                Objects.equals(jobId, wordCount.jobId) &&
                Objects.equals(value, wordCount.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(jobId, value, count);
    }

    @Override
    public String toString() {
        return "WordCount{" +
                "jobId='" + jobId + '\'' +
                ", value='" + value + '\'' +
                ", count=" + count +
                '}';
    }
}
