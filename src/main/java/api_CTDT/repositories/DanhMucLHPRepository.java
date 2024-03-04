package api_CTDT.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import api_CTDT.Models.DanhMucLHP;

public interface DanhMucLHPRepository extends JpaRepository<DanhMucLHP, Integer>, JpaSpecificationExecutor<DanhMucLHP> {

}
