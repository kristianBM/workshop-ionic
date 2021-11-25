package com.surfersolution.workshopionic.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.surfersolution.workshopionic.domain.Category;
import com.surfersolution.workshopionic.repositories.CategoryRepository;
import com.surfersolution.workshopionic.services.exceptions.DataIntegrityException;
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
	
	public Category insert (Category obj) {
		obj.setId(null);
		return categoryRepository.save(obj);
	}
	
	public Category update (Category obj) {
		findById(obj.getId());
		return categoryRepository.save(obj);
	}
	
	public void delete (Integer id) {
		findById(id);
		try {
			categoryRepository.deleteById(id);
		}
		catch (DataIntegrityViolationException e){
			throw new DataIntegrityException("Can't delete a category which contains products.");
			
		}
	}
	
	public List<Category> findAll(){
		return categoryRepository.findAll();
	}
	
	public Page<Category> findPage(Integer page,Integer linesPerPage, String orderBy, String direction){
		PageRequest pageRequest =  PageRequest.of(page, linesPerPage, Direction.valueOf(direction), orderBy);
		return categoryRepository.findAll(pageRequest);
	}
}

