package api_CTDT.Controller;

import java.util.List;

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

import api_CTDT.Models.DanhMucHocPhan;
import api_CTDT.Models.HPThayThe;
import api_CTDT.Models.KhoiKienThuc;
import api_CTDT.services.DMHPService;
import api_CTDT.services.HPTTService;
import api_CTDT.services.KhoiKTService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

@Controller
@RequestMapping("/api/hptt")
public class HPTTController {
	@Autowired
	private HPTTService hpttService;
	@Autowired
	private DMHPService dmhpService;
	
	@GetMapping
	public ResponseEntity<Object> getAll() {
		return ResponseEntity.ok(hpttService.getAll());
	}

	@RequestMapping(method = RequestMethod.POST, path = "/create", produces = "application/json; charset=utf-8")
	@ResponseBody
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public ResponseEntity<Object> create(@RequestBody @Valid HPThayThe hptt) {
		hpttService.create(hptt);
		return ResponseEntity.ok(hptt);
	}

	@GetMapping("/{id}")
	public ResponseEntity<Object> getByID(@Valid @NotNull @PathVariable("id") int id) {
		return ResponseEntity.ok(hpttService.getByID(id));
	}

	@RequestMapping(method = RequestMethod.PUT, path = "/edit/{id}", produces = "application/json; charset=utf-8")
	@ResponseBody
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public ResponseEntity<Object> edit(@PathVariable Integer id, @RequestBody @Valid HPThayThe hptt) {
		hpttService.edit(hptt,id);
		return ResponseEntity.ok(hpttService.getByID(id));
	}

	@GetMapping("/search")
	public ResponseEntity<Object> getByKey(@RequestParam(required = false) String mhp,
			@RequestParam(required = false) String thp) {
		if (mhp != null)
			return ResponseEntity.ok(hpttService.getByMaHocPhan(mhp));

		if (thp != null) {
			return ResponseEntity.ok(hpttService.getByName(thp));
		}
		return ResponseEntity.ok(hpttService.getByName(thp));

	}
	@GetMapping("/search/{mhp}")
	public ResponseEntity<Object> getBymhp(@PathVariable String mhp) {
		
			return ResponseEntity.ok(hpttService.getmhp(mhp));


	}
	@GetMapping("/recommend/{maHocPhan}")
    public ResponseEntity<List<DanhMucHocPhan>> recommendCourses(@PathVariable String maHocPhan) {
//        DanhMucHocPhan dmhp = dmhpService.getByMaHP(maHocPhan);
//
//        if (dmhp == null) {
//            return ResponseEntity.notFound().build();
//        }

        List<DanhMucHocPhan> recommendedCourses = hpttService.recommendCourse(maHocPhan);
        return ResponseEntity.ok(recommendedCourses);
    }

	
}
