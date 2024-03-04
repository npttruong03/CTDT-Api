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

import api_CTDT.Models.MQHHocPhan;
import api_CTDT.services.MoiQuanHeHPServiceAPI;


@Controller
@RequestMapping("/api/mqhhp")
public class MoiQuanHeHPControllerAPI {
	@Autowired
	MoiQuanHeHPServiceAPI mqhhpService;
	
	@GetMapping("/getAll")
	public ResponseEntity<List<MQHHocPhan>> getAll() {
		List<MQHHocPhan> listMQHHP = mqhhpService.getAll();
		return ResponseEntity.ok(listMQHHP);
	}
	
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@PostMapping(path = "/create",  produces = "application/json; charset=utf-8" )
	public ResponseEntity<Boolean> themMQHHP(@RequestBody MQHHocPhan mqhhp) {
		Boolean result = mqhhpService.themMQHHP(mqhhp);
		return ResponseEntity.ok(result);
	}
	
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@PutMapping(path = "/update",  produces = "application/json; charset=utf-8" )
	public ResponseEntity<Boolean> capNhatMQHHP(@RequestBody MQHHocPhan mqhhp) {
		Boolean result = mqhhpService.capNhatMQHHP(mqhhp);
		return ResponseEntity.ok(result);
	}
	
	@GetMapping("/get/{id}")
	public ResponseEntity<MQHHocPhan> layMQHHP(@PathVariable Integer id) {
		MQHHocPhan mqhhp = mqhhpService.layMQHHP(id);
		return ResponseEntity.ok(mqhhp);
	}
	
	@GetMapping("/getmqh/{mhp}")
	public ResponseEntity<Object> layMQHHPByMhp(@PathVariable String mhp) {
		List<MQHHocPhan>  mqhhp = mqhhpService.getMqhHocPhan(mhp);
		return ResponseEntity.ok(mqhhp);
	}
}
