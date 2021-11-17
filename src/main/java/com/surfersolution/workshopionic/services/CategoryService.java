package com.surfersolution.workshopionic.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.surfersolution.workshopionic.domain.Category;
import com.surfersolution.workshopionic.repositories.CategoryRepository;

@Service
public class CategoryService {

	@Autowired
	private CategoryRepository categoryRepository;
	
	public Category findById(Integer id) {
		Optional<Category>obj = categoryRepository.findById(id);
		return obj.orElse(null);
	}
	
	
	
}

