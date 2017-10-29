package app.viewObject;

import lombok.Data;

@Data
public class NewPasswordVO {
	
	public NewPasswordVO(NewPasswordVO newPasswordVO) {
		this.newPassword_1 = newPasswordVO.getNewPassword_1();
		this.newPassword_2 = newPasswordVO.getNewPassword_2();
	}
	
	private String newPassword_1;
	private String newPassword_2;

}
