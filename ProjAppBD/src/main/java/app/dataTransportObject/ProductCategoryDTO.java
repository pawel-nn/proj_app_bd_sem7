package app.dataTransportObject;

import app.viewObject.ProductCategoryVO;

public class ProductCategoryDTO extends ValidationDTO<ProductCategoryVO> {

	public ProductCategoryDTO(ProductCategoryVO productCategoryVO) {
		super(productCategoryVO, false);
	}
	
}
