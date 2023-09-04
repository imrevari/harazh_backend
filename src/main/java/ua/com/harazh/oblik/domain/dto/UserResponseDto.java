package ua.com.harazh.oblik.domain.dto;

import ua.com.harazh.oblik.domain.OblikUser;

public class UserResponseDto {
	
	
	private Long id;

	private String name;
	
	private String role;

	private Double percentage;

	public UserResponseDto() {
		super();
	}



	public UserResponseDto(OblikUser user) {
		super();
		this.id = user.getId();
		this.name = user.getName();
		this.role = user.getRole().toString();
		this.percentage = user.getPercentage();
	}



	public Long getId() {
		return id;
	}



	public void setId(Long id) {
		this.id = id;
	}



	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public Double getPercentage() {
		return percentage;
	}

	public void setPercentage(Double percentage) {
		this.percentage = percentage;
	}
}
