package com.tutorial.demo.batch;

import java.io.BufferedReader;
import java.io.FileReader;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.stereotype.Component;

@Component
public class JobClompletionNotificationListener implements JobExecutionListener {
	
	private static final Logger log = LoggerFactory.getLogger(JobClompletionNotificationListener.class);

	  @Override
	  public void afterJob(JobExecution jobExecution) {
	    
	    if(jobExecution.getStatus() == BatchStatus.COMPLETED) {
	      log.info("!!! JOB FINISHED! Time to verify the results");
	      
	      
	      try (BufferedReader br = new BufferedReader(new FileReader("C://batch/data.csv"))) {
	          String line;
	          while ((line = br.readLine()) != null) {
	        	  log.info("Found <{{}}> in the file csv.", line);
	          }
	      }
	      catch(Exception e) {
	    	  log.error("error leyendo el fichero", e);
	      }
	     
	    }

	  }
}
