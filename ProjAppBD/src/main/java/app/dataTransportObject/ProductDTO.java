package app.dataTransportObject;

import app.viewObject.ProductVO;

public class ProductDTO extends ValidationDTO<ProductVO> {

	public ProductDTO(ProductVO productVO) {
		super(productVO, false);
	}
	
}
