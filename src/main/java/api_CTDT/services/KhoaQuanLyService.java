package api_CTDT.services;

import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import api_CTDT.Models.ChuyenNganh;
import api_CTDT.Models.KhoaQuanLy;
import api_CTDT.repositories.KhoaQuanLyRepository;

@Service
public class KhoaQuanLyService {
	@Autowired
	private KhoaQuanLyRepository khoaQuanLyRepository;
	
	private KhoaQuanLy requireOne(Integer id) {
		return khoaQuanLyRepository.findById(id).orElseThrow(() -> new NoSuchElementException("Resource not found: " + id));
	}
	
	public List<KhoaQuanLy> findAll() {
		
		return khoaQuanLyRepository.findAll();
	}
	
	public KhoaQuanLy save(KhoaQuanLy khoaQuanLy) {
		khoaQuanLy.setId(0);
		return khoaQuanLyRepository.save(khoaQuanLy);
	}
	
	public KhoaQuanLy Update(Integer id, KhoaQuanLy khoaQuanLyDto) {
		KhoaQuanLy bean = requireOne(id);
		BeanUtils.copyProperties(khoaQuanLyDto, bean);
		return khoaQuanLyRepository.save(bean);
	}
	
	public KhoaQuanLy getById(Integer id) {
		KhoaQuanLy original = requireOne(id);
		return original;
	}
	public List<KhoaQuanLy> searchByKeyword(String Keyword) {
		 return khoaQuanLyRepository.searchByKeyword(Keyword);
	 }
}
