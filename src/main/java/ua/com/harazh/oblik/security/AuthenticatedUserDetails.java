package ua.com.harazh.oblik.security;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public class AuthenticatedUserDetails {
	
	
	 	private String name;

	    private String role;

		public AuthenticatedUserDetails() {
			super();
		}

		public AuthenticatedUserDetails(UserDetails user) {
			super();
			this.name = user.getUsername();
			this.role = findRole(user);
		}
	    
	    
		
		
		private String findRole(UserDetails user) {
	        String role = null;
	        List<GrantedAuthority> roles = user.getAuthorities().stream()
	                .filter(authority -> authority.getAuthority().startsWith("ROLE_"))
	                .collect(Collectors.toList());
	        if (!roles.isEmpty()) {
	            role = roles.get(0).getAuthority();
	        }

	        return role;
	    }

		public String getName() {
			return name;
		}

		public String getRole() {
			return role;
		}

	
}
