package com.surfersolution.workshopionic.services;

import java.awt.image.BufferedImage;
import java.net.URI;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.surfersolution.workshopionic.domain.Address;
import com.surfersolution.workshopionic.domain.City;
import com.surfersolution.workshopionic.domain.Client;
import com.surfersolution.workshopionic.domain.enums.ClientType;
import com.surfersolution.workshopionic.domain.enums.Profile;
import com.surfersolution.workshopionic.dto.ClientDTO;
import com.surfersolution.workshopionic.dto.ClientNewDTO;
import com.surfersolution.workshopionic.repositories.AddressRepository;
import com.surfersolution.workshopionic.repositories.ClientRepository;
import com.surfersolution.workshopionic.security.UserSS;
import com.surfersolution.workshopionic.services.exceptions.AuthorizationException;
import com.surfersolution.workshopionic.services.exceptions.DataIntegrityException;
import com.surfersolution.workshopionic.services.exceptions.ObjectNotFoundException;

@Service
public class ClientService {

	@Autowired
	private BCryptPasswordEncoder pe;

	@Autowired
	AddressRepository addressRepository;

	@Autowired
	private ClientRepository clientRepository;
	
	@Autowired
	private S3Service s3Service;
	
	@Autowired
	private ImageService imageService;

	@Value("${img.prefix.client.profile}")
	private String prefix; 
	
	@Value("${img.profile.size}")
	private Integer size;
	
	public Client findById(Integer id) {
		
		UserSS user = UserService.authenticated();
		
		if(user == null || !user.hasRole(Profile.ADMIN) && !id.equals(user.getId())) {
			throw new AuthorizationException("Access denied.");
		}
		
		Optional<Client> obj = clientRepository.findById(id);
		return obj.orElseThrow(
				() -> new ObjectNotFoundException("Object no found! Id: " + id + ", Type: " + Client.class.getName()));
	}

	@Transactional
	public Client insert(Client obj) {
		obj.setId(null);
		obj = clientRepository.save(obj);
		addressRepository.saveAll(obj.getAddress());
		return obj;
	}

	public Client update(Client obj) {
		Client newObj = findById(obj.getId());
		updateData(newObj, obj);
		return clientRepository.save(newObj);
	}

	public void delete(Integer id) {
		findById(id);
		try {
			clientRepository.deleteById(id);
		} catch (DataIntegrityViolationException e) {
			throw new DataIntegrityException("Can't delete a client which contains products.");

		}
	}

	public List<Client> findAll() {
		return clientRepository.findAll();
	}
	
	public Client findByEmail(String email) {
		UserSS user = UserService.authenticated();
		if (user == null || !user.hasRole(Profile.ADMIN) && !email.equals(user.getUsername())) {
			throw new AuthorizationException("Access Denied.");
		}
		Client obj = clientRepository.findByEmail(email);
		if (obj == null) {
			throw new ObjectNotFoundException(
					"Object not found! Id: " + user.getId() + "Type: " + Client.class.getName());
		}
		return obj;
	}
	
	

	public Page<Client> findPage(Integer page, Integer linesPerPage, String orderBy, String direction) {
		PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(direction), orderBy);
		return clientRepository.findAll(pageRequest);
	}

	public Client fromDto(ClientDTO objDto) {
		return new Client(objDto.getId(), objDto.getName(), objDto.getEmail(), null, null, null);

	}

	public Client fromDto(ClientNewDTO objDto) {
		Client cli = new Client(null, objDto.getName(), objDto.getEmail(), objDto.getCpfOrCnpj(),
				ClientType.toEnum(objDto.getType()), pe.encode(objDto.getPassword()));
		City city = new City(objDto.getCityId(), null, null);
		Address adr = new Address(null, objDto.getPublicPlace(), objDto.getNumber(), objDto.getComplement(),
				objDto.getDistrict(), objDto.getZipCode(), cli, city);
		cli.getAddress().add(adr);
		cli.getPhoneNumbers().add(objDto.getPhone1());
		if (objDto.getPhone2() != null) {
			cli.getPhoneNumbers().add(objDto.getPhone2());
		}
		if (objDto.getPhone3() != null) {
			cli.getPhoneNumbers().add(objDto.getPhone3());
		}
		return cli;
	}

	private void updateData(Client newObj, Client obj) {
		newObj.setName(obj.getName());
		newObj.setEmail(obj.getEmail());
	}
	
	public URI uploadProfilePicture(MultipartFile multipartFile) {
		UserSS user = UserService.authenticated();
		
		if (user == null) {
			throw new AuthorizationException("Access Denied.");
		}
		
		BufferedImage jpgImage = imageService.getJpgImageFromFile(multipartFile);
		jpgImage = imageService.cropSquare(jpgImage);
		jpgImage = imageService.resize(jpgImage, size);
		String fileName = prefix + user.getId() + ".jpg";
		
		return s3Service.uploadFile(imageService.getInputStream(jpgImage, "jpg"), fileName, "image");
	}
}