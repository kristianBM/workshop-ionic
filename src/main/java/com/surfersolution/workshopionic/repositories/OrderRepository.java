package com.surfersolution.workshopionic.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.surfersolution.workshopionic.domain.Client;
import com.surfersolution.workshopionic.domain.Order;

@Repository
public interface OrderRepository extends JpaRepository<Order, Integer> {

	@Transactional(readOnly = true)
	Page<Order> findByClient(Client client, Pageable pageRequest);
	
}
