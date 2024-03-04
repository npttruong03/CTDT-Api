package api_CTDT.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;



import api_CTDT.Models.ChuyenNganh;
import api_CTDT.payload.response.Message;
import api_CTDT.services.ChuyenNganhServices;
import api_CTDT.services.NganhService;
import api_CTDT.services.SinhVienService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

@RestController
@RequestMapping("/api/chuyennganh")
public class ChuyenNganhController {
	@Autowired
	private ChuyenNganhServices chuyenNganhServices;
	@Autowired
	private SinhVienService sinhVienService;
	
	@GetMapping()
	public ResponseEntity<Object> findAlls() {
		return ResponseEntity.ok(chuyenNganhServices.findAll());
	}
	
	@GetMapping("/{id}")
	
	public ResponseEntity<Object> getByID(@Valid @NotNull @PathVariable("id") int id) {
		return ResponseEntity.ok(chuyenNganhServices.getById(id));
	}
	
	@RequestMapping(method = RequestMethod.POST, path = "/add", produces = "application/json; charset=utf-8")
    @ResponseBody
    @PreAuthorize("hasRole('ROLE_ADMIN')")
	public ResponseEntity<Boolean> insert(@RequestBody @Valid ChuyenNganh chuyennganh){
		Boolean rs = chuyenNganhServices.save(chuyennganh);
		return ResponseEntity.ok(rs);
	}
	
//	@PutMapping("/update/{id}")
//	public ResponseEntity<ChuyenNganh> update(@RequestBody @Valid ChuyenNganh chuyenNganhDto, @PathVariable @Valid int id) {
//		List<ChuyenNganh> list = chuyenNganhServices.findAll();
//		String message = "";
//		for (ChuyenNganh chuyenNganh : list) {
//			if (chuyenNganh.getStt() == false) {
//				chuyenNganhDto.setId(id);
//				ChuyenNganh editChuyenNganh = chuyenNganhServices.Update(id, chuyenNganhDto);
//				return ResponseEntity.ok().body(editChuyenNganh);
//			} else {
//				message = "đã được sử dụng, không thể sữa";
//				return message;
//			}
//		}
//		
//	}
	
	
	@PutMapping("/update/{id}")
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public ResponseEntity<Boolean> update(@RequestBody @Valid ChuyenNganh chuyenNganhDto, @PathVariable @Valid int id) {
		chuyenNganhDto.setId(id);
		Boolean rs = chuyenNganhServices.Update(chuyenNganhDto);
		return ResponseEntity.ok(rs);
		}
		
	@GetMapping("/search")
	 public ResponseEntity<Object> findByKey(@RequestParam(value = "keyword", required  = false) String Key){
			return ResponseEntity.ok(chuyenNganhServices.searchByKeyword(Key));
		}
	
	@GetMapping("/getbymanganh/{id}") 
	public ResponseEntity<List<ChuyenNganh>> getListChuyenNganh(@PathVariable("id") int id) {
		return ResponseEntity.ok(chuyenNganhServices.getListChuyenNganh(id));
	}
}
