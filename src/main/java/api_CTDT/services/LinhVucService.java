package api_CTDT.services;


import java.sql.Timestamp;
import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import api_CTDT.Models.LinhVuc;
import api_CTDT.Models.Log;
import api_CTDT.Models.SinhVien;
import api_CTDT.Models.SinhVienLopHocPhan;
import api_CTDT.Models.LinhVuc;
import api_CTDT.Models.LinhVuc;
import api_CTDT.repositories.LinhVucRepository;
import api_CTDT.repositories.LogRepository;



@Service
public class LinhVucService {
	@Autowired
	private LinhVucRepository fieldRepository;
	@Autowired
	LogRepository logRepository;
	//getall
	public List<LinhVuc> findAll(){
		return fieldRepository.findAll();
	}
	//search by name
	 public List<LinhVuc> searchByKeyword(String Keyword) {
		 return fieldRepository.searchByKeyword(Keyword);
	 }
	 
	
	 
//		public LinhVuc Update(Integer id, LinhVuc linhvuc) {
//			LinhVuc bean = requireOne(id);
//			BeanUtils.copyProperties(linhvuc, bean);
//			return fieldRepository.save(bean);
//		}
		
	 public Boolean save(LinhVuc linhvuc) {
		 LinhVuc linhvucs = fieldRepository.save(linhvuc);
		 if (linhvucs != null) {
				Log log = new Log();
				log.setLogString("Thêm mới lĩnh vực id = " + linhvucs.getId());
				log.setCreateTime(new Timestamp(System.currentTimeMillis()));
				logRepository.save(log);
				return true;
			}	
		 	return false;
		}
	 
		public Boolean update(LinhVuc linhVuc) {
			LinhVuc LinhVuc = fieldRepository.save(linhVuc);
			if (LinhVuc != null) {
				Log log = new Log();
				log.setLogString("Cập nhật sinh viên id = " + LinhVuc.getId());
				log.setCreateTime(new Timestamp(System.currentTimeMillis()));
				logRepository.save(log);
				return true;
			}
			return false; 
		}
		
		public LinhVuc getById(Integer id) {
			LinhVuc original = requireOne(id);
			return original;
		}
		
		private LinhVuc requireOne(Integer id) {
			return fieldRepository.findById(id).orElseThrow(() -> new NoSuchElementException("Resource not found: " + id));
		}
}