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
@Table(name= "countries")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Country {
	
	public Country(String countryName, String countryCode) {
		this.countryName = countryName;
		this.countryCode = countryCode;
	}
	
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "country_id", unique = true, nullable = false)
	private Integer countryId;
	
	@Column(name = "country_name", nullable = false, length = 45)
	private String countryName;
	
	@Column(name = "country_code", nullable = false, length = 6)
	private String countryCode;
	
	

}
