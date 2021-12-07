package com.surfersolution.workshopionic.services;

import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.surfersolution.workshopionic.domain.Client;
import com.surfersolution.workshopionic.repositories.ClientRepository;
import com.surfersolution.workshopionic.services.exceptions.ObjectNotFoundException;

@Service
public class AuthService {

	@Autowired
	private ClientRepository clientRepository;

	@Autowired
	private BCryptPasswordEncoder pe;

	@Autowired
	private EmailService emailService;

	private Random random = new Random();

	public void sendNewPassword(String email) {

		Client client = clientRepository.findByEmail(email);
		if (client == null) {
			throw new ObjectNotFoundException("Email not found.");
		}

		String newPass = newPassword();
		client.setPassword(pe.encode(newPass));

		clientRepository.save(client);
		emailService.sendNewPasswordEmail(client, newPass);
	}

	private String newPassword() {
		char[] vet = new char[10];
		for (int i = 0; i < 10; i++) {
			vet[i] = randomChar();
		}
		return new String(vet);
	}

	private char randomChar() {
		int opt = random.nextInt(3);
		if (opt == 0) { // generates a number
			return (char) (random.nextInt(10) + 48);
		} else if (opt == 1) { // generates a small letter
			return (char) (random.nextInt(26) + 65);
		}

		else { // generates a capital letter
			return (char) (random.nextInt(26) + 97);
		}
	}
}