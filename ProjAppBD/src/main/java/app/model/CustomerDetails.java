package app.model;

import static javax.persistence.GenerationType.IDENTITY;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import app.viewObject.CustomerDetailsVO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name= "customer_details")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomerDetails {
	
	public CustomerDetails(String name, String surname, String dateOfBirth, String address, String city, String postcode) {
		this.name = name;
		this.surname = surname;
		this.dateOfBirth = dateOfBirth;
		this.address = address;
		this.city = city;
		this.postcode = postcode;
	}
	
	public CustomerDetails(CustomerDetailsVO customerDetailsVO) {
		this.name = customerDetailsVO.getName();
		this.surname = customerDetailsVO.getSurname();
		this.dateOfBirth = customerDetailsVO.getDateOfBirth();
		this.address = customerDetailsVO.getAddress();
		this.city = customerDetailsVO.getCity();
		this.postcode = customerDetailsVO.getPostcode();
	}

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "customer_details_id", unique = true, nullable = false)
	private Integer customerDetailsId;

	@Column(name = "name", nullable = false, length = 60)
	private String name;
	
	@Column(name = "surnname", nullable = false, length = 45)
	private String surname;
	
	@Column(name = "date_of_birth", nullable = false, length = 45)
	private String dateOfBirth;
	
	@Column(name = "address", nullable = false, length = 120)
	private String address;
	
	@Column(name = "city", nullable = false, length = 60)
	private String city;
	
	@Column(name = "postcode", nullable = false, length = 20)
	private String postcode;

}
