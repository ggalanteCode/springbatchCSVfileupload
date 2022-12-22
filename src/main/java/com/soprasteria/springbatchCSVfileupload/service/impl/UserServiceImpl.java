package com.soprasteria.springbatchCSVfileupload.service.impl;

import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.multipart.MultipartFile;

import com.soprasteria.springbatchCSVfileupload.service.UserService;

public class UserServiceImpl implements UserService {
	
	@Autowired
	private JobLauncher jobLauncher;
	
	@Autowired
	private Job job;

	@Override
	public BatchStatus startFileUpload(MultipartFile file) throws JobExecutionAlreadyRunningException, JobRestartException, JobInstanceAlreadyCompleteException, JobParametersInvalidException {
		// TODO Auto-generated method stub
		String fileName = file.getOriginalFilename();
		JobParameters jobParameters = new JobParametersBuilder().addString("fileName", fileName)
				.addLong("startAt", System.currentTimeMillis()).toJobParameters();
		JobExecution execution = jobLauncher.run(job, jobParameters);
		return null;
	}

}
