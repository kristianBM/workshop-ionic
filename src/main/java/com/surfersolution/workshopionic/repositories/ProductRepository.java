package com.surfersolution.workshopionic.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.surfersolution.workshopionic.domain.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {

}
