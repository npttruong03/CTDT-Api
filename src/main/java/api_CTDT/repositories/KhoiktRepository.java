package api_CTDT.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import api_CTDT.Models.KhoiKienThuc;

public interface KhoiktRepository extends JpaRepository<KhoiKienThuc, Integer>, JpaSpecificationExecutor<KhoiKienThuc>{

}
