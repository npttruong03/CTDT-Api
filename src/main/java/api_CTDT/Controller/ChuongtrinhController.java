package api_CTDT.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import api_CTDT.Models.ChuongTrinh;
import api_CTDT.services.ChuongtrinhService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

@RestController
@RequestMapping("api/chuongtrinh")
public class ChuongtrinhController {
	@Autowired
	private ChuongtrinhService chuongtrinhService;
	
	@GetMapping
	public ResponseEntity<Object> findAll(){
		return ResponseEntity.ok(chuongtrinhService.findAll());
	}
	
	@PostMapping("/create")
	public ResponseEntity<Object> createreligion(@RequestBody @Valid ChuongTrinh chuongTrinh){
		chuongtrinhService.save(chuongTrinh);
		return ResponseEntity.ok(chuongTrinh);
	}
	@PutMapping("/edit/{id}") 
	public ResponseEntity<Object> update (@RequestBody @Valid ChuongTrinh chuongtrinhDTO, @Valid @NotNull @PathVariable("id") int id ) {
		chuongtrinhDTO.setId(id);
		this.chuongtrinhService.update(chuongtrinhDTO);
		return ResponseEntity.ok().body(chuongtrinhService.getById(id));	}
	
	 @GetMapping("/search")
	 public ResponseEntity<Object> findByKey(@RequestParam(value = "keyword", required  = false) String Key){
			return ResponseEntity.ok(chuongtrinhService.searchByKeyword(Key));
		}
	 @GetMapping("/{id}")
	 public ResponseEntity<Object> getID(@Valid @NotNull @PathVariable("id") int Key){
			return ResponseEntity.ok(chuongtrinhService.getById(Key));
		}
	 
	 @GetMapping("/getctsv")
	 public ResponseEntity<ChuongTrinh> getCTSV(@RequestParam(value = "idnganh") Integer idnganh, @RequestParam(value = "idcn") Integer idcn, @RequestParam(value = "khoa") String khoa)
	 {
		 return ResponseEntity.ok(chuongtrinhService.getByNganhAndChuyenNganhAndKhoa(idnganh, idcn, khoa));
	 }
}
