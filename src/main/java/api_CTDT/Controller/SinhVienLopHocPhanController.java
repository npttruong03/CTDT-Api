package api_CTDT.Controller;

import java.util.ArrayList;
import java.util.List;

import org.apache.naming.java.javaURLContextFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import api_CTDT.Models.ChuongTrinh;
import api_CTDT.Models.DanhMucHocPhan;
import api_CTDT.Models.HPThayThe;
import api_CTDT.Models.LopHocPhan;
import api_CTDT.Models.NoiDungChuongTrinh;
import api_CTDT.Models.SinhVien;
import api_CTDT.Models.SinhVienLopHocPhan;
import api_CTDT.services.ChuongtrinhService;
import api_CTDT.services.HPTTService;
import api_CTDT.services.LopHocPhanService;
import api_CTDT.services.NDCTService;
import api_CTDT.services.SinhVienLopHocPhanService;
import api_CTDT.services.SinhVienService;

@Controller
@RequestMapping("/api/svlhp")

public class SinhVienLopHocPhanController {
	@Autowired
	SinhVienLopHocPhanService svlhpService;
	@Autowired
	LopHocPhanService lopHocPhanService;
	@Autowired
	SinhVienService sinhVienService;
	@Autowired
	NDCTService ndctService;
	@Autowired
	ChuongtrinhService chuongtrinhService;
	@Autowired
	HPTTService hpttService;

	@GetMapping("/getAll")

	public ResponseEntity<List<SinhVienLopHocPhan>> getAll() {
		List<SinhVienLopHocPhan> listSVLHP = svlhpService.getAll();
		return ResponseEntity.ok(listSVLHP);
	}
	
	@PostMapping(path = "/create",  produces = "application/json; charset=utf-8" )
	public ResponseEntity<Boolean> themSVLHP(@RequestBody List<SinhVienLopHocPhan> listSVLHP) {
		Boolean result = svlhpService.themSinhVienLHP(listSVLHP);
		return ResponseEntity.ok(result);
	}

	@PutMapping(path = "/update",  produces = "application/json; charset=utf-8" )
	public ResponseEntity<Boolean> capNhatSVLHP(@RequestBody SinhVienLopHocPhan sinhVien) {
		Boolean result = svlhpService.capNhatSinhVienLHP(sinhVien);
		return ResponseEntity.ok(result);
	}
	
	@GetMapping("/get/{id}")
	public ResponseEntity<SinhVienLopHocPhan> laySVLHP(@PathVariable int id) {
		SinhVienLopHocPhan svlhp = svlhpService.laySinhVienLHP(id);
		return ResponseEntity.ok(svlhp);
	}
	
	@GetMapping("/findByMaSV/{masv}")
	public ResponseEntity<List<SinhVienLopHocPhan>> laySVLHPBangMaSV(@PathVariable String masv) {
		List<SinhVienLopHocPhan> list = svlhpService.layBangMaSV(masv);
		return ResponseEntity.ok(list);
	}
	
	@GetMapping("/checkSVLHP/{masv}")
	public ResponseEntity<List<SinhVienLopHocPhan>> checkSVLHP(@PathVariable String masv) {
		List<SinhVienLopHocPhan> list = svlhpService.checkSVLHP(masv);
		return ResponseEntity.ok(list);
	}
	
