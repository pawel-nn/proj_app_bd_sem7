package app.viewObject;

import java.math.BigDecimal;

import lombok.Data;

@Data
public class BuyProductsVO {
	
	public BuyProductsVO() {
	}

	private String shipmentType;
	private BigDecimal totalCost;

}
