package com.surfersolution.workshopionic;

import java.text.SimpleDateFormat;
import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.surfersolution.workshopionic.domain.Address;
import com.surfersolution.workshopionic.domain.CardPayment;
import com.surfersolution.workshopionic.domain.Category;
import com.surfersolution.workshopionic.domain.City;
import com.surfersolution.workshopionic.domain.Client;
import com.surfersolution.workshopionic.domain.Order;
import com.surfersolution.workshopionic.domain.OrderItem;
import com.surfersolution.workshopionic.domain.Payment;
import com.surfersolution.workshopionic.domain.Product;
import com.surfersolution.workshopionic.domain.SlipPayment;
import com.surfersolution.workshopionic.domain.State;
import com.surfersolution.workshopionic.domain.enums.ClientType;
import com.surfersolution.workshopionic.domain.enums.PaymentState;
import com.surfersolution.workshopionic.repositories.AddressRepository;
import com.surfersolution.workshopionic.repositories.CategoryRepository;
import com.surfersolution.workshopionic.repositories.CityRepository;
import com.surfersolution.workshopionic.repositories.ClientRepository;
import com.surfersolution.workshopionic.repositories.OrderItemRepository;
import com.surfersolution.workshopionic.repositories.OrderRepository;
import com.surfersolution.workshopionic.repositories.PaymentRepository;
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
	
	@Autowired
	private OrderRepository orderRepository;
	
	@Autowired
	private PaymentRepository paymentRepository;
	
	@Autowired
	private OrderItemRepository orderItemRepository;
	
	public static void main(String[] args) {
		SpringApplication.run(WorkshopIonicApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
	
		Category cat1 = new Category(null, "Tech");
		Category cat2 = new Category(null, "Desk");
		Category cat3 =  new Category(null, "Bedding");
		Category cat4 = new Category(null, "Electronics");
		Category cat5 = new Category(null, "Garden");
		Category cat6 = new Category(null, "Decoration");
		Category cat7 = new Category(null, "Perfumery");
		
		
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
	
		categoryRepository.saveAll(Arrays.asList(cat1, cat2, cat3, cat4, cat5, cat6, cat7));
		productRepository.saveAll(Arrays.asList(p1, p2, p3));
		stateRepository.saveAll(Arrays.asList(st1, st2));
		cityRepository.saveAll(Arrays.asList(c1, c2, c3));
		clientRepository.saveAll(Arrays.asList(cli1));
		addressRepository.saveAll(Arrays.asList(a1, a2));
		
		Order or1 = new Order(null, sdf.parse("30/09/2017 10:32"), cli1, a1);
		Order or2 = new Order(null, sdf.parse("10/10/2017 19:35"),cli1, a2);
		
		Payment pay1 = new CardPayment(null, PaymentState.SETTLED, or1, 6);
		or1.setPayment(pay1);
		
		Payment pay2 = new SlipPayment(null, PaymentState.PENDING, or2, sdf.parse("20/10/2017 00:00"), null);
		or2.setPayment(pay2);
		
		cli1.getOrders().addAll(Arrays.asList(or1, or2));
		
		orderRepository.saveAll(Arrays.asList(or1, or2));
		paymentRepository.saveAll(Arrays.asList(pay1, pay2));
		
		OrderItem oi1 = new OrderItem(or1, p1, 0.00, 1, 2000.00); 
		OrderItem oi2 = new OrderItem(or1, p3, 0.00, 2, 80.00);
		OrderItem oi3 = new OrderItem(or2, p2, 100.00, 1, 800.00);
		
		or1.getItens().addAll(Arrays.asList(oi1, oi2));
		or2.getItens().addAll(Arrays.asList(oi3));
		
		p1.getItens().addAll(Arrays.asList(oi1));
		p2.getItens().addAll(Arrays.asList(oi3));
		p3.getItens().addAll(Arrays.asList(oi2));

		orderItemRepository.saveAll(Arrays.asList(oi1, oi2, oi3));
	}

}
