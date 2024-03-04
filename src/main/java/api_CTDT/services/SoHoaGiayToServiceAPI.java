package api_CTDT.services;

import java.sql.Timestamp;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import api_CTDT.Models.Log;
import api_CTDT.Models.SoHoaGiayTo;
import api_CTDT.repositories.LogRepository;
import api_CTDT.repositories.SoHoaGiayToRepository;

@Service
public class SoHoaGiayToServiceAPI {
	@Autowired
	SoHoaGiayToRepository shgtRepository;
	@Autowired
	LogRepository logRepository;
	
	public List<SoHoaGiayTo> getAll() {
		List<SoHoaGiayTo> listSHGT = shgtRepository.findAll();
		return listSHGT;
	}
	
	public Boolean themSoHoaGiayTo(SoHoaGiayTo soHoaGiayTo) {
		SoHoaGiayTo shgtThem = shgtRepository.save(soHoaGiayTo);
		if (shgtThem != null) {
			Log log = new Log();
			log.setLogString("Thêm mới giấy tờ số hóa id = " + shgtThem.getId());
			log.setCreateTime(new Timestamp(System.currentTimeMillis()));
			logRepository.save(log);
			return true;
		}
		return false;
	}
	
	public SoHoaGiayTo layGiayTo(Integer id) {
		SoHoaGiayTo soHoaGiayTo = shgtRepository.findById(id).orElseThrow();
		return soHoaGiayTo;
	}
	
	public Boolean capNhatSoHoaGiayTo(SoHoaGiayTo soHoaGiayTo) {
		SoHoaGiayTo soHoaGiayToCapNhat = shgtRepository.save(soHoaGiayTo);
		if (soHoaGiayToCapNhat != null) {
			Log log = new Log();
			log.setLogString("Cập nhật giấy tờ số hóa id = " + soHoaGiayToCapNhat.getId());
			log.setCreateTime(new Timestamp(System.currentTimeMillis()));
			logRepository.save(log);
			return true;
		}
		return false;
	}
}
