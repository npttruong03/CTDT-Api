package api_CTDT.Controller;


import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import api_CTDT.Models.ChuongTrinh;
import api_CTDT.Models.ChuyenNganh;
import api_CTDT.Models.DanhMucHocPhan;
import api_CTDT.Models.Nganh;
import api_CTDT.services.DHCTDaoTaoService;
import api_CTDT.services.NganhService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/admin/api")
public class DHCTDaoTaoAPIController {
    @Autowired
    private DHCTDaoTaoService dhctDaoTaoService;
    @Autowired
    private NganhService nganhService;
    public List<ChuongTrinh> chuyenDoiSangDTO(List<ChuongTrinh> danhSach) {
        List<ChuongTrinh> danhSachDTO = new ArrayList<>();
        for (ChuongTrinh chuongTrinh : danhSach) {
            ChuongTrinh chuongTrinhDTO = new ChuongTrinh();
            BeanUtils.copyProperties(chuongTrinh, chuongTrinhDTO);
            danhSachDTO.add(chuongTrinhDTO);
        }
        return danhSachDTO;
    }
    @GetMapping("/dhctdt")
    public ResponseEntity<Object> timTatCa() {
        List<ChuongTrinh> danhSachChuongTrinh = dhctDaoTaoService.findAll();
        return ResponseEntity.ok(chuyenDoiSangDTO(danhSachChuongTrinh));
    }
    
	@PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping(path = "/dhctdt/create", produces = "application/json; charset=utf-8")
    public ResponseEntity<Object> taoMoiChuongTrinh(@RequestBody @Valid ChuongTrinh chuongTrinh) {
        chuongTrinh = dhctDaoTaoService.save(chuongTrinh);
        return ResponseEntity.ok(chuongTrinh);
    }

	@PreAuthorize("hasRole('ROLE_ADMIN')")
    @RequestMapping(method = RequestMethod.PUT, path = "/dhctdt/edit/{id}", consumes = "application/json", produces = "application/json; charset=utf-8")
    public ResponseEntity<Object> edit(@PathVariable Integer id, @RequestBody @Valid ChuongTrinh chuongTrinh) {
            dhctDaoTaoService.edit(id, chuongTrinh);
           return ResponseEntity.ok(dhctDaoTaoService.getByID(id));
        } 
    
    @GetMapping("/dhctdt/search")
    public ResponseEntity<List<ChuongTrinh>> timChuongTrinhTheoNganhVaChuyenNganh(@RequestParam("ten_chuong_trinh") String tenChuongTrinh) {
        List<ChuongTrinh> danhSachChuongTrinh = dhctDaoTaoService.search(tenChuongTrinh);
        return ResponseEntity.ok(danhSachChuongTrinh);
    }
    
    @GetMapping("/dhctdt/getchuyennganh/{id}")
    @ResponseBody
    public Nganh getNganh(@PathVariable Integer id) {
		return nganhService.getById(id);
    	
    }
    
   
    
    
}
