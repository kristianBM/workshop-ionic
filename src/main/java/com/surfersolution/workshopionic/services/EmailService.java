package com.surfersolution.workshopionic.services;

import org.springframework.mail.SimpleMailMessage;

import com.surfersolution.workshopionic.domain.Order;

public interface EmailService {
	
	void sendOrderConfirmationEmail(Order obj);

	void sendEmail(SimpleMailMessage msg);
}
