package api_CTDT.services;

import java.sql.Timestamp;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import api_CTDT.Models.Log;
import api_CTDT.Models.MQHHocPhan;
import api_CTDT.repositories.LogRepository;
import api_CTDT.repositories.MoiQuanHeHPRepository;

@Service
public class MoiQuanHeHPServiceAPI {
	@Autowired
	MoiQuanHeHPRepository mqhRepository;
	@Autowired
	LogRepository logRepository;
	
	public List<MQHHocPhan> getAll() {
		List<MQHHocPhan> listLHP = mqhRepository.findAll();
		return listLHP;
	}
	
	public Boolean themMQHHP(MQHHocPhan mqhHocPhan) {
		MQHHocPhan mqhHocPhanThem = mqhRepository.save(mqhHocPhan);
		if (mqhHocPhanThem != null) {
			Log log = new Log();
			log.setLogString("Thêm mới mối quan hệ học phần mã học phần = " + mqhHocPhanThem.getMa_hoc_phan());
			log.setCreateTime(new Timestamp(System.currentTimeMillis()));
			logRepository.save(log);
			return true;
		}
		return false;
	}
	
	public MQHHocPhan layMQHHP(Integer id) {
		MQHHocPhan mqhHocPhan = mqhRepository.findById(id).orElseThrow();
		return mqhHocPhan;
	}
	
	public List<MQHHocPhan>  getMqhHocPhan(String mhp) {
		List<MQHHocPhan>  mqhHocPhan = mqhRepository.findByMaHP(mhp);
		if(mqhHocPhan!=null) {
			return mqhHocPhan;
		}else {
			return null;
		}
		
	}
	
	public Boolean capNhatMQHHP(MQHHocPhan mqhHocPhan) {
		MQHHocPhan mqhHocPhanCapNhat = mqhRepository.save(mqhHocPhan);
		if (mqhHocPhanCapNhat != null) {
			Log log = new Log();
			log.setLogString("Cập nhật mối quan hệ học phần mã học phần = " + mqhHocPhanCapNhat.getMa_hoc_phan());
			log.setCreateTime(new Timestamp(System.currentTimeMillis()));
			logRepository.save(log);
			return true;
		}
		return false;
	}
}
