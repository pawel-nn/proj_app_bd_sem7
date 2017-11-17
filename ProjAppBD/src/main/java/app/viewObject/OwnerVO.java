package app.viewObject;

import lombok.Data;

@Data
public class OwnerVO {

	public OwnerVO() {}
	
	private UserVO userVO;
	private String secret;
	
	private String errorMsg;
	private boolean invalidOverall;
}
