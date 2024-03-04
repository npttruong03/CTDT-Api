package api_CTDT.repositories;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import api_CTDT.Models.LinhVuc;
@Repository
public interface LinhVucRepository extends JpaRepository<LinhVuc, Integer> {
	@Query("SELECT lv FROM LinhVuc lv WHERE lv.ten_linh_vuc = :keyword OR lv.ma_linh_vuc = :keyword")
	List<LinhVuc> searchByKeyword(@Param("keyword") String keyword);
}