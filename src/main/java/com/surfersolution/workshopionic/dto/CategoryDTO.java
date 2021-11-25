package com.surfersolution.workshopionic.dto;

import java.io.Serializable;

import javax.validation.constraints.NotEmpty;

import org.hibernate.validator.constraints.Length;

import com.surfersolution.workshopionic.domain.Category;

public class CategoryDTO implements Serializable{

	private static final long serialVersionUID = 1L;

	private Integer id;
	
	@NotEmpty(message="Name can't be empty")
	@Length(min=5, max=80, message="Length must have from 5 to 80 letters.")
	private String name;
	
	public CategoryDTO() {
	}

	public CategoryDTO(Category obj) {
		id = obj.getId();
		name = obj.getName();
	}
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	
}
