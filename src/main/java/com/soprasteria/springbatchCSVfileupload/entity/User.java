package com.soprasteria.springbatchCSVfileupload.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Document(collection = "users")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {
	
	@Id
	private Integer id;
	private String firstName;
	private String lastName;
	private String address;
	private String email;
	private String phone;

}