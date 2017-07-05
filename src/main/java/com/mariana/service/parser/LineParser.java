package com.mariana.service.parser;

import org.springframework.core.io.InputStreamSource;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

/**
 * @author nicot
 */
@Component
public class LineParser {

    public Map<String, Integer> countValues(InputStreamSource inputStreamSource) throws IOException {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStreamSource.getInputStream()))) {
            Map<String, Integer> lineToCount = new HashMap<>();
            String line;
            while ((line = reader.readLine()) != null) {
                Integer count = lineToCount.get(line);
                if (count == null) {
                    lineToCount.put(line, 1);
                } else {
                    lineToCount.put(line, count + 1);
                }
            }
            return lineToCount;
        }
    }
}
