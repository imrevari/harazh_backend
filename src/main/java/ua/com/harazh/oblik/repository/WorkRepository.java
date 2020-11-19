package ua.com.harazh.oblik.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ua.com.harazh.oblik.domain.OblikUser;
import ua.com.harazh.oblik.domain.Work;

@Repository
public interface WorkRepository extends JpaRepository<Work, Long>{
	
	List<Work> findByOblikUserAndWorkDone(OblikUser oblikUser, boolean workDone);

}
