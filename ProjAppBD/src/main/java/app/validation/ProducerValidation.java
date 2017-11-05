package app.validation;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import app.dataTransportObject.ProducerDTO;
import app.viewObject.ProducerVO;

@Service
public class ProducerValidation {

	public ProducerDTO validateNewProduct(ProducerDTO producerDTO) {
		producerDTO.setValid(false);
		try {
			ProducerVO pvo = producerDTO.getViewObject();
			if(StringUtils.isNoneBlank(pvo.getProducerName()))
				producerDTO.setValid(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return producerDTO;	
	}

}
