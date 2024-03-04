package api_CTDT.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import api_CTDT.Models.DinhHuong;
import api_CTDT.repositories.DinhHuongRepository;

@Service
public class DinhHuongService {
	@Autowired
	private DinhHuongRepository dinhHuongRepository;
	public List<DinhHuong> getAll(){
		return dinhHuongRepository.findAll();
	}
	
	public List<DinhHuong> reloadAll(){
		List<DinhHuong> newData = dinhHuongRepository.findAll();
		System.out.println(newData.toString());
		return newData;
	}
	
	public DinhHuong getById(int id) {
		for (DinhHuong dh : getAll()) {
			if(dh.getId()==id)
				return dh;
		}
		return null;
	}
	
	public DinhHuong create(DinhHuong dh) {
		if(dh!=null) {
			return dinhHuongRepository.save(dh);
		}else {
			return null;
		}
	}
	
	public DinhHuong edit(DinhHuong dh, int id) {
		DinhHuong dinhHuong = getById(id);
		if(dinhHuong == null) {
			return null;
		}
		dinhHuong.setTen_dinh_huong(dh.getTen_dinh_huong());
		dinhHuongRepository.save(dinhHuong);
		return dinhHuong;
	}
}
