package api_CTDT.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import api_CTDT.Models.Log;


public interface LogRepository extends JpaRepository<Log, Integer>, JpaSpecificationExecutor<Log>{

}
