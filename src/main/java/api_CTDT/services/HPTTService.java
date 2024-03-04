package api_CTDT.services;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Vector;
import java.util.stream.Collectors;

import org.apache.commons.math3.linear.ArrayRealVector;
import org.apache.commons.math3.linear.RealVector;
import org.apache.commons.math3.util.MathUtils;
import org.apache.mahout.math.NamedVector;
import org.apache.mahout.vectorizer.tfidf.TFIDFPartialVectorReducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import api_CTDT.Models.DanhMucHocPhan;
import api_CTDT.Models.HPThayThe;
import api_CTDT.Models.KhoiKienThuc;
import api_CTDT.Models.NoiDungChuongTrinh;
import api_CTDT.repositories.DMHPRepository;
import api_CTDT.repositories.HPTTRepository;
import api_CTDT.repositories.NDCTRepository;

@Service
public class HPTTService {
	@Autowired
	private HPTTRepository hpttRepository;

	@Autowired
	private DMHPRepository dmhpRepository;

	@Autowired
	private NDCTRepository ndctRepository;

//	private final CacheManager cacheManager;
//
//	public HPTTService(CacheManager cacheManager) {
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

//	@Cacheable(cacheNames = "ListHPTT")
	public List<HPThayThe> getAll() {
		return hpttRepository.findAll();
	}

//	@CachePut(cacheNames = "ListHPTT")
//	@Scheduled(fixedDelay = 300000) // Thực hiện mỗi 300000 milliseconds (5 phút)
//	public List<HPThayThe> reloadAll() {
//		List<HPThayThe> newData = hpttRepository.findAll();
////		System.out.println(newData.toString());
//		return newData;
//	}
//	@CacheEvict(cacheNames = "ListHPTT", allEntries = true)
//    public void clearCache() {
//		reloadAll();
//        // Không cần thêm logic nếu bạn chỉ muốn xóa cache
//    }

	public HPThayThe getByID(int id) {
		for (HPThayThe hptt : getAll()) {
			if (hptt.getId() == id) {
				return hptt;
			}
		}
		return null;

	}

	public HPThayThe create(HPThayThe hptt) {
		int i = 0;
		for (HPThayThe hptthe : getAll()) {
			if (hptt.getMa_hoc_phan().equals(hptthe.getMa_hoc_phan())) {
				i=1;
				System.out.println(i);
				break;
			}
					
		}
		
		if(i==0) {
			hptt = hpttRepository.save(hptt);
			
			}
		else {
			return null;
		}
		return hptt;
	}

	public HPThayThe edit(HPThayThe hptt, int id) {
		HPThayThe hpthaythe = getByID(id);
		if (hpthaythe == null) {
			return null;
		}

		hpthaythe.setMa_hoc_phan(hptt.getMa_hoc_phan());
		hpthaythe.setHp_thaythe(hptt.getHp_thaythe());
		hpthaythe.setStt(hptt.isStt());
		hpttRepository.save(hpthaythe);

		return hpthaythe;
	}

	public List<HPThayThe> getByName(String name) {
		List<HPThayThe> list = new ArrayList<>();
		for (HPThayThe hptt : getAll()) {
			if (hptt.getHp_thaythe().toLowerCase().contains(name.toLowerCase())) {
				list.add(hptt);
			}
		}
		return list;
	}

	public List<HPThayThe> getByMaHocPhan(String maHocPhan) {
//		printCacheDataByName("ListDMHP");
		List<HPThayThe> list = new ArrayList();
//		System.out.println(maHocPhan);
		for (HPThayThe hpThayThe : getAll()) {
			if ((hpThayThe.getMa_hoc_phan()).contains(maHocPhan)) {
				list.add(hpThayThe);
			}
		}
		return list;
	}
	public List<HPThayThe> getmhp(String maHocPhan) {
//		printCacheDataByName("ListDMHP");
		List<HPThayThe> list = new ArrayList();
//		System.out.println(maHocPhan);
		for (HPThayThe hpThayThe : getAll()) {
			if ((hpThayThe.getMa_hoc_phan()).equals(maHocPhan)) {
				list.add(hpThayThe);
			}
		}
		return list;
	}

	public static RealVector vectorCourse(DanhMucHocPhan dmhp, NoiDungChuongTrinh ndct) {
		double[] features = new double[] { dmhp.getId_dml_hp(), dmhp.getId_loai_hoc_phan(), dmhp.getSo_tin_chi(),
				dmhp.getTc_do_an(), dmhp.getTc_ly_thuyet(), dmhp.getTc_thuc_hanh(), ndct.getId_chuongtrinh(),
				ndct.getId_dinh_huong(), ndct.getId_ktt() };
		return new ArrayRealVector(features);
	}

