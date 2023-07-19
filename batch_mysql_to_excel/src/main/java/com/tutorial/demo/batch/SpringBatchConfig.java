package com.tutorial.demo.batch;

import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.database.JdbcPagingItemReader;
import org.springframework.batch.item.database.Order;
import org.springframework.batch.item.database.support.MySqlPagingQueryProvider;
import org.springframework.batch.item.file.FlatFileItemWriter;
import org.springframework.batch.item.file.builder.FlatFileItemWriterBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.transaction.PlatformTransactionManager;

import com.tutorial.demo.entity.Worker;

@Configuration
public class SpringBatchConfig {
	
	@Autowired
	private DataSource dataSource;
	
	@Autowired
	private WorkerItemProcessor itemProcessor;
	
	@Bean
	public JdbcPagingItemReader<Worker> jdbcPagingItemReader(){
		JdbcPagingItemReader<Worker> pagingItemReader = new JdbcPagingItemReader<>();
		pagingItemReader.setDataSource(dataSource);
		pagingItemReader.setFetchSize(20);
		pagingItemReader.setRowMapper(new BeanPropertyRowMapper<>(Worker.class));
		pagingItemReader.setPageSize(5);
		
		MySqlPagingQueryProvider provider = new MySqlPagingQueryProvider();
		provider.setSelectClause("id, first_name, last_name, dept, salary");
		provider.setFromClause("person");
		
		Map<String, Order> orderByDept = new HashMap<>();
		orderByDept.put("dept", Order.ASCENDING);
		
		provider.setSortKeys(orderByDept);
		pagingItemReader.setQueryProvider(provider);
		return pagingItemReader;
		
	}
	
	@Bean
	public FlatFileItemWriter<Worker> fileItemWriter(){
		 return new FlatFileItemWriterBuilder<Worker>()
				 .name("fileItemWriter")
				 .resource(new FileSystemResource("C://batch/data.csv"))
				 .delimited()
				 .names(new String[] {"id", "firstName", "lastName", "dept", "salary"})
				 .build();
		
	}

	@Bean
	public Step getDbToCsvStep(JobRepository jobRepository, PlatformTransactionManager manager){
		
		return new StepBuilder("getDbToCsvStep", jobRepository)
				.<Worker, Worker> chunk(10, manager)
				.reader(jdbcPagingItemReader())
				.processor(itemProcessor)
				.writer(fileItemWriter())
				.build();
		
	}
	
	@Bean
	public Job dbToCsvJob(JobRepository jobRepository, JobClompletionNotificationListener listener, Step getDbToCsvStep) {
		return new JobBuilder("dbToCsvJob", jobRepository)
				.incrementer(new RunIdIncrementer())
				.listener(listener)
				.flow(getDbToCsvStep)
				.end()
				.build();
	}

}
