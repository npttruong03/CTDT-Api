package api_CTDT.services;

import java.sql.Timestamp;
import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import api_CTDT.Models.ChuongTrinh;
import api_CTDT.Models.LinhVuc;
import api_CTDT.Models.Log;
import api_CTDT.repositories.ChuongtrinhRepository;
import api_CTDT.repositories.LogRepository;

@Service
public class ChuongtrinhService {
	@Autowired
	private ChuongtrinhRepository chuongtrinhRepository;
	@Autowired
	LogRepository logRepository;
	
	public List<ChuongTrinh> findAll() {
		return chuongtrinhRepository.findAll();
	}
	public Boolean save(ChuongTrinh chuongtrinh) {
		chuongtrinh = chuongtrinhRepository.save(chuongtrinh);
		if (chuongtrinh != null) {
			Log log = new Log();
			log.setLogString("Thêm mới chương trình id = " + chuongtrinh.getId());
			log.setCreateTime(new Timestamp(System.currentTimeMillis()));
			logRepository.save(log);
			return true;
		}	
	 	return false;
	}
	
	private ChuongTrinh requireOne(Integer id) {
        return chuongtrinhRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Resource not found: " + id));
    }

	public Boolean update(ChuongTrinh chuongtrinhu) {
		ChuongTrinh chuongtrinh = chuongtrinhRepository.save(chuongtrinhu);
		if (chuongtrinh != null) {
			Log log = new Log();
			log.setLogString("Cập nhật chương trình id = " + chuongtrinhu.getId());
			log.setCreateTime(new Timestamp(System.currentTimeMillis()));
			logRepository.save(log);
			return true;
		}
		return false;
		}
		
	 public ChuongTrinh getById(Integer id) {
		 	ChuongTrinh original = requireOne(id);
	        return original;
	    }
	 public List<ChuongTrinh> searchByKeyword(String Keyword) {
		 return chuongtrinhRepository.timTheoTuKhoa(Keyword);
	 }
	 
	 public ChuongTrinh getByNganhAndChuyenNganhAndKhoa(Integer idnganh, Integer idCN, String khoa) {
		 List<ChuongTrinh> listChuongTrinh = chuongtrinhRepository.getByNganhAndChuyenNganhAndKhoa(idnganh, idCN, khoa);
		 if (listChuongTrinh.get(0) != null) {
			 return listChuongTrinh.get(0);
		 }
		 return null;
	 }
}
