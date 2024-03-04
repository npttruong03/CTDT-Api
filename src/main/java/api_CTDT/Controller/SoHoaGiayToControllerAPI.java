package api_CTDT.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import api_CTDT.Models.SoHoaGiayTo;
import api_CTDT.services.SoHoaGiayToServiceAPI;


@Controller
@RequestMapping("/api/sohoagiayto")

public class SoHoaGiayToControllerAPI {
	@Autowired
	SoHoaGiayToServiceAPI soHoaGiayToServiceAPI;
	
	@GetMapping("/getAll")
	public ResponseEntity<List<SoHoaGiayTo>> getAll() {
		List<SoHoaGiayTo> listSHGT = soHoaGiayToServiceAPI.getAll();
		return ResponseEntity.ok(listSHGT);
	}
	
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@PostMapping(path = "/create",  produces = "application/json; charset=utf-8" )
	public ResponseEntity<Boolean> themSoHoaGiayTo(@RequestBody SoHoaGiayTo soHoaGiayTo) {
		Boolean result = soHoaGiayToServiceAPI.themSoHoaGiayTo(soHoaGiayTo);
		return ResponseEntity.ok(result);
	}
	
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@PutMapping(path = "/update",  produces = "application/json; charset=utf-8" )
	public ResponseEntity<Boolean> capNhatSoHoaGiayTo(@RequestBody SoHoaGiayTo soHoaGiayTo) {
		Boolean result = soHoaGiayToServiceAPI.capNhatSoHoaGiayTo(soHoaGiayTo);
		return ResponseEntity.ok(result);
	}
	
	@GetMapping("/get/{id}")
	public ResponseEntity<SoHoaGiayTo> layGiayTo(@PathVariable int id) {
		SoHoaGiayTo soHoaGiayTo = soHoaGiayToServiceAPI.layGiayTo(id);
		return ResponseEntity.ok(soHoaGiayTo);
	}
}
