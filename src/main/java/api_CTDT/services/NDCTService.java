package api_CTDT.services;

import java.util.ArrayList;
import java.util.List;

import org.aspectj.weaver.patterns.IfPointcut.IfFalsePointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import api_CTDT.Models.DanhMucHocPhan;
import api_CTDT.Models.KhoiKienThuc;
import api_CTDT.Models.NoiDungChuongTrinh;
import api_CTDT.repositories.KhoiktRepository;
import api_CTDT.repositories.NDCTRepository;

@Service
public class NDCTService {

	@Autowired
	private NDCTRepository ndctRepository;

//	private final CacheManager cacheManager;
//
//	public KhoiKTService(CacheManager cacheManager) {
//		this.cacheManager = cacheManager;
//	}
//	public void printCacheDataByName(String cacheName) {
//	Cache cache = cacheManager.getCache(cacheName);
//	if (cache != null) {
//		System.out.println("Cache Name: " + cacheName);
//
//		// Access cache-specific features to get data by name
//		// For example, if it's a Caffeine Cache:
//		CaffeineCache caffeineCache = (CaffeineCache) cache;
//		com.github.benmanes.caffeine.cache.Cache<Object, Object> nativeCache = caffeineCache.getNativeCache();
//		for (Object key : nativeCache.asMap().keySet()) {
//			System.out.println("Key: " + key + ", Value: " + cache.get(key).get());
//		}
//
//		System.out.println("-------------");
//	} else {
//		System.out.println("Cache with name " + cacheName + " not found.");
//	}
//}
//	printCacheDataByName("ListKKT");

//	@Cacheable(cacheNames = "ListNDCT", key = "#result != null ? #result?.![id] : 'empty'")
	public List<NoiDungChuongTrinh> getAll() {
		return ndctRepository.findAll();
	}

//	@CachePut(cacheNames = "ListNDCT", key = "#result != null ? #result?.![id] : 'empty'")
//	@Scheduled(fixedDelay = 12000000) // Thực hiện mỗi 300000 milliseconds (5 phút)
//	public List<NoiDungChuongTrinh> reloadAll() {
//		List<NoiDungChuongTrinh> newData = ndctRepository.findAll();
////		System.out.println(newData.toString());
//		return newData;
//	}


	public NoiDungChuongTrinh getByID(int id) {
		for (NoiDungChuongTrinh noiDungChuongTrinh : getAll()) {
			if (noiDungChuongTrinh.getId() == id) {
				return noiDungChuongTrinh;
			}
		}
		return null;

	}
//	@CachePut(cacheNames = "ListNDCT", key = "#result.id")
	public NoiDungChuongTrinh create(NoiDungChuongTrinh ndct) {
		ndct = ndctRepository.save(ndct);
		return ndct;
	}
//	
//	@Caching(evict = {@CacheEvict(cacheNames = "ListNDCT", key = "#id")},
//	         put = {@CachePut(cacheNames = "ListNDCT", key = "#result.id")})
	public NoiDungChuongTrinh edit(NoiDungChuongTrinh ndct, int id) {
		NoiDungChuongTrinh noiDungChuongTrinh = getByID(id);
		if (noiDungChuongTrinh == null) {
			return null;
		}
		noiDungChuongTrinh.setMa_hoc_phan(ndct.getMa_hoc_phan());
		noiDungChuongTrinh.setId_mqh(ndct.getId_mqh());
		noiDungChuongTrinh.setId_ktt(ndct.getId_ktt());
		noiDungChuongTrinh.setId_hptt(ndct.getId_hptt());
		noiDungChuongTrinh.setId_dinh_huong(ndct.getId_dinh_huong());
		noiDungChuongTrinh.setId_danh_muc_lhp(ndct.getId_danh_muc_lhp());
		noiDungChuongTrinh.setId_chuongtrinh(ndct.getId_chuongtrinh());
		noiDungChuongTrinh.setHoc_ky_du_kien(ndct.getHoc_ky_du_kien());
		noiDungChuongTrinh.setStt(ndct.isStt());
		ndctRepository.save(noiDungChuongTrinh);
		
		return noiDungChuongTrinh;
	}

//	public List<HP_KKT> findByNganhKKT(Integer id_nganh, Integer id_chuyenNganh, Integer id_kkt){
//		List<HP_KKT> list = new ArrayList<>();
//		for (HP_KKT hpKKT : getAll()) {
//			if(id_chuyenNganh!=null && id_kkt!=null && id_nganh!=null) {
//				if(id_chuyenNganh==hpKKT.getId_chuyen_nganh()&& id_kkt == hpKKT.getId_ktt() && id_nganh == hpKKT.getId_nganh()) {
//					list.add(hpKKT);
//				}
//			}else if(id_chuyenNganh!=null && id_kkt!=null) {
//				if(id_chuyenNganh==hpKKT.getId_chuyen_nganh()&& id_kkt == hpKKT.getId_ktt())
//					list.add(hpKKT);
//			}else if (id_chuyenNganh!=null&&id_nganh!=null) {
//				if (id_chuyenNganh==hpKKT.getId_chuyen_nganh()&&id_nganh == hpKKT.getId_nganh()) {
//					list.add(hpKKT);
//				}
//			}else if (id_kkt!=null&&id_nganh!=null) {
//				if(id_kkt == hpKKT.getId_ktt() && id_nganh == hpKKT.getId_nganh())
//					list.add(hpKKT);
//			}else if (id_chuyenNganh!=null) {
//				if(id_chuyenNganh==hpKKT.getId_chuyen_nganh())
//					list.add(hpKKT);
//			}
//			else if (id_nganh!=null) {
//				if(id_nganh==hpKKT.getId_nganh())
//					list.add(hpKKT);
//			}
//			else if (id_kkt!=null) {
//				if(id_kkt==hpKKT.getId_ktt())
//					list.add(hpKKT);
//			}else {
//				return getAll();
//			}
//		}
//		return list;
//	}
	public List<NoiDungChuongTrinh> getListNDCTSV(int idct, int hocky) {
		List<NoiDungChuongTrinh> listCT = ndctRepository.getListNDCT(idct, hocky);
		return listCT;
	}
	
	public List<NoiDungChuongTrinh> getListByIDCT(int idct) {
		List<NoiDungChuongTrinh> list = ndctRepository.getByIdct(idct);
		return list;
	}
}
