package api_CTDT.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import api_CTDT.Models.ERole;
import api_CTDT.Models.Role;

public interface roleRepository extends JpaRepository<Role, Integer>, JpaSpecificationExecutor<Role> {
	Optional<Role> findByName(ERole name);
}
