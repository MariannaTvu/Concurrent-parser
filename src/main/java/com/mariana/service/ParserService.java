package com.mariana.service;

import com.mariana.model.ValueCount;
import com.mariana.persitence.ValueCountDao;
import com.mariana.service.parser.ConcurrentParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamSource;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.stream.Collectors;

/**
 * @author nicot
 */
@Service
public class ParserService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ParserService.class);

    private final ExecutorService executorService;
    private final ConcurrentParser concurrentParser;
    private final ValueCountDao valueCountDao;

    @Autowired
    public ParserService(ExecutorService executorService, ConcurrentParser concurrentParser, ValueCountDao valueCountDao) {
        this.executorService = executorService;
        this.concurrentParser = concurrentParser;
        this.valueCountDao = valueCountDao;
    }

    public String performParsing(List<? extends InputStreamSource> inputStreams) {
        String jobId = UUID.randomUUID().toString();

        executorService.execute(() -> {
            LOGGER.debug("Job " + jobId + " starts processing...");

            Map<String, Integer> valuesCount = concurrentParser.countValues(inputStreams);

            List<ValueCount> valueCounts = valuesCount.entrySet().stream()
                    .map(entry -> new ValueCount(jobId, entry.getKey(), entry.getValue()))
                    .collect(Collectors.toList());

            valueCountDao.saveAll(valueCounts);

            LOGGER.debug("Job " + jobId + " is process successfully!");
        });

        return jobId;
    }

    /**
     * @throws JobNotFoundException the job is not found.
     */
    public List<ValueCount> getValueCounts(String jobId) {
        List<ValueCount> valueCounts = valueCountDao.getByJobId(jobId);
        if (valueCounts.isEmpty()) {
            throw new JobNotFoundException(jobId);
        }

        return valueCounts;
    }
}
