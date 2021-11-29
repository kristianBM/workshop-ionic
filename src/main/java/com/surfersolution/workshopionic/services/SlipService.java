package com.surfersolution.workshopionic.services;

import java.util.Calendar;
import java.util.Date;

import org.springframework.stereotype.Service;

import com.surfersolution.workshopionic.domain.SlipPayment;

@Service
public class SlipService {

	public void fillSlipPayment (SlipPayment pay, Date orderInstant) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(orderInstant);
		cal.add(Calendar.DAY_OF_MONTH, 7);
		pay.setDueDate(cal.getTime());
	}
	
}
