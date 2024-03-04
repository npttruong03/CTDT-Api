package api_CTDT.services;


import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ResponseStatus;

import api_CTDT.Models.ChuongTrinh;
import api_CTDT.Models.KhoiKienThuc;
import api_CTDT.Models.Nganh;
import api_CTDT.repositories.ChuongtrinhRepository;
import api_CTDT.repositories.KhoiktRepository;
import api_CTDT.repositories.NganhRepository;

@Service
public class DHCTDaoTaoService {

    @Autowired
    private ChuongtrinhRepository chuongTrinhRepository;
    @Autowired
    private KhoiktRepository khoiktRepository;
    @Autowired
    private NganhRepository nganhRepository;
    
    @Autowired
    private ChuyenNganhServices chuyenNganhServices;
    @Autowired
    private NganhService nganhService;
    
    public List<ChuongTrinh> findAll(){
        return chuongTrinhRepository.findAll();
    }
//    public ChuongTrinh save(ChuongTrinh chuongTrinh) {
//        Nganh nganh = chuongTrinh.getNganh();
//        chuongTrinh.setNganh(nganh);
//        ChuongTrinh ct = chuongTrinhRepository.save(chuongTrinh);
//        return ct;
//    }
 public ChuongTrinh save(ChuongTrinh chuongTrinh) {
    try {
    		Nganh nganh = chuongTrinh.getNganh();
    		chuongTrinh.setNganh(nganh);
            KhoiKienThuc khoiKienThuc = khoiktRepository.findById(chuongTrinh.getKhoiKienThuc().getId()).orElse(null);
            if (khoiKienThuc != null) {     
                chuongTrinh.setKhoiKienThuc(khoiKienThuc);
            }else {
            	 throw new ChuyenNganhOrKhoiKienThucNotFoundException("Không tìm thấy liên kết với khối kiến thức" );
            }
            ChuongTrinh ct = chuongTrinhRepository.save(chuongTrinh);
            return ct;
	} catch (Exception e) {
			e.printStackTrace();	
	}
		return null; 
}
	public ChuongTrinh getByID(int id) {
		List<ChuongTrinh> ct = findAll();
		for (ChuongTrinh ctct : ct) {
			if (ctct.getId() == id) {
				return ctct;
			}
		}
		return null;
	}
	public ChuongTrinh edit(int id, ChuongTrinh ct) {
		ChuongTrinh chuongTrinh = getByID(id);
		if (chuongTrinh == null) {
			return null;
		}
		chuongTrinh.setId_nganh(ct.getId_nganh());
		chuongTrinh.setMa_chuong_trinh(ct.getMa_chuong_trinh());
		chuongTrinh.setStt(ct.getStt());
		chuongTrinh.setId_chuyen_nganh(ct.getId_chuyen_nganh());
		chuongTrinh.setTen_chuong_trinh(ct.getTen_chuong_trinh());
		chuongTrinh = chuongTrinhRepository.save(chuongTrinh);
		return chuongTrinh;
	}
	
    public List<ChuongTrinh> search(String keyword) {
        return chuongTrinhRepository.timTheoMaNganhVaMaChuyenNganh(keyword);
    }
 // Ngoại lệ khi không tìm thấy Ngành
    @ResponseStatus(HttpStatus.NOT_FOUND)
    class NganhNotFoundException extends RuntimeException {
        public NganhNotFoundException(String message) {
            super(message);
        }
    }

    // Ngoại lệ khi không tìm thấy Chuyên Ngành hoặc Khối Kiến Thức
    @ResponseStatus(HttpStatus.NOT_FOUND)
    class ChuyenNganhOrKhoiKienThucNotFoundException extends RuntimeException {
        public ChuyenNganhOrKhoiKienThucNotFoundException(String message) {
            super(message);
        }
    }
}
