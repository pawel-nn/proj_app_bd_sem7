package app.dataTransportObject;

import app.viewObject.ProductVO;

public class DictionaryDTO extends ValidationDTO<ProductVO> {

	public DictionaryDTO(ProductVO productVO) {
		super(productVO, false);
	}
	
}
