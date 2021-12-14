package com.surfersolution.workshopionic.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.surfersolution.workshopionic.domain.State;
import com.surfersolution.workshopionic.repositories.StateRepository;

@Service
public class StateService {

	@Autowired
	private StateRepository stateRepository;
	
	public List<State> findAllByOrderByName(){
		return stateRepository.findAllByOrderByName();
	}
}
