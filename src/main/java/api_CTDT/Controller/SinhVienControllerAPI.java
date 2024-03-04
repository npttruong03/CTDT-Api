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

import api_CTDT.Models.SinhVien;
import api_CTDT.services.SinhVienService;


@Controller
@RequestMapping("/api/sinhvien")
public class SinhVienControllerAPI {
	@Autowired
	SinhVienService sinhVienService;
	
	@GetMapping("/getAll")

	public ResponseEntity<List<SinhVien>> getAll() {
		List<SinhVien> listSinhVien = sinhVienService.getAll();
		return ResponseEntity.ok(listSinhVien);
	}
	
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@PostMapping(path = "/create",  produces = "application/json; charset=utf-8" )
	public ResponseEntity<Boolean> themSinhVien(@RequestBody SinhVien sinhVien) {
		sinhVien.setHocKyHienTai(1);
		Boolean result = sinhVienService.themSinhVien(sinhVien);
		return ResponseEntity.ok(result);
	}
	
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@PutMapping(path = "/update",  produces = "application/json; charset=utf-8" )
	public ResponseEntity<Boolean> capNhatSinhVien(@RequestBody SinhVien sinhVien) {
		Boolean result = sinhVienService.capNhatSinhVien(sinhVien);
		return ResponseEntity.ok(result);
	}
	
	@GetMapping("/get/{id}")
	public ResponseEntity<SinhVien> laySinhVien(@PathVariable int id) {
		SinhVien sinhVien = sinhVienService.laySinhVien(id);
		return ResponseEntity.ok(sinhVien);
	}
}
