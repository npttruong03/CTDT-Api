package api_CTDT.Controller;

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
import api_CTDT.Models.KhoaQuanLy;
import api_CTDT.services.KhoaQuanLyService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

@RestController
@RequestMapping("/api/khoaquanly")
public class KhoaQuanLyController {

	@Autowired
	private KhoaQuanLyService khoaQuanLyService;
	
	@GetMapping()
	public ResponseEntity<Object> findAlls() {
		return ResponseEntity.ok(khoaQuanLyService.findAll());
	}
	
	@RequestMapping(method = RequestMethod.POST, path = "/add", produces = "application/json; charset=utf-8")
    @ResponseBody
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public ResponseEntity<KhoaQuanLy> insert(@RequestBody @Valid KhoaQuanLy khoaQuanLy){
		khoaQuanLyService.save(khoaQuanLy);
		return ResponseEntity.ok(khoaQuanLy);
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
	@GetMapping("/{id}")
	public ResponseEntity<Object> getByID(@Valid @NotNull @PathVariable("id") int id) {
		return ResponseEntity.ok(khoaQuanLyService.getById(id));
	}
	
	
	@PutMapping("/update/{id}")
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public ResponseEntity<KhoaQuanLy> update(@RequestBody @Valid KhoaQuanLy khoaQuanLyDto, @PathVariable @Valid int id) {
		String message = "";
		khoaQuanLyDto.setId(id);
		KhoaQuanLy editChuyenNganh = khoaQuanLyService.Update(id, khoaQuanLyDto);
//			if (khoaQuanLyDto.get == false) {
//				khoaQuanLyDto editChuyenNganh = khoaQuanLyService.Update(id, khoaQuanLyDto);
//				message = "thêm thành công";
////				return ResponseEntity.ok().body(editChuyenNganh);
//			} else {
//				message = "đã được sử dụng, không thể sữa";
//			}
			return ResponseEntity.ok().body(editChuyenNganh);
		}
		
	@GetMapping("/search")
	 public ResponseEntity<Object> findByKey(@RequestParam(value = "keyword", required  = false) String Key){
			return ResponseEntity.ok(khoaQuanLyService.searchByKeyword(Key));
		}
}
