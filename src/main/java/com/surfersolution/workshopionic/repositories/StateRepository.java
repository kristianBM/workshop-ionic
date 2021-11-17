package com.surfersolution.workshopionic.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.surfersolution.workshopionic.domain.State;

@Repository
public interface StateRepository extends JpaRepository<State, Integer> {

}
