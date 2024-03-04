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

import api_CTDT.Models.LoaiHocPhan;
import api_CTDT.services.LoaiHocPhanServiceAPI;

@Controller
@RequestMapping("/api/loaihocphan")
public class LoaiHocPhanControllerAPI {
	@Autowired
	LoaiHocPhanServiceAPI loaiHocPhanServiceAPI;
	
	@GetMapping("/getAll")
	public ResponseEntity<List<LoaiHocPhan>> getAll() {
		List<LoaiHocPhan> listLHP = loaiHocPhanServiceAPI.getAll();
		return ResponseEntity.ok(listLHP);
	}
	
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@PostMapping(path = "/create",  produces = "application/json; charset=utf-8" )
	public ResponseEntity<Boolean> themLHP(@RequestBody LoaiHocPhan loaiHocPhan) {
		Boolean result = loaiHocPhanServiceAPI.themLHP(loaiHocPhan);
		return ResponseEntity.ok(result);
	}
	
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@PutMapping(path = "/update",  produces = "application/json; charset=utf-8" )
	public ResponseEntity<Boolean> capNhatLHP(@RequestBody LoaiHocPhan loaiHocPhan) {
		Boolean result = loaiHocPhanServiceAPI.capNhatLHP(loaiHocPhan);
		return ResponseEntity.ok(result);
	}
	
	@GetMapping("/get/{id}")
	public ResponseEntity<LoaiHocPhan> layLHP(@PathVariable int id) {
		LoaiHocPhan loaiHocPhan = loaiHocPhanServiceAPI.layLHP(id);
		return ResponseEntity.ok(loaiHocPhan);
	}
}
