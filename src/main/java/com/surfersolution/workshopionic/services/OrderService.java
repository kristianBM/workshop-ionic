package com.surfersolution.workshopionic.services;

import java.util.Date;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.surfersolution.workshopionic.domain.Order;
import com.surfersolution.workshopionic.domain.OrderItem;
import com.surfersolution.workshopionic.domain.SlipPayment;
import com.surfersolution.workshopionic.domain.enums.PaymentState;
import com.surfersolution.workshopionic.repositories.OrderItemRepository;
import com.surfersolution.workshopionic.repositories.OrderRepository;
import com.surfersolution.workshopionic.repositories.PaymentRepository;
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
	
	public Order findById(Integer id) {
		Optional<Order>obj = orderRepository.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException(
				"Object no found! Id: " + id + ", Type: " + Order.class.getName()));
	}
	
	public Order insert(Order obj) {
		obj.setId(null);
		obj.setInstant(new Date());
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
		return obj;
	}
	
}

