package app.viewObject;

import lombok.Data;

@Data
public class ProductCategoryVO {
	
	public ProductCategoryVO(String productCategoryName) {
		this.productCategoryName = productCategoryName;
	}

	private String productCategoryName;

	private String errorMsg;
	private boolean invalidOverall;
}
