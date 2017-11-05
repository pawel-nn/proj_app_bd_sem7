package app.validation;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import app.dataTransportObject.ProductCategoryDTO;
import app.viewObject.ProductCategoryVO;

@Service
public class ProductCategoryValidation {

	public ProductCategoryDTO validateNewProduct(ProductCategoryDTO productCategoryDTO) {
		productCategoryDTO.setValid(false);
		try {
			ProductCategoryVO pvo = productCategoryDTO.getViewObject();
			if(StringUtils.isNoneBlank(pvo.getProductCategoryName()))
				productCategoryDTO.setValid(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return productCategoryDTO;	
	}

}
