package com.surfersolution.workshopionic;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.surfersolution.workshopionic.domain.Category;
import com.surfersolution.workshopionic.domain.City;
import com.surfersolution.workshopionic.domain.Product;
import com.surfersolution.workshopionic.domain.State;
import com.surfersolution.workshopionic.repositories.CategoryRepository;
import com.surfersolution.workshopionic.repositories.CityRepository;
import com.surfersolution.workshopionic.repositories.ProductRepository;
import com.surfersolution.workshopionic.repositories.StateRepository;

@SpringBootApplication
public class WorkshopIonicApplication implements CommandLineRunner {

	@Autowired
	CategoryRepository categoryRepository;
	
	@Autowired
	ProductRepository productRepository;
	
	@Autowired
	StateRepository stateRepository;
	
	@Autowired
	CityRepository cityRepository;
	
	public static void main(String[] args) {
		SpringApplication.run(WorkshopIonicApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		
		
	
		Category cat1 = new Category(null, "Tech");
		Category cat2 = new Category(null, "Desk");
		
		Product p1 = new Product(null, "Computer", 2000.00);
		Product p2 = new Product(null, "Printer", 800.00);
		Product p3 = new Product(null, "Mouse", 80.00);
		
		State st1 = new State(null, "Minas Gerais");
		State st2 = new State(null, "São Paulo");
		
		City c1 = new City(null, "Uberlândia", st1);
		City c2 = new City(null, "São Paulo", st2);
		City c3 = new City(null, "Campinas", st2);
		
		st1.getCities().addAll(Arrays.asList(c1));
		st2.getCities().addAll(Arrays.asList(c2, c3));
		
		cat1.getProducts().addAll(Arrays.asList(p1, p2, p3));
		cat2.getProducts().addAll(Arrays.asList(p2));
		p1.getCategories().addAll(Arrays.asList(cat1));
		p2.getCategories().addAll(Arrays.asList(cat1, cat2));
		p3.getCategories().addAll(Arrays.asList(cat1));
		
		categoryRepository.saveAll(Arrays.asList(cat1, cat2));
		productRepository.saveAll(Arrays.asList(p1, p2, p3));
		stateRepository.saveAll(Arrays.asList(st1, st2));
		cityRepository.saveAll(Arrays.asList(c1, c2, c3));
		
	}

}
