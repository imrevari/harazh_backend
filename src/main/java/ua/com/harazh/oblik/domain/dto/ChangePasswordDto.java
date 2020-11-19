package ua.com.harazh.oblik.domain.dto;

import javax.validation.constraints.NotBlank;

public class ChangePasswordDto {
	
	
	@NotBlank(message = "{pass.notNull}")
	private String oldPassword;

	@NotBlank(message = "{pass.notNull}")
	private String newPassword;

	public ChangePasswordDto() {
		super();
	}

	public String getOldPassword() {
		return oldPassword;
	}

	public void setOldPassword(String oldPassword) {
		this.oldPassword = oldPassword;
	}

	public String getNewPassword() {
		return newPassword;
	}

	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}
	
	
	

}
