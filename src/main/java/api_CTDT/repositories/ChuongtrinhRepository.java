package api_CTDT.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import api_CTDT.Models.ChuongTrinh;
import api_CTDT.Models.Nganh;
 
public interface ChuongtrinhRepository extends JpaRepository<ChuongTrinh, Integer>, JpaSpecificationExecutor<ChuongTrinh>{
	@Query("SELECT c FROM ChuongTrinh c WHERE c.ma_chuong_trinh LIKE %:keyword% OR c.ten_chuong_trinh LIKE %:keyword%")
	List<ChuongTrinh> timTheoTuKhoa(@Param("keyword") String tuKhoa);

	 @Query("SELECT a FROM ChuongTrinh a WHERE a.ten_chuong_trinh LIKE %:keyword% OR a.ma_chuong_trinh LIKE %:keyword%")
	 List<ChuongTrinh> timTheoMaNganhVaMaChuyenNganh(@Param("keyword") String keyword);

	@Query(value = "SELECT * FROM chuongtrinh WHERE id_chuyen_nganh=:idcn AND id_nganh=:idnganh AND khoa_ap_dung LIKE %:khoa%", nativeQuery = true)
	List<ChuongTrinh> getByNganhAndChuyenNganhAndKhoa(@Param("idnganh") int idnganh, @Param("idcn") int idcn, @Param("khoa") String khoa);


}