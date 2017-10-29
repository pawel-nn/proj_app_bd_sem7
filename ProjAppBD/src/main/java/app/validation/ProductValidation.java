package app.validation;

import java.math.BigDecimal;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import app.dataTransportObject.ProductDTO;
import app.viewObject.ProductVO;

@Service
public class ProductValidation {

	public ProductDTO validateNewProduct(ProductDTO productDTO) {
		productDTO.setValid(false);
		try {
			ProductVO pvo = productDTO.getViewObject();
			if(pvo.getStockSize() != null && pvo.getPrice() != null && StringUtils.isNotBlank(pvo.getName()))
				if(pvo.getCode() > 0 && pvo.getStockSize() >= 0 && pvo.getName().length() < ProductVO.NAME_MAX_LEN+1) {
					pvo.setValidatedPrice(new BigDecimal(pvo.getPrice().replaceAll(",", ".")));
					productDTO.setValid(true);
				}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return productDTO;	
	}

}
