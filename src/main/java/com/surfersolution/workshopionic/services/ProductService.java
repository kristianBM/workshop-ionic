package com.surfersolution.workshopionic.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.surfersolution.workshopionic.domain.Category;
import com.surfersolution.workshopionic.domain.Product;
import com.surfersolution.workshopionic.repositories.CategoryRepository;
import com.surfersolution.workshopionic.repositories.ProductRepository;
import com.surfersolution.workshopionic.services.exceptions.ObjectNotFoundException;

@Service
public class ProductService {

	@Autowired
	private ProductRepository productRepository;
	
	@Autowired
	private CategoryRepository categoryRepository;
	
	public Product findById(Integer id) {
		Optional<Product>obj = productRepository.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException(
				"Object no found! Id: " + id + ", Type: " + Product.class.getName()));
	}
	
	public Page<Product> search(String name, List<Integer> ids, Integer page, Integer linesPerPage, String orderBy, String direction){
		PageRequest pageRequest =  PageRequest.of(page, linesPerPage, Direction.valueOf(direction), orderBy);
		List<Category> categories = categoryRepository.findAllById(ids);
		return productRepository.search(name, categories, pageRequest);
	}
	
}

