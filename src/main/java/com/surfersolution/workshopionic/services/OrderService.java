package com.surfersolution.workshopionic.services;

import java.util.Date;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.surfersolution.workshopionic.domain.Client;
import com.surfersolution.workshopionic.domain.Order;
import com.surfersolution.workshopionic.domain.OrderItem;
import com.surfersolution.workshopionic.domain.SlipPayment;
import com.surfersolution.workshopionic.domain.enums.PaymentState;
import com.surfersolution.workshopionic.repositories.OrderItemRepository;
import com.surfersolution.workshopionic.repositories.OrderRepository;
import com.surfersolution.workshopionic.repositories.PaymentRepository;
import com.surfersolution.workshopionic.security.UserSS;
import com.surfersolution.workshopionic.services.exceptions.AuthorizationException;
import com.surfersolution.workshopionic.services.exceptions.ObjectNotFoundException;

@Service
public class OrderService {

	@Autowired
	private OrderRepository orderRepository;
	
	@Autowired
	private  PaymentRepository paymentRepository;
	
	@Autowired
	private SlipService slipService;
	
	@Autowired
	private ProductService productService;
	
	@Autowired
	private OrderItemRepository orderItemRepository;
	
	@Autowired
	private ClientService clientService;
	
	@Autowired
	private EmailService emailService;
	
	
	public Order findById(Integer id) {
		Optional<Order>obj = orderRepository.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException(
				"Object no found! Id: " + id + ", Type: " + Order.class.getName()));
	}
	
	public Order insert(Order obj) {
		obj.setId(null);
		obj.setInstant(new Date());
		obj.setClient(clientService.findById(obj.getClient().getId()));
		obj.getPayment().setPaymentState(PaymentState.PENDING);
		obj.getPayment().setOrder(obj);
		if (obj.getPayment() instanceof SlipPayment) {
			SlipPayment pay = (SlipPayment) obj.getPayment();
			slipService.fillSlipPayment(pay, obj.getInstant());
		}
		obj = orderRepository.save(obj);
		paymentRepository.save(obj.getPayment());
		for (OrderItem oi : obj.getItens()) {
			oi.setDiscount(0.0);
			oi.setProduct(productService.findById(oi.getProduct().getId()));
			oi.setPrice(oi.getProduct().getPrice());
			oi.setOrder(obj);
		}
		orderItemRepository.saveAll(obj.getItens());
		emailService.sendOrderConfirmationHtmlEmail(obj);
		return obj;
	}
	
	public Page<Order> findPage(Integer page,Integer linesPerPage, String orderBy, String direction){
		UserSS user = UserService.authenticated(); 
		if (user == null) {
			throw new AuthorizationException("Access denied.");
		}
		PageRequest pageRequest =  PageRequest.of(page, linesPerPage, Direction.valueOf(direction), orderBy);
		Client client = clientService.findById(user.getId());
		return orderRepository.findByClient(client, pageRequest);
		
	}
}

