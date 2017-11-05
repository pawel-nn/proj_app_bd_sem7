package app.model;

import static javax.persistence.GenerationType.IDENTITY;

import java.math.BigDecimal;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.Digits;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name= "products")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Product {

	public Product(ProductImage productImage, String name, BigDecimal validatedPrice, Integer stockSize, String code) {
		this.productImage = productImage;
		this.name = name;
		this.price = validatedPrice;
		this.stockSize = stockSize;
		this.code = code;
	}

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "product_id", unique = true, nullable = false)
	private Integer productId;

	@Column(name = "name", nullable = false, length = 45)
	private String name;
	
	@Digits(integer=10, fraction=2)
	@Column(name = "price", nullable = false)
	private BigDecimal price;
	
	@Column(name = "stock_size", nullable = false)
	private Integer stockSize;
	
	@Column(name = "code", length=12, nullable = false)
	private String code;
	
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name="producer_id")
	private Producer producer;
	
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name="product_image_id")
	private ProductImage productImage;
	
	@ManyToOne
	@JoinColumn(name="product_category_id")
	private ProductCategory productCategory;
	
}
