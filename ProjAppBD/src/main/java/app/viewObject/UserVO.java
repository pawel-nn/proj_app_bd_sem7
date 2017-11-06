package app.viewObject;

import lombok.Data;

@Data
public class UserVO {

	private Integer userId;
	private String username;
	private String password_1;
	private String password_2;
	private String email;
	private boolean enabled;
	private String userRole;
	private Integer failedLogins;
	
}
