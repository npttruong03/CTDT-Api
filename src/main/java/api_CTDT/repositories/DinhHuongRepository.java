package api_CTDT.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import api_CTDT.Models.DinhHuong;

public interface DinhHuongRepository extends JpaRepository<DinhHuong, Integer>, JpaSpecificationExecutor<DinhHuong>{

}
