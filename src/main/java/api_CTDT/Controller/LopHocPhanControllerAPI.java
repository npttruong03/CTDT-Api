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

import api_CTDT.Models.LopHocPhan;
import api_CTDT.services.LopHocPhanService;


@Controller
@RequestMapping("/api/lophocphan")
public class LopHocPhanControllerAPI {
	@Autowired
	LopHocPhanService lopHocPhanService;
	
	@GetMapping("/getAll")
	public ResponseEntity<List<LopHocPhan>> getAll() {
		List<LopHocPhan> listLHP = lopHocPhanService.getAll();
		return ResponseEntity.ok(listLHP);
	}
	
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@PostMapping(path = "/create",  produces = "application/json; charset=utf-8" )
	public ResponseEntity<Boolean> themLopHocPhan(@RequestBody LopHocPhan lopHocPhan) {
		Boolean result = lopHocPhanService.themLopHocPhan(lopHocPhan);
		return ResponseEntity.ok(result);
	}
	
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@PutMapping(path = "/update",  produces = "application/json; charset=utf-8" )
	public ResponseEntity<Boolean> capNhatLopHocPhan(@RequestBody LopHocPhan lopHocPhan) {
		Boolean result = lopHocPhanService.capNhatLopHocPhan(lopHocPhan);
		return ResponseEntity.ok(result);
	}
	
	@GetMapping("/get/{id}")
	public ResponseEntity<LopHocPhan> layLapHocPhan(@PathVariable int id) {
		LopHocPhan loaiHocPhan = lopHocPhanService.layLopHocPhan(id);
		return ResponseEntity.ok(loaiHocPhan);
	}
	
	@GetMapping("/getlistlhp/{maHP}") 
	public ResponseEntity<List<LopHocPhan>> layListLHP(@PathVariable String maHP) {
		List<LopHocPhan> list = lopHocPhanService.layListLopHocPhan(maHP);
		return ResponseEntity.ok(list);
	}
	
}
