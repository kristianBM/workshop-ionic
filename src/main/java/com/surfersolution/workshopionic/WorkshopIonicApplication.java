package com.surfersolution.workshopionic;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.surfersolution.workshopionic.domain.Category;
import com.surfersolution.workshopionic.repositories.CategoryRepository;

@SpringBootApplication
public class WorkshopIonicApplication implements CommandLineRunner {

	@Autowired
	CategoryRepository categoryRepository;
	
	public static void main(String[] args) {
		SpringApplication.run(WorkshopIonicApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		
		
	
		Category cat1 = new Category(null, "Tech");
		Category cat2 = new Category(null, "Desk");
		
		categoryRepository.saveAll(Arrays.asList(cat1, cat2));
	}

}
