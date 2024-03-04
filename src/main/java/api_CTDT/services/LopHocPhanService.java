package api_CTDT.services;

import java.sql.Timestamp;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.annotation.JsonTypeInfo.Id;

import api_CTDT.Models.DanhMucHocPhan;
import api_CTDT.Models.Log;
import api_CTDT.Models.LopHocPhan;
import api_CTDT.repositories.LogRepository;
import api_CTDT.repositories.LopHocPhanRepository;

@Service
public class LopHocPhanService {
	@Autowired
	LopHocPhanRepository lopHocPhanRepository;
	@Autowired
	DMHPService dmhpService;
	@Autowired
	LogRepository logRepository;
	
	public List<LopHocPhan> getAll() {
		List<LopHocPhan> listLHP = lopHocPhanRepository.findAll();
		return listLHP;
	}
	
	public Boolean themLopHocPhan(LopHocPhan lhp) {
		Integer dem = lopHocPhanRepository.countLHP(lhp.getHocPhan(), lhp.getHocKi(), lhp.getNamHoc());
		lhp.setMaLop(lhp.getHocPhan() + " (" + (dem + 1) + ")");
		LopHocPhan lopHocPhan = lopHocPhanRepository.save(lhp);
		if (lopHocPhan != null) {
			Log log = new Log();
			log.setLogString("Thêm mới lớp học phần id = " + lopHocPhan.getId());
			log.setCreateTime(new Timestamp(System.currentTimeMillis()));
			logRepository.save(log);
			return true;
		}
		return false;
	}
	
	public LopHocPhan layLopHocPhan(Integer id) {
		LopHocPhan lopHocPhan = lopHocPhanRepository.findById(id).orElseThrow();
		return lopHocPhan;
	}
	
	public Boolean capNhatLopHocPhan(LopHocPhan lopHocPhan) {
		LopHocPhan lhpHienTai = layLopHocPhan(lopHocPhan.getId());
		if (lhpHienTai.getHocPhan() != lopHocPhan.getHocPhan()) {
			Integer dem = lopHocPhanRepository.countLHP(lopHocPhan.getHocPhan(), lopHocPhan.getHocKi(), lopHocPhan.getNamHoc());
			lopHocPhan.setMaLop(lopHocPhan.getHocPhan() + " (" + (dem + 1) + ")");
		}
		LopHocPhan lhpCapNhat = lopHocPhanRepository.save(lopHocPhan);
		if (lhpCapNhat != null) {
			Log log = new Log();
			log.setLogString("Cập nhật lớp học phần id = " + lopHocPhan.getId());
			log.setCreateTime(new Timestamp(System.currentTimeMillis()));
			logRepository.save(log);
			return true;
		}
		return false;
	}
	
	public List<LopHocPhan> layListLopHocPhan(String maHP) {
		return lopHocPhanRepository.getLopHocPhan(maHP);
	}

}
