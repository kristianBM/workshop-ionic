package com.surfersolution.workshopionic.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.surfersolution.workshopionic.domain.Address;

@Repository
public interface AddressRepository extends JpaRepository<Address, Integer> {

}
