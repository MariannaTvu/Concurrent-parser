package com.mariana.persitence;

import com.mariana.model.ValueCount;

import java.util.List;

/**
 * @author nicot
 */
public interface ValueCountDao {

    void saveAll(List<ValueCount> wordCounts);
    List<ValueCount> getByJobId(String jobId);
}
