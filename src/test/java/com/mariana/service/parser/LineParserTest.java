package com.mariana.service.parser;

import org.junit.Test;
import org.springframework.core.io.InputStreamSource;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.Map;

import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.hasEntry;
import static org.junit.Assert.assertThat;

/**
 * @author nicot
 */
public class LineParserTest {

    @Test
    public void testLinesParser() throws IOException {
        String s = "aaa\n" +
                "bbb\n" +
                "aaa\n" +
                "bbb\n" +
                "ccc\n" +
                "ddd\n" +
                "eee\n";

        InputStreamSource inputStreamSource = () -> new ByteArrayInputStream(s.getBytes());
        Map<String, Integer> countWords = new LineParser().countValues(inputStreamSource);

        assertThat(countWords, allOf(
                hasEntry("aaa", 2),
                hasEntry("bbb", 2),
                hasEntry("ccc", 1),
                hasEntry("ddd", 1),
                hasEntry("eee", 1)
        ));
    }
}