package com.surfersolution.workshopionic.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.surfersolution.workshopionic.domain.Category;
import com.surfersolution.workshopionic.repositories.CategoryRepository;
import com.surfersolution.workshopionic.services.exceptions.ObjectNotFoundException;

@Service
public class CategoryService {

	@Autowired
	private CategoryRepository categoryRepository;
	
	public Category findById(Integer id) {
		Optional<Category>obj = categoryRepository.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException(
				"Object no found! Id: " + id + ", Type: " + Category.class.getName()));
	}
	
	
	
}

