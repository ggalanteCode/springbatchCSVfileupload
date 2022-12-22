package com.soprasteria.springbatchCSVfileupload.config;

import java.io.IOException;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.data.MongoItemWriter;
import org.springframework.batch.item.data.builder.MongoItemWriterBuilder;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.LineMapper;
import org.springframework.batch.item.file.MultiResourceItemReader;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.web.multipart.MultipartFile;

import com.soprasteria.springbatchCSVfileupload.entity.User;

@Configuration
@EnableBatchProcessing
public class BatchConfig {
	
	@Autowired
	JobBuilderFactory jobBuilderFactory;
	@Autowired
	StepBuilderFactory stepBuilderFactory;
	
	@Autowired
	private MongoTemplate template;

	@Value("#{jobParameters['fileName']}")
	private Resource[] resources;

	@Bean
	@StepScope
	public FlatFileItemReader<User> csvFileUserReader() {
		FlatFileItemReader<User> flatFileItemReader = new FlatFileItemReader<>();
		flatFileItemReader.setName("CSV-Reader");
		flatFileItemReader.setLineMapper(lineMapper());
		return flatFileItemReader;
	}

	@Bean
	public MultiResourceItemReader<User> multiResourceItemReader() {
		MultiResourceItemReader<User> reader = new MultiResourceItemReader<>();
		reader.setResources(resources);
		reader.setDelegate(csvFileUserReader());
		return reader;
	}

	@Bean
	public LineMapper<User> lineMapper() {
		DefaultLineMapper<User> lineMapper = new DefaultLineMapper<User>();

		DelimitedLineTokenizer lineTokenizer = new DelimitedLineTokenizer();
		lineTokenizer.setDelimiter(",");
		lineTokenizer.setStrict(false);
		lineTokenizer.setNames("id", "firstName", "lastName", "address", "email", "phone");

		BeanWrapperFieldSetMapper<User> fieldSetMapper = new BeanWrapperFieldSetMapper<>();
		fieldSetMapper.setTargetType(User.class);

		lineMapper.setLineTokenizer(lineTokenizer);
		lineMapper.setFieldSetMapper(fieldSetMapper);
		return lineMapper;

	}

	@Bean
	public MongoItemWriter<User> mongoDbProdottoWriter() {
		return new MongoItemWriterBuilder<User>().template(template).collection("users")
                .build();
	}

	@Bean
	public Job job(ItemReader<User> reader, ItemWriter<User> writer) {

		Step step = stepBuilderFactory.get("usersStep").<User, User>chunk(100).reader(reader).writer(writer).build();

		return jobBuilderFactory.get("importUsers")
				.incrementer(new RunIdIncrementer())
				.start(step).build();
	}

}
