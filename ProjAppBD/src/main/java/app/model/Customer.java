package app.model;

import static javax.persistence.GenerationType.IDENTITY;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name= "customers")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Customer {
	
	public Customer(User user, CustomerDetails customerDetails, Country country) {
		this.user = user;
		this.customerDetails = customerDetails;
		this.country = country;
	}
	
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "customer_id", unique = true, nullable = false)
	private Integer customerId;
	
	@OneToOne(cascade=CascadeType.ALL)
	@JoinColumn(name="user_id")
	private User user;
	
	@OneToOne(cascade=CascadeType.ALL)
	@JoinColumn(name="customer_details_id")
	private CustomerDetails customerDetails;	

	@ManyToOne(cascade=CascadeType.ALL)
	@JoinColumn(name="country_id")
	private Country country;
	
}
