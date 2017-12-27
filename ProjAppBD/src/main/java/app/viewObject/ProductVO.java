package app.viewObject;

import java.math.BigDecimal;

import app.model.Product;
import lombok.Data;

@Data
public class ProductVO {
	
	public void setUp(Product product) {
		this.name = product.getName();
		this.price = product.getPrice().toString();
		this.stockSize = product.getStockSize();
		this.code = product.getCode();
		this.producerId = product.getProducer().getProducerId();
		this.uId = product.getProductId();
		this.productId = product.getProductId();
		this.productCategoryId = product.getProductCategory().getProductCategoryId();
		this.productImageVO = new ProductImageVO(product.getProductImage());
	}
	
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
	private ProductImageVO productImageVO;
	
	private Integer uId;
	
}
