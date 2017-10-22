package app.model;

import static javax.persistence.GenerationType.IDENTITY;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name= "product_image")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductImage {

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "product_image_id", unique = true, nullable = false)
	private Integer productImageId;

	@Column(name = "product_image_name", unique = true, nullable = false, length = 200)
	private String producerName;
	
}
