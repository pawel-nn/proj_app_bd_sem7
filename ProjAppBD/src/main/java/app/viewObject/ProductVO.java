package app.viewObject;

import java.math.BigDecimal;

import app.model.Producer;
import app.model.ProductCategory;
import lombok.Data;

@Data
public class ProductVO {
	
	public static int NAME_MAX_LEN = 45;
	
	private String name;
	private String price;
	private Integer stockSize;
	private String code;
	
	private Integer productId;
	private BigDecimal validatedPrice;
	
	private Integer producerId;
	private Integer productCategoryId;

}
