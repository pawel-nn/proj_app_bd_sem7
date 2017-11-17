package app.viewObject;

import lombok.Data;

@Data
public class CustomerDetailsVO {

	public CustomerDetailsVO() {}
	
	private Integer customerDetailsId;
	private String name;
	private String surname;
	private String dateOfBirth;
	private String address;
	private String city;
	private String postcode;
	
}
