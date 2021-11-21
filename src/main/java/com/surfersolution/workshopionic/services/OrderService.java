package com.surfersolution.workshopionic.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.surfersolution.workshopionic.domain.Order;
import com.surfersolution.workshopionic.repositories.OrderRepository;
import com.surfersolution.workshopionic.services.exceptions.ObjectNotFoundException;

@Service
public class OrderService {

	@Autowired
	private OrderRepository categoryRepository;
	
	public Order findById(Integer id) {
		Optional<Order>obj = categoryRepository.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException(
				"Object no found! Id: " + id + ", Type: " + Order.class.getName()));
	}
	
	
	
}

