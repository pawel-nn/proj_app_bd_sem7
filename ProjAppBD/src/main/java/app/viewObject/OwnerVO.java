package app.viewObject;

import lombok.Data;

@Data
public class OwnerVO {

	private UserVO userVO;
	private Integer ownerId;
	private String secret;
	
}
