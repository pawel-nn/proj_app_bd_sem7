package app.viewObject;

import lombok.Data;

@Data
public class OwnerVO {

	private UserVO userVO;
	private String secret;
	
	private String errorMsg;
	private boolean invalidOverall;
}
