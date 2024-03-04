package api_CTDT.services;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;

import org.hibernate.boot.model.naming.ImplicitNameSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.cache.caffeine.CaffeineCache;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.github.benmanes.caffeine.cache.Caffeine;

import org.springframework.cache.Cache;
import api_CTDT.Models.DanhMucHocPhan;
import api_CTDT.repositories.DMHPRepository;

@Service
public class DMHPService {
	@Autowired
	private DMHPRepository dmhpRepository;

//	private final CacheManager cacheManager;
//
//	public DMHPService(CacheManager cacheManager) {
//		this.cacheManager = cacheManager;
//	}

	/*
	 * Use @Cacheable to cache the results of a method, Spring will automatically
	 * manage checking whether the result has been cached or not. If the result
	 * already exists in the cache, Spring will return the result without executing
	 * the method.
	 */
//	@Cacheable(cacheNames = "ListDMHP", key = "#result != null ? #result?.![id] : 'empty'")
	public List<DanhMucHocPhan> getAll() {
		List<DanhMucHocPhan> data = dmhpRepository.findAll();
	    
	    if (data != null) {
	        return data;
	    } else {
	        // Xử lý trường hợp dữ liệu là null, có thể trả về một danh sách rỗng hoặc xử lý khác tùy thuộc vào yêu cầu của bạn.
	        return Collections.emptyList();
	    }
	}

//	@CachePut(cacheNames = "ListDMHP", key = "#result != null ? #result?.![id] : 'empty'")
////	@Scheduled(fixedDelay = 300000) // Thực hiện mỗi 300000 milliseconds (5 phút)
//	public List<DanhMucHocPhan> reloadAll() {
//		  List<DanhMucHocPhan> newData = dmhpRepository.findAll();
//		    
//		    if (newData != null) {
//		        System.out.println(newData.toString());
//		        return newData;
//		    } else {
//		        // Xử lý trường hợp dữ liệu mới là null, có thể trả về một danh sách rỗng hoặc xử lý khác tùy thuộc vào yêu cầu của bạn.
//		        return Collections.emptyList();
//		    }
//	}


//	public void printCacheDataByName(String cacheName) {
//		Cache cache = cacheManager.getCache(cacheName);
//		if (cache != null) {
//			System.out.println("Cache Name: " + cacheName);
//
//			// Access cache-specific features to get data by name
//			// For example, if it's a Caffeine Cache:
//			CaffeineCache caffeineCache = (CaffeineCache) cache;
//			com.github.benmanes.caffeine.cache.Cache<Object, Object> nativeCache = caffeineCache.getNativeCache();
//			for (Object key : nativeCache.asMap().keySet()) {
//				System.out.println("Key: " + key + ", Value: " + cache.get(key).get());
//			}
//
//			System.out.println("-------------");
//		} else {
//			System.out.println("Cache with name " + cacheName + " not found.");
//		}
//	}

	public DanhMucHocPhan getByID(int id) {
//		printCacheDataByName("ListDMHP");
		List<DanhMucHocPhan> allDMHP = getAll();
		for (DanhMucHocPhan dmhp : allDMHP) {
			if (dmhp.getId() == id) {
				return dmhp;
			}
		}

		return null;
	}
	
//	@CachePut(cacheNames = "ListDMHP", key = "#result.id")
	public DanhMucHocPhan create(DanhMucHocPhan dmhp) {
		int i = 0;
		for (DanhMucHocPhan dm : getAll()) {
			if (dmhp.getMa_hoc_phan().equals(dm.getMa_hoc_phan())) {
				i = 1;
				break;
			}
		}
		if (i == 0) {
			int sotinchi = (int)(dmhp.getTc_thuc_hanh()+dmhp.getTc_do_an()+dmhp.getTc_ly_thuyet());
			dmhp.setSo_tin_chi(sotinchi);
			dmhp = dmhpRepository.save(dmhp);
//			reloadAll();
		} else {
			return null;
		}

		return dmhp;
	}

//	@Caching(evict = {@CacheEvict(cacheNames = "ListDMHP", key = "#id")},
//	         put = {@CachePut(cacheNames = "ListDMHP", key = "#result.id")})
	public DanhMucHocPhan edit(int id, DanhMucHocPhan dmhp) {
		DanhMucHocPhan danhmuc = getByID(id);
		if (danhmuc == null) {
			return null;
		}
		int i = 0;
		if (dmhp.getMa_hoc_phan().equals(danhmuc.getMa_hoc_phan())) {

		}else {
			for (DanhMucHocPhan dm : getAll()) {
				 if (dmhp.getMa_hoc_phan().equals(dm.getMa_hoc_phan())) {
					i = 1;
					break;
				}

			}
		}
		
	
		if (i == 0) {
			danhmuc.setGhi_chu(dmhp.getGhi_chu());
			danhmuc.setId_loai_hoc_phan(dmhp.getId_loai_hoc_phan());
			danhmuc.setId_dml_hp(dmhp.getId_dml_hp());
			danhmuc.setMa_hoc_phan(dmhp.getMa_hoc_phan());
			int sotinchi = (int)(dmhp.getTc_thuc_hanh()+dmhp.getTc_do_an()+dmhp.getTc_ly_thuyet());
			danhmuc.setSo_tin_chi(sotinchi);
			danhmuc.setTc_do_an(dmhp.getTc_do_an());
			danhmuc.setTc_ly_thuyet(dmhp.getTc_ly_thuyet());
			danhmuc.setTc_thuc_hanh(dmhp.getTc_thuc_hanh());
			danhmuc.setHp_cot_loi(dmhp.isHp_cot_loi());
			danhmuc.setTen_hoc_phan(dmhp.getTen_hoc_phan());
			danhmuc.setStt(dmhp.isStt());
			danhmuc.setTg_batdau(dmhp.getTg_batdau());
			danhmuc.setTg_ketthuc(dmhp.getTg_ketthuc());
			danhmuc = dmhpRepository.save(danhmuc);
//			reloadAll();
		} else {
			return null;
		}

		return danhmuc;
	}

