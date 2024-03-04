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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import api_CTDT.Models.ChuyenNganh;
import api_CTDT.Models.Nganh;
import api_CTDT.services.NganhService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

@RestController
@RequestMapping("api/nganh")
public class NganhController {
	@Autowired
	private NganhService nganhService;
	
	@GetMapping
	public ResponseEntity<Object> findAll(){
		return ResponseEntity.ok(nganhService.findAll());
	}
	
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@PostMapping("/create")
	public ResponseEntity<Object> createreligion(@RequestBody @Valid Nganh nganh){
		nganhService.save(nganh);
		return ResponseEntity.ok(nganh);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Object> getByID(@Valid @NotNull @PathVariable("id") int id) {
		return ResponseEntity.ok(nganhService.getById(id));
	}
	
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@PutMapping("/edit/{id}") 
	public ResponseEntity<Object> update (@RequestBody @Valid Nganh nganhDTO, @Valid @NotNull @PathVariable("id") int id ) {
		nganhDTO.setId(id);
		this.nganhService.update(nganhDTO);
		return ResponseEntity.ok().body(nganhService.getById(id));	}
	
	@GetMapping("/search")
	public ResponseEntity<Object> findByKey(@RequestParam(value = "keyword", required  = false) String Key){
		return ResponseEntity.ok(nganhService.searchByKeyword(Key));
	}

	
}
