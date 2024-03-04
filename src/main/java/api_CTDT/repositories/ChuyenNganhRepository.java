package api_CTDT.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import api_CTDT.Models.ChuongTrinh;
import api_CTDT.Models.ChuyenNganh;
import jakarta.transaction.Transactional;

@Transactional
public interface ChuyenNganhRepository extends JpaRepository<ChuyenNganh, Integer>, JpaSpecificationExecutor<ChuyenNganh> {
	@Query("SELECT cn FROM ChuyenNganh cn WHERE cn.ma_chuyen_nganh LIKE %:keyword% OR cn.ten_chuyen_nganh LIKE %:keyword%")
    List<ChuyenNganh> searchByKeyword(@Param("keyword") String query);
	
	@Query(value = "SELECT COUNT(*) FROM sinhvien WHERE chuyen_nganh=:macn", nativeQuery = true)
	Integer countSVByChuyenNganh(@Param("macn") Integer macn);
	
	
	@Query(value = "SELECT * FROM chuyennganh WHERE id_nganh=:ma_nganh", nativeQuery = true)
	List<ChuyenNganh> getListChuyenNganh(@Param("ma_nganh") int ma_nganh);
}
