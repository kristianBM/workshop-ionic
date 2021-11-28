package com.surfersolution.workshopionic.services.validation;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerMapping;

import com.surfersolution.workshopionic.domain.Client;
import com.surfersolution.workshopionic.dto.ClientDTO;
import com.surfersolution.workshopionic.repositories.ClientRepository;
import com.surfersolution.workshopionic.resources.exception.FieldMessage;

public class ClientUpdateValidator implements ConstraintValidator<ClientUpdate, ClientDTO> {

	@Autowired
	private ClientRepository clientRepository;
	
	@Autowired
	private HttpServletRequest request;

	@Override
	public void initialize(ClientUpdate ann) {
	}

	@Override
	public boolean isValid(ClientDTO objDto, ConstraintValidatorContext context) {
		
		@SuppressWarnings("unchecked")
		Map<String, String> map = (Map<String, String>) request.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE);
		Integer uriId = Integer.parseInt(map.get("id"));
		
		List<FieldMessage> list = new ArrayList<>();

		Client aux = clientRepository.findByEmail(objDto.getEmail());
		if (aux != null && !aux.getId().equals(uriId)) {
			list.add(new FieldMessage("email", "Email already used."));
		}

		// inclua os testes aqui, inserindo erros na lista

		for (FieldMessage e : list) {
			context.disableDefaultConstraintViolation();
			context.buildConstraintViolationWithTemplate(e.getMessage()).addPropertyNode(e.getFieldName())
					.addConstraintViolation();
		}
		return list.isEmpty();
	}
}
