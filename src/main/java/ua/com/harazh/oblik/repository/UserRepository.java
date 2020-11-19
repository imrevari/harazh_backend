package ua.com.harazh.oblik.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ua.com.harazh.oblik.domain.OblikUser;

@Repository
public interface UserRepository extends JpaRepository<OblikUser, Long>{
	
	Optional<OblikUser> findByName(String name);

}
