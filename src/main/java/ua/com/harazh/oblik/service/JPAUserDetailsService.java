package ua.com.harazh.oblik.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import ua.com.harazh.oblik.domain.OblikUser;
import ua.com.harazh.oblik.repository.UserRepository;

@Service
public class JPAUserDetailsService  implements UserDetailsService{
	
	private UserRepository userRepository;

	@Autowired
	public JPAUserDetailsService(UserRepository userRepository) {
		super();
		this.userRepository = userRepository;
	}






	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		Optional<OblikUser> optionalUser = userRepository.findByName(username);
		
		if (!optionalUser.isPresent() || optionalUser.get().isDeleted()) {
            throw new UsernameNotFoundException("No user found with name: " + username);
        }
		
		OblikUser oblikUser = optionalUser.get();
		
		List<GrantedAuthority> authorities = AuthorityUtils.createAuthorityList(oblikUser.getRole().toString());
		
		UserDetails principal = User.withUsername(username).authorities(authorities).password(oblikUser.getPassword()).build();
		
		
		return principal;
	}

}
