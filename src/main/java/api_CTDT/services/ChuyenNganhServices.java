package api_CTDT.services;

import java.sql.Timestamp;
import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import api_CTDT.Models.ChuongTrinh;
import api_CTDT.Models.ChuyenNganh;
import api_CTDT.Models.Log;
import api_CTDT.repositories.ChuyenNganhRepository;
import api_CTDT.repositories.LogRepository;
@Service
public class ChuyenNganhServices {
	@Autowired
	private ChuyenNganhRepository chuyenNganhRepository;
	@Autowired
	private LogRepository logRepository;
	private ChuyenNganh requireOne(Integer id) {
		return chuyenNganhRepository.findById(id).orElseThrow(() -> new NoSuchElementException("Resource not found: " + id));
	}
	
	public List<ChuyenNganh> findAll() {
		
		return chuyenNganhRepository.findAll();
	}
	
	public Boolean save(ChuyenNganh chuyennganh) {
		chuyennganh.setId(0);
		ChuyenNganh chuyenNganhMoi = chuyenNganhRepository.save(chuyennganh);
		if (chuyenNganhMoi != null) {
			Log log = new Log();
			log.setLogString("Thêm mới chuyên ngành id = " + chuyenNganhMoi.getId());
			log.setCreateTime(new Timestamp(System.currentTimeMillis()));
			logRepository.save(log);
			return true;
		}
		return false;
	}
	
	public Boolean Update(ChuyenNganh chuyenNganh) {
		Integer dem = chuyenNganhRepository.countSVByChuyenNganh(chuyenNganh.getId());
		if (dem > 0) {
			return false;
		}
		ChuyenNganh chuyenNganhCapNhat =  chuyenNganhRepository.save(chuyenNganh);
		if (chuyenNganhCapNhat != null) {
			Log log = new Log();
			log.setLogString("Cập nhật chuyên ngành id = " + chuyenNganh.getId());
			log.setCreateTime(new Timestamp(System.currentTimeMillis()));
			logRepository.save(log);
			return true;
		}
		return false;
	}
	
	public ChuyenNganh getById(Integer id) {
		ChuyenNganh original = requireOne(id);
		return original;
	}
	public List<ChuyenNganh> searchByKeyword(String Keyword) {
		 return chuyenNganhRepository.searchByKeyword(Keyword);
	 }
	
	 public List<ChuyenNganh> getListChuyenNganh(int id) {
		 return chuyenNganhRepository.getListChuyenNganh(id);
	 }

}
