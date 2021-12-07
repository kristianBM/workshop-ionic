package com.surfersolution.workshopionic.resources;

import java.net.URI;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

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
	@PreAuthorize("hasAnyRole('ADMIN')")
	@RequestMapping(method=RequestMethod.POST)
	public ResponseEntity<Void> insert(@Valid @RequestBody Order obj){
		obj = service.insert(obj);
	URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
			.buildAndExpand(obj.getId()).toUri();
	return ResponseEntity.created(uri).build();
	}
	
	@RequestMapping(method= RequestMethod.GET)
	public ResponseEntity<Page<Order>> findPage(
			@RequestParam(value="page", defaultValue = "0") Integer page,
			@RequestParam(value="linesPerPage", defaultValue = "24") Integer linesPerPage,
			@RequestParam(value="orderBy", defaultValue = "instant") String orderBy,
			@RequestParam(value="direction", defaultValue = "DESC")	String direction) {
		Page<Order> list = service.findPage(page, linesPerPage, orderBy, direction);
		return ResponseEntity.ok().body(list);
		
	}
	
}
