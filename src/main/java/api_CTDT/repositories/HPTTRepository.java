package api_CTDT.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import api_CTDT.Models.HPThayThe;

public interface HPTTRepository extends JpaRepository<HPThayThe, Integer>, JpaSpecificationExecutor<HPThayThe>{

}
