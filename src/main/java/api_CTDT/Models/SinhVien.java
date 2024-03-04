package api_CTDT.Models;

import java.util.List;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "sinhvien")
@JsonIgnoreProperties(value={"hibernateLazyInitializer","handler","fieldHandler"})
public class SinhVien {
	@Id
	@Column(name = "id", nullable = false)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@Column(name = "maSV", nullable = false, unique = true)
	private String maSV;
	
	@Column(name = "ho_ten", nullable = false)
	private String hoTen;
	
	@Column(name = "khoa", nullable = false)
	private String khoa;
	
	@Column(name = "nganh", nullable = false)
	private Integer nganh;
	
	@Column(name = "chuyen_nganh", nullable = true)
	private Integer chuyenNganh;
	
	@Column(name = "hoc_ki_hien_tai", columnDefinition = "int(11) default 1", nullable = false) 
	private Integer hocKyHienTai;
}
