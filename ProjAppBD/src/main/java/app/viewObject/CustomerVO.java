package app.viewObject;

import lombok.Data;

@Data
public class CustomerVO {

	public CustomerVO() {}
	
	private UserVO userVO;
	private CustomerDetailsVO customerDetailsVO;
	private CountryVO countryVO;
	private Integer customerId;
	
}
