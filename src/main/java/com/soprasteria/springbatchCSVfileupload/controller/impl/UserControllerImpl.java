package com.soprasteria.springbatchCSVfileupload.controller.impl;

import java.util.HashMap;

import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameter;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.function.RenderingResponse;

import com.soprasteria.springbatchCSVfileupload.controller.UserController;
import com.soprasteria.springbatchCSVfileupload.service.UserService;

@RestController
@RequestMapping("/users")
public class UserControllerImpl implements UserController {
	
	@Autowired
	private UserService userService;

	@Override
	@RequestMapping(value = "/uploadFromCSVTOMongoDB", method = RequestMethod.POST)
	public BatchStatus fromCSVTOMongoDB(@RequestParam("file") MultipartFile file) throws JobExecutionAlreadyRunningException, JobRestartException, JobInstanceAlreadyCompleteException, JobParametersInvalidException {
		// TODO Auto-generated method stub
		return userService.startFileUpload(file);						
	}

	@Override
	@RequestMapping(value = "/test", method = RequestMethod.POST)
	public ResponseEntity<Boolean> test() {
		// TODO Auto-generated method stub
		return new ResponseEntity<>(true, HttpStatus.OK);
	}
	
	@RequestMapping
    public String index() {
        return "Spring Boot POC Welcomes You!";
    }


    @RequestMapping(method = RequestMethod.POST, value = "/findrouting", consumes = { "application/json"}, produces = {
    		"application/json" })
    public RenderingResponse findRoute(@RequestBody HttpRequest request) throws Exception {
        // some business logic that would return response; as of now I have set it to null
        RenderingResponse response = null;
        return response;
    }

}
