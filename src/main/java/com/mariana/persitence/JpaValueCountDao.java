package com.mariana.persitence;

import com.mariana.model.ValueCount;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;

/**
 * @author nicot
 */
@Repository
@Transactional
public class JpaValueCountDao implements ValueCountDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public void saveAll(List<ValueCount> wordCounts) {
        for (ValueCount wordCount : wordCounts) {
            entityManager.persist(wordCount);
        }
    }

    @Override
    public List<ValueCount> getByJobId(String jobId) {
        return entityManager.createNamedQuery(ValueCount.JPQL_FIND_ALL_BY_JOB_ID, ValueCount.class)
                .setParameter("jobId", jobId)
                .getResultList();
    }
}
