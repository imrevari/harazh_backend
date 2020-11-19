package ua.com.harazh.oblik.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ua.com.harazh.oblik.domain.Car;

@Repository
public interface CarRepository extends JpaRepository<Car, Long>{
	
	Optional<Car> findByVinCode(String vinCode);
	
	List<Car> findByVinCodeContaining(String vinCode);
	
	Optional<Car> findByLicencePlate(String licencePlate);

}
