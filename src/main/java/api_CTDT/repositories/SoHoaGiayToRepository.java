package api_CTDT.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import api_CTDT.Models.SoHoaGiayTo;

public interface SoHoaGiayToRepository extends JpaRepository<SoHoaGiayTo, Integer>, JpaSpecificationExecutor<SoHoaGiayTo> {
	

}
