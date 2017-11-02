package app.viewObject;

import lombok.Data;

@Data
public class CustomerDetailsVO {

	private Integer customerDetailsId;

	private String name;

	private String surnname;

	private String dateOfBirth;

	private String address;

	private String city;

	private String postcode;
}