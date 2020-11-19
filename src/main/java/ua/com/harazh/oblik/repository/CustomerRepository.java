package ua.com.harazh.oblik.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ua.com.harazh.oblik.domain.Customer;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long>{
	
	Optional<Customer> findByFirstNameAndLastNameAndTelNumber(String firstName, String lastName, String telNumber);
	
	List<Customer> findByFirstName(String firstName);

}
