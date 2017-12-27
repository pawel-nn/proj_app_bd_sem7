package app.validation;

import java.lang.invoke.MethodHandles;
import java.math.BigDecimal;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import app.dataTransportObject.ProductDTO;
import app.model.ProductImage;
import app.model.repository.ProductImageRepository;
import app.operations.DatabaseLogService;
import app.viewObject.ProductVO;

@Service
public class ProductValidation {

	private static final Logger log = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
	
	@Autowired
	private ProductImageRepository productImageRepository;
	
	@Autowired
	private DatabaseLogService databaseLogService;
	
	public ProductDTO validateNewProduct(MultipartFile productPhoto, ProductDTO productDTO) {
		productDTO.setValid(false);
		try {
			ProductVO pvo = productDTO.getViewObject();
			if(pvo.getStockSize() != null && pvo.getPrice() != null && StringUtils.isNotBlank(pvo.getName())  && StringUtils.isNotBlank(pvo.getCode()))
				if(pvo.getCode().length() == 12 && pvo.getStockSize() >= 0 && pvo.getName().length() < ProductVO.NAME_MAX_LEN+1) {
					pvo.setValidatedPrice(new BigDecimal(pvo.getPrice().replaceAll(",", ".")));
					productDTO.setValid(true);
				}
			if(productPhoto != null && StringUtils.isNotBlank(productPhoto.getOriginalFilename())) {
				ProductImage productImage = productImageRepository.findByProductImageName(productPhoto.getOriginalFilename());
				if(productImage != null && productDTO.getViewObject().getProductId() == null) { // not checked at merge
					log.warn("PtS: Product image already in DB");
					databaseLogService.warn("PtS: Product image already in DB");
					productDTO.setErrorMsg("Zdjęcie o podanej nazwie już istnieje.");
					productDTO.setValid(false);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return productDTO;	
	}

}
