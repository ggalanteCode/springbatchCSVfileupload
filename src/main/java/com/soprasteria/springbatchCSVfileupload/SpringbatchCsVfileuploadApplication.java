package com.soprasteria.springbatchCSVfileupload;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import com.soprasteria.springbatchCSVfileupload.service.UserService;

@SpringBootApplication(scanBasePackageClasses = UserService.class)
@EnableAutoConfiguration(exclude = DataSourceAutoConfiguration.class)
@EnableMongoRepositories(basePackageClasses = MongoTemplate.class)
@EnableWebMvc
public class SpringbatchCsVfileuploadApplication extends SpringBootServletInitializer {

	@Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(SpringbatchCsVfileuploadApplication.class);
    }
	
	public static void main(String[] args) {
		SpringApplication.run(SpringbatchCsVfileuploadApplication.class, args);
	}

}
