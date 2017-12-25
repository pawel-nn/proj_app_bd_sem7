package app.viewObject;

import java.math.BigDecimal;

import lombok.Data;

@Data
public class ProductVO {
	
	public ProductVO(Integer producerId, Integer productCategoryId, String name, String price, int stockSize, String code) {
		this.name = name;
		this.price = price;
		this.stockSize = stockSize;
		this.code = code;
		this.producerId = producerId;
		this.productCategoryId = productCategoryId;
	}
	
	public ProductVO() {}
	
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
