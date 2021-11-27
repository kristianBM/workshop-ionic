package com.surfersolution.workshopionic.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.surfersolution.workshopionic.domain.Client;
import com.surfersolution.workshopionic.dto.ClientDTO;
import com.surfersolution.workshopionic.repositories.ClientRepository;
import com.surfersolution.workshopionic.services.exceptions.DataIntegrityException;
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
	
	public Client update (Client obj) {
		Client newObj = findById(obj.getId());
		updateData(newObj, obj);
		return clientRepository.save(newObj);
	}
	
	public void delete (Integer id) {
		findById(id);
		try {
			clientRepository.deleteById(id);
		}
		catch (DataIntegrityViolationException e){
			throw new DataIntegrityException("Can't delete a client which contains products.");
			
		}
	}
	
	public List<Client> findAll(){
		return clientRepository.findAll();
	}
	
	public Page<Client> findPage(Integer page,Integer linesPerPage, String orderBy, String direction){
		PageRequest pageRequest =  PageRequest.of(page, linesPerPage, Direction.valueOf(direction), orderBy);
		return clientRepository.findAll(pageRequest);
	}
	
	public Client fromDto(ClientDTO objDto) {
		return new Client(objDto.getId(), objDto.getName(), objDto.getEmail(), null, null);
		
	}
	
	private void updateData(Client newObj, Client obj) {
		newObj.setName(obj.getName());
		newObj.setEmail(obj.getEmail());
	}
	
	
}

