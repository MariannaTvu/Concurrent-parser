package com.mariana.service.parser;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.InputStreamSource;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * @author nicot
 */
@Component
public class ConcurrentParser {

    private static final Logger LOGGER = LoggerFactory.getLogger(ConcurrentParser.class);

    private final LineParser lineParser;
    private final int threads;

    @Autowired
    public ConcurrentParser(LineParser lineParser, @Value("${parser.concurrent.threads}") int threads) {
        this.lineParser = lineParser;
        this.threads = threads;
    }

    public Map<String, Integer> countValues(List<? extends InputStreamSource> inputStreamSources) {
        List<Future<Map<String, Integer>>> futures = new ArrayList<>(inputStreamSources.size());

        ExecutorService parserExecutorService = Executors.newFixedThreadPool(threads);
        for (InputStreamSource inputStreamSource : inputStreamSources) {
            Callable<Map<String, Integer>> parserJob = () -> lineParser.countValues(inputStreamSource);
            Future<Map<String, Integer>> future = parserExecutorService.submit(parserJob);
            futures.add(future);
        }
        parserExecutorService.shutdown();

        Map<String, Integer> valuesCount = new HashMap<>();
        for (Future<Map<String, Integer>> future : futures) {
            try {
                Map<String, Integer> currentValuesCount = future.get();
                merge(valuesCount, currentValuesCount);
            } catch (InterruptedException e) {
                LOGGER.debug("Concurrent parsing thread is interrupted", e);
            } catch (ExecutionException e) {
                LOGGER.debug("Exception in the parsing thread", e);
            }
        }

        return valuesCount;
    }

    private void merge(Map<String, Integer> a, Map<String, Integer> b) {
        for (Map.Entry<String, Integer> entry : b.entrySet()) {
            Integer count = a.get(entry.getKey());
            if (count == null) {
                a.put(entry.getKey(), entry.getValue());
            } else {
                a.put(entry.getKey(), count + entry.getValue());
            }
        }
    }
}
