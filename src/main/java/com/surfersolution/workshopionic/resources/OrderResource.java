package com.surfersolution.workshopionic.resources;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.surfersolution.workshopionic.domain.Order;
import com.surfersolution.workshopionic.services.OrderService;

@RestController
@RequestMapping(value="/orders")
public class OrderResource {

	@Autowired
	private OrderService service;
	
	@RequestMapping(value="/{id}", method=RequestMethod.GET)
	public ResponseEntity<Order>find(@PathVariable Integer id){
		Order obj = service.findById(id);
		return ResponseEntity.ok().body(obj);
	}
	
	
}
