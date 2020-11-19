package ua.com.harazh.oblik.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ua.com.harazh.oblik.domain.Part;

@Repository
public interface PartRepository extends JpaRepository<Part, Long>{
	
	
	List<Part> findByNameContaining(String name);
}