	@GetMapping("/inbangdiem/{masv}")
	public ResponseEntity<List<SinhVienLopHocPhan>> inBangDiem(@PathVariable String masv) {
		SinhVien sinhVien = sinhVienService.laySinhVienTheoMaSV(masv);
		ChuongTrinh chuongTrinh = chuongtrinhService.getByNganhAndChuyenNganhAndKhoa(sinhVien.getNganh(), sinhVien.getChuyenNganh(), sinhVien.getKhoa());
		List<NoiDungChuongTrinh> listNDCT = ndctService.getListByIDCT(chuongTrinh.getId());
		List<SinhVienLopHocPhan> listSVLHP = svlhpService.layBangMaSV(masv);
		List<Integer> listHPDuocTT = new ArrayList<>();
		for (int i = 0; i < listSVLHP.size(); i++) {
			LopHocPhan lopHocPhanHT = lopHocPhanService.layLopHocPhan(listSVLHP.get(i).getLhp());
			double diemMax = listSVLHP.get(i).getDiem10();
			int index = i;
			// đầu tiên là check có học lại môn đó không
			for (int j = i + 1; j < listSVLHP.size(); j++) {
				LopHocPhan lopHocPhan = lopHocPhanService.layLopHocPhan(listSVLHP.get(j).getLhp());
				if (lopHocPhanHT.getHocPhan().equals(lopHocPhan.getHocPhan())) {
					if (listSVLHP.get(j).getDiem10() > diemMax) {
						diemMax = listSVLHP.get(j).getDiem10();
						listHPDuocTT.add(index);
						index = j;
					}
					else {
						listHPDuocTT.add(j);
					}
				}
			}
			// nếu không học lại, thì tìm hptt
			if (index == i) {
				for (int j = 0; j < listNDCT.size(); j++) {
					LopHocPhan lopHocPhan = lopHocPhanService.layLopHocPhan(listSVLHP.get(i).getLhp());
					if (listNDCT.get(j).getMa_hoc_phan().equals(lopHocPhan.getHocPhan())) {
						if (listNDCT.get(j).getId_hptt() != 0) {
							HPThayThe hpThayThe = hpttService.getByID(listNDCT.get(j).getId_hptt());
							List<Object> hptt = splitRecursively(hpThayThe.getHp_thaythe());
							Boolean check = checkTT(listSVLHP, hptt, listSVLHP.get(i).getDiem10());
							if (check) {
								listHPDuocTT.add(i);
								break;
							}
						}
					}
				}
			}
			// nếu không học lại, mà cũng không có học phần thay thế
			if (listSVLHP.get(i).getDiem10() < 4.0 && !listHPDuocTT.contains(i)) {
				listHPDuocTT.add(i);
			}
		}
		
		
		List<SinhVienLopHocPhan> list = new ArrayList<>();
		for (int i = 0; i < listSVLHP.size(); i++) {
			if (!listHPDuocTT.contains(i)) {
				list.add(listSVLHP.get(i));
			}
		}
		return ResponseEntity.ok(list);
	}
	
	@PostMapping(path = "/checkhoctruoc", produces = "application/json; charset=utf-8" ) 
	public ResponseEntity<Boolean> checkHocTruoc(@RequestBody SV_LHP svLhp) {
		List<Object> qhList = splitRecursively(svLhp.qh);
		List<SinhVienLopHocPhan> listSVLHP = svlhpService.layBangMaSV(svLhp.maSV);
		Boolean rs = checkHT(listSVLHP, qhList);
		return ResponseEntity.ok(rs);
	}
	
	@PostMapping(path = "/checktienquyet", produces = "application/json; charset=utf-8" ) 
	public ResponseEntity<Boolean> checkTienQuyet(@RequestBody SV_LHP svLhp) {
		List<Object> qhList = splitRecursively(svLhp.qh);
		List<SinhVienLopHocPhan> listSVLHP = svlhpService.layBangMaSV(svLhp.maSV);
		Boolean rs = checkTQ(listSVLHP, qhList);
		return ResponseEntity.ok(rs);
	}

	public static List<String> splitByParentheses(String expression) {
        List<String> output = new ArrayList<>();
        List<Character> stack = new ArrayList<>();
        StringBuilder current = new StringBuilder();
        for (int i = 0; i < expression.length(); i++) {
            char ch = expression.charAt(i);
            if (ch == '(') {
                if (stack.size() == 0 && current.toString().trim().length() != 0) {
                    output.add(current.toString().trim());
                    current = new StringBuilder();
                }
                stack.add(ch);
                current.append(ch);
            } else if (ch == ')') {
                current.append(ch);
                stack.remove(stack.size() - 1);
                if (stack.size() == 0) {
                    String innerExpression = current.substring(1, current.length() - 1);
                    output.add(innerExpression);
                    current = new StringBuilder();
                }
            } else {
                current.append(ch);
            }
        }
        if (current.toString().trim().length() != 0) {
            output.add(current.toString().trim());
        }
        return output;
    }

    public static List<Object> splitRecursively(String expression) {
        List<String> split = splitByParentheses(expression);
        List<Object> result = new ArrayList<>();
        for (String item : split) {
            if (item.contains("(") || item.contains(")")) {
                result.add(splitRecursively(item));
            } else {
                result.add(item);
            }
        }
        return result;
    }
	
