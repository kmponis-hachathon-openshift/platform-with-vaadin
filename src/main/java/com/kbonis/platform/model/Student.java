package com.kbonis.platform.model;

import lombok.Data;
import org.springframework.data.annotation.Id;

import java.io.Serializable;

@Data
public class Student implements Serializable { //extends ResourceSupport

	private static final long serialVersionUID = 1L;

	@Id
	private String studentId;

	private String firstName;

	private String lastName;

	private String phone;

	private String email;

	public Student() {}

	public Student(String firstName, String lastName, String phone, String email) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.phone = phone;
		this.email = email;
	}

}