package api_CTDT.services;

import java.sql.Timestamp;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import api_CTDT.Models.LoaiHocPhan;
import api_CTDT.Models.Log;
import api_CTDT.repositories.DMHPRepository;
import api_CTDT.repositories.LoaiHocPhanRepository;
import api_CTDT.repositories.LogRepository;

@Service
public class LoaiHocPhanServiceAPI {
	@Autowired
	LoaiHocPhanRepository loaiHocPhanRepository;
	@Autowired
	DMHPRepository dmhpRepository;
	@Autowired
	LogRepository logRepository;
	
	public List<LoaiHocPhan> getAll() {
		List<LoaiHocPhan> listLHP = loaiHocPhanRepository.findAll();
		return listLHP;
	}
	
	public Boolean themLHP(LoaiHocPhan lhp) {
		LoaiHocPhan loaiHocPhan = loaiHocPhanRepository.save(lhp);
		if (loaiHocPhan != null) {
			Log log = new Log();
			log.setLogString("Thêm mới loại học phần id = " + loaiHocPhan.getId());
			log.setCreateTime(new Timestamp(System.currentTimeMillis()));
			logRepository.save(log);
			return true;
		}
		return false;
	}
	
	public LoaiHocPhan layLHP(Integer id) {
		LoaiHocPhan loaiHocPhan = loaiHocPhanRepository.findById(id).orElseThrow();
		return loaiHocPhan;
	}
	
	public Boolean capNhatLHP(LoaiHocPhan loaiHocPhan) {
		Integer used = dmhpRepository.checkLHPused(loaiHocPhan.getId());
		if (used > 0) {
			return false;
		}
		LoaiHocPhan loaiHocPhanCapNhat = loaiHocPhanRepository.save(loaiHocPhan);
		if (loaiHocPhanCapNhat != null) {
			Log log = new Log();
			log.setLogString("Cập nhật loại học phần id = " + loaiHocPhan.getId());
			log.setCreateTime(new Timestamp(System.currentTimeMillis()));
			logRepository.save(log);
			return true;
		}
		return false;
	}
}
