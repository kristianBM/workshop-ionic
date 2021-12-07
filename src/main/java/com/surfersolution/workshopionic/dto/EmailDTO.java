package com.surfersolution.workshopionic.dto;

import java.io.Serializable;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

public class EmailDTO implements Serializable{
	private static final long serialVersionUID = 1L;
	
	private String Email;
	
	public EmailDTO() {
	}
	
	@NotEmpty(message="Email can't be null.")
	@Email(message="Invalid Email.")
	public String getEmail() {
		return Email;
	}

	public void setEmail(String email) {
		Email = email;
	}
	
}
