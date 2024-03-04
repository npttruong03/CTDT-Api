package api_CTDT.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import api_CTDT.Models.ChuyenNganh;
import api_CTDT.Models.KhoaQuanLy;
import api_CTDT.Models.LinhVuc;

@Repository
public interface KhoaQuanLyRepository extends JpaRepository<KhoaQuanLy, Integer>, JpaSpecificationExecutor<KhoaQuanLy>  {
	@Query("SELECT k FROM KhoaQuanLy k WHERE k.ten_khoa = :keyword")
	List<KhoaQuanLy> searchByKeyword(@Param("keyword") String keyword);
}
