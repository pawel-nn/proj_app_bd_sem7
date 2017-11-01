package app.dataTransportObject;

import app.viewObject.CustomerVO;

public class CustomerDTO extends ValidationDTO<CustomerVO>{

	public CustomerDTO(CustomerVO viewObject) {
		super(viewObject, false);
	}

}
