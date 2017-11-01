package app.viewObject;

import app.model.User;
import lombok.Data;

@Data
public class CustomerVO {

	private String username;
	private String password_1;
	private String password_2;
	private String name;
	private String surname;
	private User user;
	private CustomerDetailsVO customerDetailsVO;
	private CountryVO countryVO;
	private Integer customerId;
}
