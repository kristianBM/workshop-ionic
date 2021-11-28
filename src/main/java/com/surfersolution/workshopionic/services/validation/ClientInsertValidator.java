package com.surfersolution.workshopionic.services.validation;

import java.util.ArrayList;
import java.util.List;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;

import com.surfersolution.workshopionic.domain.Client;
import com.surfersolution.workshopionic.domain.enums.ClientType;
import com.surfersolution.workshopionic.dto.ClientNewDTO;
import com.surfersolution.workshopionic.repositories.ClientRepository;
import com.surfersolution.workshopionic.resources.exception.FieldMessage;
import com.surfersolution.workshopionic.services.validation.utils.BR;

public class ClientInsertValidator implements ConstraintValidator<ClientInsert, ClientNewDTO> {

	@Autowired
	private ClientRepository clientRepository;
	
	@Override
	public void initialize(ClientInsert ann) {
	}

	@Override
	public boolean isValid(ClientNewDTO objDto, ConstraintValidatorContext context) {
		List<FieldMessage> list = new ArrayList<>();

		if(objDto.getType().equals(ClientType.PHYSICAL_PERSON.getCod()) && !BR.isValidCPF(objDto.getCpfOrCnpj())){
			list.add(new FieldMessage("cpfOrCnpj", "Invalid CPF."));
		}
		
		if(objDto.getType().equals(ClientType.LEGAL_PERSON.getCod()) && !BR.isValidCNPJ(objDto.getCpfOrCnpj())){
			list.add(new FieldMessage("cpfOrCnpj", "Invalid CNPJ."));
		}
		
		Client aux = clientRepository.findByEmail(objDto.getEmail());
		if (aux != null) {
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
