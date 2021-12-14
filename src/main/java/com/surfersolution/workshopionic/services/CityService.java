package com.surfersolution.workshopionic.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.surfersolution.workshopionic.domain.City;
import com.surfersolution.workshopionic.repositories.CityRepository;

@Service
public class CityService {

	@Autowired
	private CityRepository cityRepository;

	public List<City> findByState(Integer stateId) {
		return cityRepository.findCities(stateId);
	}
}