	public List<DanhMucHocPhan> getByidLHP(int id) {
//		printCacheDataByName("ListDMHP");
		List<DanhMucHocPhan> list = new ArrayList<>();
		for (DanhMucHocPhan dmhp : getAll()) {
			if (dmhp.getId_loai_hoc_phan() == id) {
				list.add(dmhp);
			}
		}
		return list;
	}

	public List<DanhMucHocPhan> getByMaHocPhan(String maHocPhan) {
//		printCacheDataByName("ListDMHP");
		List<DanhMucHocPhan> list = new ArrayList();
//		System.out.println(maHocPhan);
		for (DanhMucHocPhan dmhp : getAll()) {
			if ((dmhp.getMa_hoc_phan()).contains(maHocPhan)) {
				list.add(dmhp);
			}
		}
		return list;
	}

	public List<DanhMucHocPhan> getByTenHocPhan(String tenHocPhan) {
//		printCacheDataByName("ListDMHP");
		List<DanhMucHocPhan> list = new ArrayList();
//		System.out.println(tenHocPhan);
		for (DanhMucHocPhan dmhp : getAll()) {
//			System.out.println(dmhp.getTen_hoc_phan());
			if (dmhp.getTen_hoc_phan().toLowerCase().contains(tenHocPhan.toLowerCase())) {
				list.add(dmhp);
			}
		}
		return list;
	}

	public List<DanhMucHocPhan> getByKey(String key, Integer id_lhp, Integer id_dmlhp) {
		List<DanhMucHocPhan> list = new ArrayList<>();
		for (DanhMucHocPhan dmhp : getAll()) {
			if (key != null  && id_lhp != null && id_dmlhp!=null) {
				if ((key.equals(dmhp.getMa_hoc_phan()) || key.equals(dmhp.getTen_hoc_phan()))
						&& id_lhp == dmhp.getId_loai_hoc_phan() && id_dmlhp == dmhp.getId_dml_hp()) {
					list.add(dmhp);
				}
			} else if (key != null&& id_dmlhp!=null) {
				if ((key.equals(dmhp.getMa_hoc_phan()) || key.equals(dmhp.getTen_hoc_phan()))&& id_dmlhp == dmhp.getId_dml_hp())
					list.add(dmhp);
			}else if (key!= null && id_lhp != null) {
				if ((key.equals(dmhp.getMa_hoc_phan()) || key.equals(dmhp.getTen_hoc_phan()))
						&& id_lhp == dmhp.getId_loai_hoc_phan())
					list.add(dmhp);
			}else if (id_lhp != null&& id_dmlhp!=null) {
				if (id_lhp == dmhp.getId_loai_hoc_phan()&& id_dmlhp == dmhp.getId_dml_hp())
					list.add(dmhp);
			}else if (key!=null) {
				if (key.equals(dmhp.getMa_hoc_phan())|| key.equals(dmhp.getTen_hoc_phan()))
					list.add(dmhp);
			}  else if (id_lhp != null) {
				if (id_lhp == dmhp.getId_loai_hoc_phan())
					list.add(dmhp);
			} else if (id_dmlhp!=null) {
				if (id_dmlhp == dmhp.getId_dml_hp())
					list.add(dmhp);
			} else {
				return getAll();
			}
		}
		return list;
	}
	
	public DanhMucHocPhan getByMaHP(String maHP) {
		DanhMucHocPhan dmhp = dmhpRepository.getByMaHP(maHP);
		return dmhp;
	}
}
