package app.viewObject;

import lombok.Data;

@Data
public class UserVO {
	
	public UserVO(String username, String password_1, String password_2, String email) {
		this.username = username;
		this.password_1 = password_1;
		this.password_2 = password_2;
		this.email = email;
	}
	
	private Integer userId;
	private String username;
	private String password_1;
	private String password_2;
	private String email;
	private boolean enabled;
	private String userRole;
	private Integer failedLogins;
	
}
