package api_CTDT.repositories;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import api_CTDT.Models.ChuyenNganh;
import api_CTDT.Models.Nganh;
public interface NganhRepository extends JpaRepository<Nganh, Integer>, JpaSpecificationExecutor<Nganh>{
	@Query("SELECT n FROM Nganh n WHERE n.ten_nganh LIKE %:keyword% OR n.ma_nganh LIKE %:keyword%")
	List<Nganh> searchByKeyword(@Param("keyword") String searchTerm);

}
