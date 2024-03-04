package api_CTDT.services;

import java.sql.Timestamp;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import api_CTDT.Models.Log;
import api_CTDT.Models.SinhVien;
import api_CTDT.repositories.LogRepository;
import api_CTDT.repositories.SinhVienRepository;

@Service
public class SinhVienService {
	@Autowired
	SinhVienRepository sinhVienRepository;

	@Autowired
	LogRepository logRepository;
	
	public List<SinhVien> getAll() {
		List<SinhVien> listSinhVien = sinhVienRepository.findAll();
		return listSinhVien;
	}
	
	public Boolean themSinhVien(SinhVien sv) {
		SinhVien sinhVien = sinhVienRepository.save(sv);
		if (sinhVien != null) {
			Log log = new Log();
			log.setLogString("Thêm mới sinh viên id = " + sinhVien.getId());
			log.setCreateTime(new Timestamp(System.currentTimeMillis()));
			logRepository.save(log);
			return true;
		}
		return false;
	}
	
	public SinhVien laySinhVien(Integer id) {
		SinhVien sinhVien = sinhVienRepository.findById(id).orElseThrow();
		return sinhVien;
	}
	
	public Boolean capNhatSinhVien(SinhVien sinhVien) {
		SinhVien sinhVienCapNhat = sinhVienRepository.save(sinhVien);
		if (sinhVienCapNhat != null) {
			Log log = new Log();
			log.setLogString("Cập nhật sinh viên id = " + sinhVien.getId());
			log.setCreateTime(new Timestamp(System.currentTimeMillis()));
			logRepository.save(log);
			return true;
		}
		return false;
	}
	
	public SinhVien laySinhVienTheoMaSV(String maSV) {
		SinhVien sinhVien = sinhVienRepository.getByMaSV(maSV);
		return sinhVien;
	}
	
}
