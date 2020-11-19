package ua.com.harazh.oblik.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ua.com.harazh.oblik.domain.Record;

@Repository
public interface RecordRepository extends JpaRepository<Record, Long>{

}
