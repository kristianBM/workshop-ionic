package com.surfersolution.workshopionic.resources;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.surfersolution.workshopionic.domain.City;
import com.surfersolution.workshopionic.domain.State;
import com.surfersolution.workshopionic.dto.CityDTO;
import com.surfersolution.workshopionic.dto.StateDTO;
import com.surfersolution.workshopionic.services.CityService;
import com.surfersolution.workshopionic.services.StateService;

@RestController
@RequestMapping(value="/states")
public class StateResource {
	
	@Autowired
	private StateService service;
	
	@Autowired
	private CityService cityService;
	
	@RequestMapping(method= RequestMethod.GET)
	public ResponseEntity<List<StateDTO>> findAll(){
		List<State> list = service.findAllByOrderByName();
		List<StateDTO> listDto = list.stream().map(obj -> new StateDTO(obj)).collect(Collectors.toList());
		return ResponseEntity.ok().body(listDto);
		
	}
	
	@RequestMapping(value="/{stateId}/cities", method=RequestMethod.GET)
	public ResponseEntity<List<CityDTO>> findCities(@PathVariable Integer stateId){
		List<City> list = cityService.findByState(stateId);
		List<CityDTO> listDto = list.stream().map(obj -> new CityDTO(obj)).collect(Collectors.toList());
		return ResponseEntity.ok().body(listDto);
		
	}
}
