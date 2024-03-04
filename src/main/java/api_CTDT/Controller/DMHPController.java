package api_CTDT.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import api_CTDT.Models.DanhMucHocPhan;
import api_CTDT.services.DMHPService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

@RestController
@RequestMapping("/api/dmhp")
public class DMHPController {
	@Autowired
	private DMHPService dmhpService;

	@GetMapping
	public ResponseEntity<Object> getAll() {
		return ResponseEntity.ok(dmhpService.getAll());
	}

	@RequestMapping(method = RequestMethod.POST, path = "/create", produces = "application/json; charset=utf-8")
	@ResponseBody
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public ResponseEntity<Object> create(@RequestBody DanhMucHocPhan dmhp) {
		DanhMucHocPhan dmhpc = dmhpService.create(dmhp);
		if(dmhpc==null) {
			return null;
		}
		return ResponseEntity.ok(dmhp);
	}

	@GetMapping("/{id}")
	public ResponseEntity<Object> getByID(@Valid @NotNull @PathVariable("id") int id) {
		return ResponseEntity.ok(dmhpService.getByID(id));
	}

	@RequestMapping(method = RequestMethod.PUT, path = "/edit/{id}", produces = "application/json; charset=utf-8")
	@ResponseBody
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public ResponseEntity<Object> edit(@PathVariable Integer id, @RequestBody DanhMucHocPhan dmhp) {
		DanhMucHocPhan dmhpc = dmhpService.edit(id, dmhp);
		if(dmhpc==null) {
			return null;
		}
		return ResponseEntity.ok(dmhpService.getByID(id));
	}

	@GetMapping("/searchByLHP")
	public ResponseEntity<Object> getByidLHP(@RequestParam(required = false) int idlhp) {
		return ResponseEntity.ok(dmhpService.getByidLHP(idlhp));
	}

	@GetMapping("/search")
	public ResponseEntity<Object> getByKey(@RequestParam(required = false) String mhp,
			@RequestParam(required = false) String thp) {
		if (mhp != null)
			return ResponseEntity.ok(dmhpService.getByMaHocPhan(mhp));

		if (thp != null) {
			return ResponseEntity.ok(dmhpService.getByTenHocPhan(thp));
		}
		
		return ResponseEntity.ok(dmhpService.getByTenHocPhan(thp));

	}
	@GetMapping("/searchByKey")
	public ResponseEntity<Object> seachByKey(
			@RequestParam(required = false) String key, @RequestParam(required = false) Integer idlhp, @RequestParam(required = false) Integer iddmlhp){
		return ResponseEntity.ok(dmhpService.getByKey(key, idlhp,iddmlhp));
	}
	
	@GetMapping("/getByMaHP/{maHP}") 
	public ResponseEntity<DanhMucHocPhan> getByMaHP(@PathVariable String maHP) {
		return ResponseEntity.ok(dmhpService.getByMaHP(maHP));
	}
}
