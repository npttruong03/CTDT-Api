package api_CTDT.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import api_CTDT.Models.ChuongTrinh;
import api_CTDT.Models.LinhVuc;
import api_CTDT.Models.SinhVien;
import api_CTDT.services.LinhVucService;
import jakarta.validation.Valid;




@RestController
@RequestMapping("/api/field")
public class LinhVucAPIController {
	@Autowired
	private LinhVucService fieldService;

	@GetMapping()
	public ResponseEntity<Object> findAll(){
		return ResponseEntity.ok(fieldService.findAll());
	}
	
	@GetMapping("/search")
	public ResponseEntity<Object> findByKey(@RequestParam(value = "keyword", required  = false) String Key){
		return ResponseEntity.ok(fieldService.searchByKeyword(Key));
	}
	
	
	
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	 @PostMapping(path = "/create", produces = "application/json; charset=utf-8")
	    public ResponseEntity<Boolean> taomoilinhvuc(@RequestBody LinhVuc linhvuc) {
	       Boolean result = fieldService.save(linhvuc);
	        return ResponseEntity.ok(result);
	    }
	  
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	    @RequestMapping(method = RequestMethod.PUT, path = "/edit/{id}", consumes = "application/json", produces = "application/json; charset=utf-8")
	    public ResponseEntity<Boolean> edit( @RequestBody @Valid LinhVuc linhvuc) {
	          Boolean rs =  fieldService.update( linhvuc);
	           return ResponseEntity.ok(rs);
	        } 
}
