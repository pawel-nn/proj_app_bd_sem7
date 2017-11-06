package app.validation;

import org.springframework.stereotype.Service;

import app.dataTransportObject.OwnerDTO;

@Service
public class OwnerValidation {

	public OwnerDTO validateNewOwner(OwnerDTO ownerDTO) {
		ownerDTO.setValid(true);
		return ownerDTO;
	}

}
