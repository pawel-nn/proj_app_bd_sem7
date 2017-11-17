package app.viewObject;

import lombok.Data;

@Data
public class CountryVO {

	public CountryVO() {}
	
	private Integer countryId;
	private String countryName;
	private String countryCode;
}
