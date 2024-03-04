package api_CTDT.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import api_CTDT.Models.LopHocPhan;

public interface LopHocPhanRepository extends JpaRepository<LopHocPhan, Integer>, JpaSpecificationExecutor<LopHocPhan> {
	@Query(value = "SELECT COUNT(*) AS count FROM lophocphan WHERE hoc_phan=:maHP AND hoc_ki=:hocki AND nam_hoc=:namhoc", nativeQuery = true)
	Integer countLHP(@Param("maHP") String maHP, @Param("hocki") int hocki, @Param("namhoc") String namhoc);
	
	@Query(value = "SELECT * FROM lophocphan WHERE hoc_phan=:maHP AND nam_hoc = (SELECT MAX(nam_hoc) FROM lophocphan) AND hoc_ki = (SELECT MAX(hoc_ki) FROM lophocphan WHERE nam_hoc = (SELECT MAX(nam_hoc) FROM lophocphan))", nativeQuery = true)
	List<LopHocPhan> getLopHocPhan(@Param("maHP") String maHP);
}
