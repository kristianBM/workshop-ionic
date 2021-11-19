package com.surfersolution.workshopionic.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.surfersolution.workshopionic.domain.Client;
import com.surfersolution.workshopionic.repositories.ClientRepository;
import com.surfersolution.workshopionic.services.exceptions.ObjectNotFoundException;

@Service
public class ClientService {

	@Autowired
	private ClientRepository clientRepository;
	
	public Client findById(Integer id) {
		Optional<Client>obj = clientRepository.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException(
				"Object no found! Id: " + id + ", Type: " + Client.class.getName()));
	}
	
	
	
}

