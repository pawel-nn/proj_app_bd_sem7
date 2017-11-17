package app.viewObject;

import lombok.Data;

@Data
public class ProductCategoryVO {
	
	public ProductCategoryVO(String productCategoryName) {
		this.productCategoryName = productCategoryName;
	}
	
	public ProductCategoryVO(String productCategoryName, Integer productCategoryId) {
		this.productCategoryId = productCategoryId;
		this.productCategoryName = productCategoryName;
	}
	
	public void setUp(String productCategoryName, Integer uId) {
		this.uId = uId;
		this.productCategoryName = productCategoryName;
	}
	
	public ProductCategoryVO() {}

	private Integer productCategoryId;
	private String productCategoryName;
	private Integer uId;
	
	private String errorMsg;
	private boolean invalidOverall;
	
}
