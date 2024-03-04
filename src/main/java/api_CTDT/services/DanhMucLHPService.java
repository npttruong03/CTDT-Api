package api_CTDT.services;

import java.sql.Timestamp;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import api_CTDT.Models.DanhMucLHP;
import api_CTDT.Models.Log;
import api_CTDT.repositories.DanhMucLHPRepository;
import api_CTDT.repositories.LogRepository;

@Service
public class DanhMucLHPService {
	@Autowired
	DanhMucLHPRepository danhMucLHPRepository;
	@Autowired
	LogRepository logRepository;
	
	public List<DanhMucLHP> getAll() {
		List<DanhMucLHP> listDMLHP = danhMucLHPRepository.findAll();
		return listDMLHP;
	}
	
	public Boolean themDMLHP(DanhMucLHP dmlhp) {
		DanhMucLHP DMLHP = danhMucLHPRepository.save(dmlhp);
		if (DMLHP != null) {
			Log log = new Log();
			log.setLogString("Thêm mới danh mục loại học phần id = " + DMLHP.getId());
			log.setCreateTime(new Timestamp(System.currentTimeMillis()));
			logRepository.save(log);
			return true;
		}
		return false;
	}
	
	public DanhMucLHP layDMLHP(Integer id) {
		DanhMucLHP DMLHP = danhMucLHPRepository.findById(id).orElseThrow();
		return DMLHP;
	}
	
	public Boolean capNhatDMLHP(DanhMucLHP dmlhp) {
		DanhMucLHP dmlhpCapNhat = danhMucLHPRepository.save(dmlhp);
		
		if (dmlhpCapNhat != null) {
			Log log = new Log();
			log.setLogString("Cập nhật danh mục loại học phần id = " + dmlhpCapNhat.getId());
			log.setCreateTime(new Timestamp(System.currentTimeMillis()));
			logRepository.save(log);
			return true;
		}
		return false;
	}
}
