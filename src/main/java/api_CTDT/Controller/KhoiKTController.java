package api_CTDT.Controller;

import org.springframework.beans.factory.annotation.Autowired;
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

import api_CTDT.Models.KhoiKienThuc;
import api_CTDT.services.KhoiKTService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

@Controller
@RequestMapping("/api/kkt")
public class KhoiKTController {
	@Autowired
	private KhoiKTService khoiKTService;
	
	@GetMapping
	public ResponseEntity<Object> getAll() {
		return ResponseEntity.ok(khoiKTService.getAll());
	}

	@RequestMapping(method = RequestMethod.POST, path = "/create", produces = "application/json; charset=utf-8")
	@ResponseBody
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public ResponseEntity<Object> create(@RequestBody @Valid KhoiKienThuc kkt) {
		khoiKTService.create(kkt);
		return ResponseEntity.ok(kkt);
	}

	@GetMapping("/{id}")
	public ResponseEntity<Object> getByID(@Valid @NotNull @PathVariable("id") int id) {
		return ResponseEntity.ok(khoiKTService.getByID(id));
	}

	@RequestMapping(method = RequestMethod.PUT, path = "/edit/{id}", produces = "application/json; charset=utf-8")
	@ResponseBody
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public ResponseEntity<Object> edit(@PathVariable Integer id, @RequestBody @Valid KhoiKienThuc kkt) {
		khoiKTService.edit(kkt,id);
		return ResponseEntity.ok(khoiKTService.getByID(id));
	}

	@GetMapping("/search")
	public ResponseEntity<Object> getNameKKT(@RequestParam(required = false) String name) {
		return ResponseEntity.ok(khoiKTService.getByName(name));
	}

	
}
