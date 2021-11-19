package com.surfersolution.workshopionic;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.surfersolution.workshopionic.domain.Address;
import com.surfersolution.workshopionic.domain.Category;
import com.surfersolution.workshopionic.domain.City;
import com.surfersolution.workshopionic.domain.Client;
import com.surfersolution.workshopionic.domain.Product;
import com.surfersolution.workshopionic.domain.State;
import com.surfersolution.workshopionic.domain.enums.ClientType;
import com.surfersolution.workshopionic.repositories.AddressRepository;
import com.surfersolution.workshopionic.repositories.CategoryRepository;
import com.surfersolution.workshopionic.repositories.CityRepository;
import com.surfersolution.workshopionic.repositories.ClientRepository;
import com.surfersolution.workshopionic.repositories.ProductRepository;
import com.surfersolution.workshopionic.repositories.StateRepository;

@SpringBootApplication
public class WorkshopIonicApplication implements CommandLineRunner {

	@Autowired
	private CategoryRepository categoryRepository;
	
	@Autowired
	private ProductRepository productRepository;
	
	@Autowired
	private StateRepository stateRepository;
	
	@Autowired
	private CityRepository cityRepository;
	
	@Autowired
	private ClientRepository clientRepository;
	
	@Autowired
	private AddressRepository addressRepository;
	
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
		
		Client cli1 = new Client(null, "Maria Silva", "maria@gmail.com", "36378912377", ClientType.PHYSICAL_PERSON);

		cli1.getPhoneNumbers().addAll(Arrays.asList("27363323", "93838393"));

		Address a1 = new Address (null, "Rua Flores", "300", "Apto 303", "Jardim", "38220834", cli1, c1);
		Address a2 = new Address (null, "Avenida Matos", "105", "Sala 800", "Centro", "38777012", cli1, c2);

		cli1.getAddress().addAll(Arrays.asList(a1, a2));
		
		categoryRepository.saveAll(Arrays.asList(cat1, cat2));
		productRepository.saveAll(Arrays.asList(p1, p2, p3));
		stateRepository.saveAll(Arrays.asList(st1, st2));
		cityRepository.saveAll(Arrays.asList(c1, c2, c3));
		clientRepository.saveAll(Arrays.asList(cli1));
		addressRepository.saveAll(Arrays.asList(a1, a2));
		
		
	}

}
