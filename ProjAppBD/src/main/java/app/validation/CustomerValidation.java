package app.validation;

import org.springframework.stereotype.Service;

import app.dataTransportObject.CustomerDTO;

@Service
public class CustomerValidation {

	public CustomerDTO validateNewCustomer(CustomerDTO customerDTO) {
		// TODO Auto-generated method stub
		customerDTO.setValid(true);
		return customerDTO; // do zmiany
	}

}
