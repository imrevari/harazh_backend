package ua.com.harazh.oblik.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import ua.com.harazh.oblik.domain.dto.UserCreationDto;

import java.util.Objects;

@Entity
public class OblikUser {
	
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(nullable = false)
	private String name;
	
	@Column(nullable = false)
	private String password;
	
	private Role role;
	
	private boolean deleted;

	@Column(nullable = false)
	private Double percentage;

	public OblikUser() {
		super();
	}
	
	

	public OblikUser(UserCreationDto dto) {
		super();
		this.name = dto.getName().trim();
		this.role = Role.ROLE_JUNIOR_USER;
		this.deleted = false;
		if(Objects.isNull(dto.getPercentage())){
			this.percentage = 0.5d;
		}else{
			if(dto.getPercentage() < 0d || dto.getPercentage() > 1){
				this.percentage = 0.5d;
			}else{
				this.percentage = (double) Math.round(dto.getPercentage() * 100) / 100 ;
			}
		}
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

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}



	public boolean isDeleted() {
		return deleted;
	}



	public void setDeleted(boolean deleted) {
		this.deleted = deleted;
	}

	public Double getPercentage() {
		return percentage;
	}

	public void setPercentage(Double percentage) {
		this.percentage = percentage;
	}
}
