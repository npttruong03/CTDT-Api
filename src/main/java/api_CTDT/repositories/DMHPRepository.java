package api_CTDT.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import api_CTDT.Models.DanhMucHocPhan;

public interface DMHPRepository extends JpaRepository<DanhMucHocPhan, Integer>, JpaSpecificationExecutor<DanhMucHocPhan>{
	@Query(value = "SELECT COUNT(*) FROM danhmuchocphan where id_loai_hoc_phan = :id_lhp", nativeQuery = true )
	Integer checkLHPused(@Param("id_lhp") Integer id_lhp);
	
	@Query(value = "SELECT * FROM danhmuchocphan where ma_hoc_phan = :maHP", nativeQuery = true )
	DanhMucHocPhan getByMaHP(@Param("maHP") String maHP);
}
