package app.dataTransportObject;

import app.viewObject.BuyProductsVO;

public class BuyProductsDTO extends ValidationDTO<BuyProductsVO>{
	public BuyProductsDTO(BuyProductsVO buyProductsVO) {
		super(buyProductsVO, false);
	}

}
