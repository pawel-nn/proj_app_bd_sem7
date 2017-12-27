package app.viewObject;

import app.model.ProductImage;
import lombok.Data;

@Data
public class ProductImageVO {

	public ProductImageVO() {}
	
	public ProductImageVO(ProductImage productImage) {
		this.productImageId = productImage.getProductImageId();
		this.productImageName = productImage.getProductImageName();
	}

	private Integer productImageId;
	private String productImageName;
	
}
