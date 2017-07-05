package com.mariana;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author nicot
 */
@Configuration
public class SpringConfig {

    @Bean(destroyMethod = "shutdown")
    public ExecutorService mainExecutorService() {
        return Executors.newCachedThreadPool();
    }
}
