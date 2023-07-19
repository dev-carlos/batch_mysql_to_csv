package com.tutorial.demo.batch;

import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

import com.tutorial.demo.entity.Worker;

@Component
public class WorkerItemProcessor implements ItemProcessor<Worker, Worker> {

	@Override
	public Worker process(Worker item) throws Exception {
		
		return item;
	}
	
	

}
