package app.viewObject;

import lombok.Data;

@Data
public class CustomerVO {

	private CustomerDetailsVO customerDetailsVO;
	private CountryVO countryVO;
	private Integer customerId;
}
