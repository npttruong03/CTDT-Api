package api_CTDT.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import api_CTDT.Models.DanhMucLHP;
import api_CTDT.services.DanhMucLHPService;

@RestController
@RequestMapping("/api/dmloaihp")
public class DanhMucLHPController {
	@Autowired
	private DanhMucLHPService dmlhpService;

	@GetMapping("/getAll")
	public ResponseEntity<List<DanhMucLHP>> getAll() {
		List<DanhMucLHP> listLHP = dmlhpService.getAll();
		return ResponseEntity.ok(listLHP);
	}
	
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@PostMapping(path = "/create",  produces = "application/json; charset=utf-8" )
	public ResponseEntity<Boolean> themDMLHP(@RequestBody DanhMucLHP danhMucLHP) {
		Boolean result = dmlhpService.themDMLHP(danhMucLHP);
		return ResponseEntity.ok(result);
	}
	
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@PutMapping(path = "/update",  produces = "application/json; charset=utf-8" )
	public ResponseEntity<Boolean> capNhatDMLHP(@RequestBody DanhMucLHP danhMucLHP) {
		Boolean result = dmlhpService.capNhatDMLHP(danhMucLHP);
		return ResponseEntity.ok(result);
	}
	
	@GetMapping("/get/{id}")
	public ResponseEntity<DanhMucLHP> layDMLHP(@PathVariable int id) {
		DanhMucLHP dmlhp = dmlhpService.layDMLHP(id);
		return ResponseEntity.ok(dmlhp);
	}
}
