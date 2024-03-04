package api_CTDT.services;

import java.sql.Timestamp;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import api_CTDT.Models.Log;
import api_CTDT.Models.SinhVienLopHocPhan;
import api_CTDT.repositories.LogRepository;
import api_CTDT.repositories.SinhVienLopHocPhanRepository;

@Service
public class SinhVienLopHocPhanService {
	@Autowired
	SinhVienLopHocPhanRepository svlhpRepo;

	@Autowired
	LogRepository logRepository;
	
	public List<SinhVienLopHocPhan> getAll() {
		List<SinhVienLopHocPhan> listSVLHP = svlhpRepo.findAll();
		return listSVLHP;
	}
	
	public Boolean themSinhVienLHP(List<SinhVienLopHocPhan> svlhp) {
		svlhp.forEach(e -> {
			SinhVienLopHocPhan SVLHP = svlhpRepo.save(e);
			if (SVLHP != null) {
				Log log = new Log();
				log.setLogString("Thêm mới sinh viên lớp học phần id = " + SVLHP.getId());
				log.setCreateTime(new Timestamp(System.currentTimeMillis()));
				logRepository.save(log);
			}
		});

		return true;
	}
	
	public SinhVienLopHocPhan laySinhVienLHP(Integer id) {
		SinhVienLopHocPhan SVLHP = svlhpRepo.findById(id).orElseThrow();
		return SVLHP;
	}
	
	public Boolean capNhatSinhVienLHP(SinhVienLopHocPhan svlhp) {
		SinhVienLopHocPhan sinhVienLHPCapNhat = svlhpRepo.save(svlhp);
		if (sinhVienLHPCapNhat != null) {
			Log log = new Log();
			log.setLogString("Cập nhật sinh viên lớp học phần id = " + svlhp.getId());
			log.setCreateTime(new Timestamp(System.currentTimeMillis()));
			logRepository.save(log);
			return true;
		}
		return false;
	}
	
	public List<SinhVienLopHocPhan> layBangMaSV(String maSV) {
		List<SinhVienLopHocPhan> list = svlhpRepo.findByMaSV(maSV);
		return list;
	}
	
	public List<SinhVienLopHocPhan> bangdiembylhp(Integer idlhp) {
		List<SinhVienLopHocPhan> list = svlhpRepo.findByIdLHP(idlhp);
		return list;
	}

	public List<SinhVienLopHocPhan> checkSVLHP(String maSV) {
		List<SinhVienLopHocPhan> list = svlhpRepo.checkSVLHP(maSV);

		return list;
	}
}
