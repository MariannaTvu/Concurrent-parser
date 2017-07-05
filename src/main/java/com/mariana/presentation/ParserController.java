package com.mariana.presentation;

import com.mariana.model.ValueCount;
import com.mariana.service.JobNotFoundException;
import com.mariana.service.ParserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author nicot
 */
@RestController
public class ParserController {

    private final ParserService parserService;

    @Autowired
    public ParserController(ParserService parserService) {
        this.parserService = parserService;
    }

    @PostMapping(value = "/parse")
    public JobIdDto uploadingPost(@RequestParam("files") List<MultipartFile> uploadingFiles) {
        String jobId = parserService.performParsing(uploadingFiles);
        return new JobIdDto(jobId);
    }

    /**
     * @throws JobNotFoundException the job is not found.
     */
    @GetMapping(value = "/res/{jobId}")
    public List<ValueCountDto> getValueCounts(@PathVariable("jobId") String jobId) {
        List<ValueCount> valueCounts = parserService.getValueCounts(jobId);
        List<ValueCountDto> valueCountDtos = valueCounts.stream()
                .map(ValueCountDto::new)
                .collect(Collectors.toList());
        return valueCountDtos;
    }

    @ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "The job is not found")
    @ExceptionHandler(JobNotFoundException.class)
    public void handleJobNotFound() {
    }
}
