package ua.com.harazh.oblik.domain.dto;

import javax.validation.constraints.NotBlank;

public class UserCreationDto {
	
	@NotBlank(message = "{login.notNull}")
	private String name;

	@NotBlank(message = "{pass.notNull}")
	private String password;

	private Double percentage;
	

	public UserCreationDto() {
		super();
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public String getPassword() {
		return password;
	}


	public void setPassword(String password) {
		this.password = password;
	}

	public Double getPercentage() {
		return percentage;
	}

	public void setPercentage(Double percentage) {
		this.percentage = percentage;
	}
}
