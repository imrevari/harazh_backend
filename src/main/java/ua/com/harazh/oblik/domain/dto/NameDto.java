package ua.com.harazh.oblik.domain.dto;

import javax.validation.constraints.NotNull;

public class NameDto {
	
	@NotNull
	private String name;

	public NameDto() {
		super();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	
	

}
