package api_CTDT.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import api_CTDT.Models.MQHHocPhan;

public interface MoiQuanHeHPRepository extends JpaRepository<MQHHocPhan, Integer>, JpaSpecificationExecutor<MQHHocPhan> {
	@Query(value = "SELECT * FROM mqhhocphan WHERE ma_hoc_phan = :mahp", nativeQuery = true)
	List<MQHHocPhan> findByMaHP(@Param("mahp") String mahp);
}
