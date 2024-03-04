package api_CTDT.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import api_CTDT.Models.NoiDungChuongTrinh;
import api_CTDT.services.NDCTService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

@Controller
@RequestMapping("/api/ndct")
public class NDCTController {
	@Autowired
	private NDCTService ndctService;
	@GetMapping
	public ResponseEntity<Object> getAll() {
		return ResponseEntity.ok(ndctService.getAll());
	}

	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@RequestMapping(method = RequestMethod.POST, path = "/create", produces = "application/json; charset=utf-8")
	@ResponseBody
	public ResponseEntity<Object> create(@RequestBody @Valid NoiDungChuongTrinh ndct) {
		ndctService.create(ndct);
		return ResponseEntity.ok(ndct);
	}

	@GetMapping("/{id}")
	public ResponseEntity<Object> getByID(@Valid @NotNull @PathVariable("id") int id) {
		return ResponseEntity.ok(ndctService.getByID(id));
	}

	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@RequestMapping(method = RequestMethod.PUT, path = "/edit/{id}", produces = "application/json; charset=utf-8")
	@ResponseBody
	public ResponseEntity<Object> edit(@PathVariable Integer id, @RequestBody @Valid NoiDungChuongTrinh ndct) {
		ndctService.edit(ndct,id);
		return ResponseEntity.ok(ndctService.getByID(id));
	}

//	@GetMapping("/search")
//	public ResponseEntity<Object> getByNganhKKT(@RequestParam(required = false) Integer idNganh,@RequestParam(required = false) Integer idChuyenNganh,
//			@RequestParam(required = false) Integer idKkt) {
//		return ResponseEntity.ok(ndctService.findByNganhKKT(idNganh, idChuyenNganh,idKkt));
//	}

	@GetMapping("/getndctsv") 
	public ResponseEntity<List<NoiDungChuongTrinh>> getNDCTSV(@RequestParam(value = "idct") int idct, @RequestParam(value = "hocky") int hocky) {
		return ResponseEntity.ok(ndctService.getListNDCTSV(idct, hocky));
	}
	
}
