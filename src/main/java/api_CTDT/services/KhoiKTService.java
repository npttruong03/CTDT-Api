package api_CTDT.services;

import java.util.ArrayList;
import java.util.List;
import com.github.benmanes.caffeine.cache.Caffeine;

import org.springframework.cache.Cache;
import org.aspectj.weaver.patterns.IfPointcut.IfFalsePointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.caffeine.CaffeineCache;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import api_CTDT.Models.DanhMucHocPhan;
import api_CTDT.Models.KhoiKienThuc;
import api_CTDT.repositories.KhoiktRepository;

@Service
public class KhoiKTService {
	
	@Autowired
	private KhoiktRepository kktRepository;
	
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
	
	
//	@Cacheable(cacheNames = "ListKKT")
	public List<KhoiKienThuc> getAll(){
//		printCacheDataByName("ListKKT");
		return kktRepository.findAll();
		
	}
	
//	@CachePut(cacheNames = "ListKKT")
//	@Scheduled(fixedDelay = 12000000) // Thực hiện mỗi 300000 milliseconds (5 phút)
//	public List<KhoiKienThuc> reloadAll() {
//		List<KhoiKienThuc> newData = kktRepository.findAll();
////		System.out.println(newData.toString());
//		return newData;
//	}
//	@CacheEvict(cacheNames = "ListKKT", allEntries = true)
//    public void clearCache() {
//		reloadAll();
//        // Không cần thêm logic nếu bạn chỉ muốn xóa cache
//    }
	
	public KhoiKienThuc getByID(int id) {
		for (KhoiKienThuc kkThuc : getAll()) {
			if(kkThuc.getId() == id) {
				return kkThuc;
			}
		}
		return null;
		
	}
	
	public KhoiKienThuc create(KhoiKienThuc kkt) {
		kkt = kktRepository.save(kkt);
		return kkt;
	}
	
	public KhoiKienThuc edit(KhoiKienThuc kkt, int id) {
		KhoiKienThuc khoiKienThuc = getByID(id);
		if(khoiKienThuc==null) {
			return null;
		}
		
		khoiKienThuc.setTen_khoi_kien_thuc(kkt.getTen_khoi_kien_thuc());
		kktRepository.save(khoiKienThuc);
		
		return khoiKienThuc;
	}
	public List<KhoiKienThuc> getByName(String name){
		List<KhoiKienThuc> list = new ArrayList<>();
		for (KhoiKienThuc khoiKienThuc : getAll()) {
			if(khoiKienThuc.getTen_khoi_kien_thuc().toLowerCase().contains(name.toLowerCase())) {
				list.add(khoiKienThuc);
			}
		}
		return list;
	}

}
