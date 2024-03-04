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
import api_CTDT.Models.Nganh;
import api_CTDT.repositories.LogRepository;
import api_CTDT.repositories.NganhRepository;

@Service
public class NganhService {
	@Autowired
	private NganhRepository nganhRepository;
	@Autowired
	LogRepository logRepository;
	
	public List<Nganh> findAll() {
		return nganhRepository.findAll();
	}
	public Boolean save(Nganh nganh) {		
		nganh = nganhRepository.save(nganh);
		if (nganh != null) {
			Log log = new Log();
			log.setLogString("Thêm mới ngành id = " + nganh.getId());
			log.setCreateTime(new Timestamp(System.currentTimeMillis()));
			logRepository.save(log);
			return true;
		}	
	 	return false;
	}
	
	private Nganh requireOne(Integer id) {
        return nganhRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Resource not found: " + id));
    }
	
	public Boolean update(Nganh nganhu) {
		Nganh nganh = nganhRepository.save(nganhu);
		if (nganh != null) {
			Log log = new Log();
			log.setLogString("Cập nhật ngành id = " + nganhu.getId());
			log.setCreateTime(new Timestamp(System.currentTimeMillis()));
			logRepository.save(log);
			return true;
		}
		return false;
		}
	
	 public Nganh getById(Integer id) {
		 	Nganh original = requireOne(id);
	        return original;
	    }
	 public List<Nganh> searchByKeyword(String Keyword) {
		 return nganhRepository.searchByKeyword(Keyword);
	 }
	 
}
