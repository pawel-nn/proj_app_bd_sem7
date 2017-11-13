package app.validation;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import app.dataTransportObject.OwnerDTO;
import app.viewObject.UserVO;

@Service
public class OwnerValidation {

	public static final String secret = "secret"; 
	
	public OwnerDTO validateNewOwner(OwnerDTO ownerDTO) {
		ownerDTO.setValid(false);
		UserVO vo = ownerDTO.getViewObject().getUserVO();
		ownerDTO.getViewObject().setInvalidOverall(true);
		if(StringUtils.isNoneBlank(vo.getEmail()) && StringUtils.isNoneBlank(vo.getPassword_1()) && StringUtils.isNoneBlank(vo.getUsername())) {
			if(vo.getPassword_1().equals(vo.getPassword_2())) {
				if(ownerDTO.getViewObject().getSecret().equals(secret))
					ownerDTO.setValid(true);
			}
		}
		return ownerDTO;
	}

}
