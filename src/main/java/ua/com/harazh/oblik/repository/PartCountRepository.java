package ua.com.harazh.oblik.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ua.com.harazh.oblik.domain.PartCount;

@Repository
public interface PartCountRepository extends JpaRepository<PartCount, Long>{

}
