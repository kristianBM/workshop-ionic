package com.surfersolution.workshopionic.resources;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.surfersolution.workshopionic.domain.Category;
import com.surfersolution.workshopionic.services.CategoryService;

@RestController
@RequestMapping(value="/categories")
public class CategoryResource {

	@Autowired
	private CategoryService service;
	
	@RequestMapping(value="/{id}", method=RequestMethod.GET)
	public ResponseEntity<Category>find(@PathVariable Integer id){
		Category obj = service.findById(id);
		return ResponseEntity.ok().body(obj);
	}
	
	
}
