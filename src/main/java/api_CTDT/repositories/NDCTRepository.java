package api_CTDT.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import api_CTDT.Models.NoiDungChuongTrinh;


public interface NDCTRepository extends JpaRepository<NoiDungChuongTrinh, Integer>, JpaSpecificationExecutor<NoiDungChuongTrinh>{
	@Query(value = "SELECT * FROM noidung_chuongtrinh WHERE id_chuongtrinh=:idct AND hoc_ky_du_kien=:hocky", nativeQuery = true) 
	List<NoiDungChuongTrinh> getListNDCT(@Param("idct") int idct, @Param("hocky") int hocky);
	
	@Query(value = "SELECT * FROM noidung_chuongtrinh WHERE ma_hoc_phan=:mhp", nativeQuery = true) 
	List<NoiDungChuongTrinh> getByMa_hoc_phan( @Param("mhp") String mhp);
	
	@Query(value = "SELECT * FROM noidung_chuongtrinh WHERE id_chuongtrinh=:idct", nativeQuery = true) 
	List<NoiDungChuongTrinh> getByIdct( @Param("idct") Integer idct);


}
