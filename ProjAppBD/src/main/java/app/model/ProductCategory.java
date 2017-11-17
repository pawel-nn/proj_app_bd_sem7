package app.model;

import static javax.persistence.GenerationType.IDENTITY;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import app.dataTransportObject.ProductCategoryDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name= "product_categories")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductCategory {

	public ProductCategory(String productCategoryName) {
		this.productCategoryName = productCategoryName;
	}
	
	public ProductCategory(String productCategoryName, Integer productCategoryId) {
		this.productCategoryName = productCategoryName;
		this.productCategoryId = productCategoryId;
	}

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "product_category_id", unique = true, nullable = false)
	private Integer productCategoryId;

	@Column(name = "product_category_name", unique = true, nullable = false, length = 45)
	private String productCategoryName;
	
}