    public Boolean checkHT(List<SinhVienLopHocPhan> listSVLHP, List<Object> qhList) {
    	Boolean check = false;
    	String dk = "";
    	if (qhList.contains("|")) {
    		dk = "|";
    	}
    	else if (qhList.contains("&")) {
    		dk = "&";
    	}
    	List<Object> newList = new ArrayList<>();
    	for (int a = 0; a < qhList.size(); a++) {
    		if (qhList.get(a) != dk) {
    			newList.add(qhList.get(a));
    		}
    	}
    	for (int i = 0; i < newList.size(); i++) {
    		Boolean subCheck1 = false;
    		if (newList.get(i) instanceof List) {
				List<Object> subList = (List<Object>) newList.get(i);
				subCheck1 = checkHT(listSVLHP, subList);
			}
			else {
				String subDK = "|";
				if (newList.get(i).toString().contains("|")) {
					subDK = "|";
				}
				else if (newList.get(i).toString().contains("&")) {
					subDK = "&";
				}
				if (newList.get(i).toString().contains(subDK)) {
					String[] subListQH = newList.get(i).toString().split(subDK);
					Boolean subCheck2 = false;
					for (int j = 0; j < subListQH.length; j++) {
						if (subListQH[j].trim() != dk) {
							for (int x = 0; x < listSVLHP.size(); x++) {
								LopHocPhan lhp = lopHocPhanService.layLopHocPhan(listSVLHP.get(x).getLhp());
								if (lhp.getHocPhan().equals(subListQH[j].trim())) {
									subCheck2 = true;
									break;
								}	
							}
							if (subDK == "&" && subCheck2 == false) {
								subCheck1 = false;
								break;
							}
							else if (subDK == "|" && subCheck2 == true) {
								subCheck1 = true;
								break;
							}
						}
					}
					subCheck1 = subCheck2;
				}
				else {
					for (int x = 0; x < listSVLHP.size(); x++) {
						LopHocPhan lhp = lopHocPhanService.layLopHocPhan(listSVLHP.get(x).getLhp());
						if (lhp.getHocPhan().equals(newList.get(i).toString().trim())) {
							subCheck1 = true;
							break;
						}
						
					}	
				}
			}
			if (dk == "&" && subCheck1 == false) {
				check = false;
				break;
			}
			else if (dk == "|" && subCheck1 == true) {
				check = true;
				break;
			}
        	check = subCheck1;
 
    	}

		return check;
    }
    
    public Boolean checkTQ(List<SinhVienLopHocPhan> listSVLHP, List<Object> qhList) {
    	Boolean check = false;
    	String dk = "";
    	if (qhList.contains("|")) {
    		dk = "|";
    	}
    	else if (qhList.contains("&")) {
    		dk = "&";
    	}
    	List<Object> newList = new ArrayList<>();
    	for (int a = 0; a < qhList.size(); a++) {
    		if (qhList.get(a) != dk) {
    			newList.add(qhList.get(a));
    		}
    	}
    	for (int i = 0; i < newList.size(); i++) {
    		Boolean subCheck1 = false;
    		if (newList.get(i) instanceof List) {
				List<Object> subList = (List<Object>) newList.get(i);
				subCheck1 = checkTQ(listSVLHP, subList);
			}
			else {
				String subDK = "|";
				if (newList.get(i).toString().contains("|")) {
					subDK = "|";
				}
				else if (newList.get(i).toString().contains("&")) {
					subDK = "&";
				}
				if (newList.get(i).toString().contains(subDK)) {
					String[] subListQH = newList.get(i).toString().split(subDK);
					Boolean subCheck2 = false;
					for (int j = 0; j < subListQH.length; j++) {
						if (subListQH[j].trim() != dk) {
							for (int x = 0; x < listSVLHP.size(); x++) {
								LopHocPhan lhp = lopHocPhanService.layLopHocPhan(listSVLHP.get(x).getLhp());
								if (lhp.getHocPhan().equals(subListQH[j].trim()) && listSVLHP.get(x).getDiem10() > 4.0) {
									subCheck2 = true;
									break;
								}	
							}
							if (subDK == "&" && subCheck2 == false) {
								subCheck1 = false;
								break;
							}
							else if (subDK == "|" && subCheck2 == true) {
								subCheck1 = true;
								break;
							}
						}
					}
					subCheck1 = subCheck2;
				}
				else {
					for (int x = 0; x < listSVLHP.size(); x++) {
						LopHocPhan lhp = lopHocPhanService.layLopHocPhan(listSVLHP.get(x).getLhp());
						if (lhp.getHocPhan().equals(newList.get(i).toString().trim()) && listSVLHP.get(x).getDiem10() > 4.0) {
							subCheck1 = true;
							break;
						}
						
					}	
				}
			}
			if (dk == "&" && subCheck1 == false) {
				check = false;
				break;
			}
			else if (dk == "|" && subCheck1 == true) {
				check = true;
				break;
			}
        	check = subCheck1;
 
    	}

		return check;
    }
    