	public static double calculateCosineSimilarity(RealVector vector1, RealVector vector2) {
		double dotProduct = vector1.dotProduct(vector2);
		double norm1 = vector1.getNorm();
		double norm2 = vector2.getNorm();
		// Use a small tolerance value for equality check
		double tolerance = 1e-6;
		if (MathUtils.equals(norm1, 0.0) || MathUtils.equals(norm2, 0.0)) {
			return 0; // Handle division by zero
		}

		return dotProduct / (norm1 * norm2);
	}

	public List<DanhMucHocPhan> recommendCourse(String mhp) {
		DanhMucHocPhan dmhp = dmhpRepository.getByMaHP(mhp);
		for (NoiDungChuongTrinh ndct : ndctRepository.getByMa_hoc_phan(mhp)) {
			if (dmhp == null) {
				return null;
			}

			List<DanhMucHocPhan> dmhps = dmhpRepository.findAll();
			RealVector vRealVector = vectorCourse(dmhp, ndct);

			List<DanhMucHocPhan> recommended = calculateCosineSimilarity(vRealVector, dmhps, dmhp);
			return recommended;
		}
		// Xử lý nếu không có kết quả
		return Collections.emptyList();

	}

	private List<DanhMucHocPhan> calculateCosineSimilarity(RealVector realVector, List<DanhMucHocPhan> dmhps,
			DanhMucHocPhan targetCourse) {
		List<DanhMucHocPhan> recommendedCourses = new ArrayList<>();

		for (DanhMucHocPhan course : dmhps) {
			for (NoiDungChuongTrinh ndct : ndctRepository.getByMa_hoc_phan(course.getMa_hoc_phan())) {
				RealVector courseVector = vectorCourse(course, ndct);
				double similarity = calculateCosineSimilarity(realVector, courseVector);

				// Thay đổi ngưỡng tương đồng để kiểm tra
				if (similarity > SIMILARITY_THRESHOLD && satisfyOtherCriteria(course, targetCourse)) {
					recommendedCourses.add(course);
				}
			}
		}

		return recommendedCourses;
	}

	private boolean satisfyOtherCriteria(DanhMucHocPhan course, DanhMucHocPhan targetCourse) {
		// Kiểm tra cùng loại học phần và cùng dm_lhp
		if (course.getId_loai_hoc_phan() != targetCourse.getId_loai_hoc_phan()) {
			return false;
		} else {
			List<NoiDungChuongTrinh> ndcts = ndctRepository.getByMa_hoc_phan(course.getMa_hoc_phan());
			List<NoiDungChuongTrinh> ndct2s = ndctRepository.getByMa_hoc_phan(targetCourse.getMa_hoc_phan());

			for (NoiDungChuongTrinh ndct : ndcts) {
			    for (NoiDungChuongTrinh ndct2 : ndct2s) {
			        // Kiểm tra điều kiện
			        if ((course.getSo_tin_chi() >= targetCourse.getSo_tin_chi()
			                && course.getTc_ly_thuyet() >= targetCourse.getTc_ly_thuyet()
			                && course.getTc_thuc_hanh() >= targetCourse.getTc_thuc_hanh())
			                || ndct.getId_chuongtrinh() != ndct2.getId_chuongtrinh()
			                || ndct.getId_dinh_huong() == ndct2.getId_dinh_huong()
			                || ndct.getId_ktt() == ndct2.getId_ktt()||(course.getSo_tin_chi() >= targetCourse.getSo_tin_chi()
			                && course.getTc_ly_thuyet() >= targetCourse.getTc_ly_thuyet()
			                && course.getTc_thuc_hanh() >= targetCourse.getTc_thuc_hanh()&&ndct.getId_chuongtrinh() == ndct2.getId_chuongtrinh())||
			                (course.getSo_tin_chi() >= targetCourse.getSo_tin_chi()
			                && course.getTc_ly_thuyet() >= targetCourse.getTc_ly_thuyet()
			                && course.getTc_thuc_hanh() >= targetCourse.getTc_thuc_hanh()&&ndct.getId_dinh_huong() == ndct2.getId_dinh_huong())
			                ||
			                (course.getSo_tin_chi() >= targetCourse.getSo_tin_chi()
			                && course.getTc_ly_thuyet() >= targetCourse.getTc_ly_thuyet()
			                && course.getTc_thuc_hanh() >= targetCourse.getTc_thuc_hanh()&&ndct.getId_ktt() == ndct2.getId_ktt())) {
			            return true;
			        }
			    }
			}
		}
		return false;

	}

	private static final double SIMILARITY_THRESHOLD = 0.7;
	private static final int MINIMUM_CREDIT = 3;
	private static final int DESIRED_TYPE = 1;
}
