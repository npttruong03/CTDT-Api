package api_CTDT.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import api_CTDT.Models.DinhHuong;
import api_CTDT.services.DinhHuongService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

@RestController
@RequestMapping("/api/dinhhuong")
public class DinhHuongController {
	@Autowired
	private DinhHuongService dinhHuongService;
	@GetMapping
	public ResponseEntity<Object> getAll(){
		return ResponseEntity.ok(dinhHuongService.getAll());
	}
	@GetMapping("/{id}")
	public ResponseEntity<Object> getById(@Valid @NotNull @PathVariable("id") int id){
		return ResponseEntity.ok(dinhHuongService.getById(id));
	}
	@RequestMapping(method = RequestMethod.POST, path = "/create", produces = "application/json; charset=utf-8")
	@ResponseBody
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public ResponseEntity<Object> post(@RequestBody @Valid DinhHuong dh){
		dinhHuongService.create(dh);
		return ResponseEntity.ok(dh);
	}
	@RequestMapping(method = RequestMethod.PUT, path = "/edit/{id}", produces = "application/json; charset=utf-8")
	@ResponseBody
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public ResponseEntity<Object> put(@PathVariable int id, @RequestBody @Valid DinhHuong dh){
		dinhHuongService.edit(dh, id);
		return ResponseEntity.ok(dinhHuongService.getById(id));
	}
}
