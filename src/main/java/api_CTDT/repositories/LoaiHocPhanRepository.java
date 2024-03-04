package api_CTDT.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import api_CTDT.Models.LoaiHocPhan;

public interface LoaiHocPhanRepository extends JpaRepository<LoaiHocPhan, Integer>, JpaSpecificationExecutor<LoaiHocPhan> {
	
}
