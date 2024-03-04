package api_CTDT.repositories;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import api_CTDT.Models.SinhVien;

public interface SinhVienRepository extends JpaRepository<SinhVien, Integer>, JpaSpecificationExecutor<SinhVien> {
	@Query(value = "SELECT * FROM sinhvien WHERE masv=:ma_Sv", nativeQuery = true)
	SinhVien getByMaSV(@Param("ma_Sv") String maSV);
}
