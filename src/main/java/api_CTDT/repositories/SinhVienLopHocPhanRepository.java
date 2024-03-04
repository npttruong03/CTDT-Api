package api_CTDT.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


import api_CTDT.Models.SinhVienLopHocPhan;

public interface SinhVienLopHocPhanRepository extends JpaRepository<SinhVienLopHocPhan, Integer>, JpaSpecificationExecutor<SinhVienLopHocPhan> {
	@Query(value = "SELECT * FROM svlhp WHERE maSV=:ma_Sv", nativeQuery = true)
	List<SinhVienLopHocPhan> findByMaSV(@Param("ma_Sv") String maSV);
	
	@Query(value = "SELECT * FROM svlhp WHERE idlhp=:idlhp", nativeQuery = true)
	List<SinhVienLopHocPhan> findByIdLHP(@Param("idlhp") Integer idlhp);
	

	@Query(value = "SELECT * FROM svlhp WHERE maSV=:ma_Sv AND diem10 >= 2.0", nativeQuery = true) 
	List<SinhVienLopHocPhan> checkSVLHP(@Param("ma_Sv") String maSV);

}
