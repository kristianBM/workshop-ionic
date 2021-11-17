package com.surfersolution.workshopionic.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.surfersolution.workshopionic.domain.Category;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Integer> {

}
