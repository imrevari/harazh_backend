package ua.com.harazh.oblik.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ua.com.harazh.oblik.domain.OblikUser;
import ua.com.harazh.oblik.domain.Work;
import ua.com.harazh.oblik.domain.WorkType;

@Repository
public interface WorkRepository extends JpaRepository<Work, Long>{
	
	List<Work> findByOblikUserAndWorkDone(OblikUser oblikUser, boolean workDone);

	List<Work> findByOblikUserInAndDoneAtBetweenAndWorkDoneOrderByDoneAtAsc(
			List<OblikUser> oblikUsers,
			LocalDateTime start,
			LocalDateTime end,
			boolean workDone);

	List<Work> findByWorkType(WorkType workType);
}
