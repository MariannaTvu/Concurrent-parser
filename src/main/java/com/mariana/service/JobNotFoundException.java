package com.mariana.service;

/**
 * @author nicot
 */
public class JobNotFoundException extends RuntimeException {

    public JobNotFoundException(String jobId) {
        super("The job " + jobId + " is not found");
    }
}