    public Boolean checkTT(List<SinhVienLopHocPhan> listSVLHP, List<Object> qhList, double diemht) {
    	Boolean check = false;
    	String dk = "";
    	if (qhList.contains("|")) {
    		dk = "|";
    	}
    	else if (qhList.contains("&")) {
    		dk = "&";
    	}
    	List<Object> newList = new ArrayList<>();
    	for (int a = 0; a < qhList.size(); a++) {
    		if (qhList.get(a) != dk) {
    			newList.add(qhList.get(a));
    		}
    	}
    	for (int i = 0; i < newList.size(); i++) {
    		Boolean subCheck1 = false;
    		if (newList.get(i) instanceof List) {
				List<Object> subList = (List<Object>) newList.get(i);
				subCheck1 = checkTT(listSVLHP, subList, diemht);
			}
			else {
				String subDK = "|";
				if (newList.get(i).toString().contains("|")) {
					subDK = "|";
				}
				else if (newList.get(i).toString().contains("&")) {
					subDK = "&";
				}
				if (newList.get(i).toString().contains(subDK)) {
					String[] subListQH = newList.get(i).toString().split(subDK);
					Boolean subCheck2 = false;
					for (int j = 0; j < subListQH.length; j++) {
						if (subListQH[j].trim() != dk) {
							for (int x = 0; x < listSVLHP.size(); x++) {
								LopHocPhan lhp = lopHocPhanService.layLopHocPhan(listSVLHP.get(x).getLhp());
								if (lhp.getHocPhan().equals(subListQH[j].trim()) && listSVLHP.get(x).getDiem10() > diemht) {
									subCheck2 = true;
									break;
								}	
							}
							if (subDK == "&" && subCheck2 == false) {
								subCheck1 = false;
								break;
							}
							else if (subDK == "|" && subCheck2 == true) {
								subCheck1 = true;
								break;
							}
						}
					}
					subCheck1 = subCheck2;
				}
				else {
					for (int x = 0; x < listSVLHP.size(); x++) {
						LopHocPhan lhp = lopHocPhanService.layLopHocPhan(listSVLHP.get(x).getLhp());
						if (lhp.getHocPhan().equals(newList.get(i).toString().trim()) && listSVLHP.get(x).getDiem10() > diemht) {
							subCheck1 = true;
							break;
						}
						
					}	
				}
			}
			if (dk == "&" && subCheck1 == false) {
				check = false;
				break;
			}
			else if (dk == "|" && subCheck1 == true) {
				check = true;
				break;
			}
        	check = subCheck1;
 
    	}

		return check;
    }
    
	public static class SV_LHP {
		private String maSV;
		private String qh;
		public String getMaSV() {
			return maSV;
		}
		public void setMaSV(String maSV) {
			this.maSV = maSV;
		}
		public String getQh() {
			return qh;
		}
		public void setQh(String qh) {
			this.qh = qh;
		}
	}
	
	
//	@GetMapping("/inbangdiem/{masv}")
//	public ResponseEntity<List<SinhVienLopHocPhan>> inBangDiem(@PathVariable String masv) {
//		SinhVien sinhVien = sinhVienService.laySinhVienTheoMaSV(masv);
//		ChuongTrinh chuongTrinh = chuongtrinhService.getByNganhAndChuyenNganhAndKhoa(sinhVien.getNganh(), sinhVien.getChuyenNganh(), sinhVien.getKhoa());
//		List<NoiDungChuongTrinh> listNDCT = ndctService.getListByIDCT(chuongTrinh.getId());
//		List<SinhVienLopHocPhan> listSVLHP = svlhpService.layBangMaSV(masv);
//		List<Integer> listHPDuocTT = new ArrayList<>();
//		List<SinhVienLopHocPhan> newList = new ArrayList<>();
//		for (SinhVienLopHocPhan svlhp : listSVLHP) {
//			if(svlhp.getDiem10()<4.0) {
//				for(NoiDungChuongTrinh ndct : listNDCT) {
//					if(lopHocPhanService.layLopHocPhan(svlhp.getLhp()).getHocPhan().equals(ndct.getMa_hoc_phan())) {
//						if(ndct.getId_hptt()!=0) {
//							List<Object> hptt = splitRecursively(hpttService.getByID(ndct.getId_hptt()).getHp_thaythe());
//							Boolean checkTT = checkTT(listSVLHP, hptt, svlhp.getDiem10());
//							if(checkTT&&svlhp.getDiem10()>=4.0) {
//								newList.add(svlhp);
//							}
//						}
//					}
//				}
//			}else if(svlhp.getDiem10()>=4.0){
//				for(NoiDungChuongTrinh ndct : listNDCT) {
//					if(lopHocPhanService.layLopHocPhan(svlhp.getLhp()).getHocPhan().equals(ndct.getMa_hoc_phan())) {
//						if(ndct.getId_hptt()!=0) {
//							System.out.println(svlhp.toString());
//							
//							for (SinhVienLopHocPhan svlhpsub : listSVLHP) {
//								if(lopHocPhanService.layLopHocPhan(svlhpsub.getLhp()).getHocPhan())
//							}
//						}
//					}
//				}
//			}
//		}
//		return ResponseEntity.ok(newList);
//	}
	
}
