package api_CTDT.Models;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "lophocphan")
@JsonIgnoreProperties(value={"hibernateLazyInitializer","handler","fieldHandler"})
public class LopHocPhan {
	@Id
	@Column(name = "id", nullable = false)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@Column(name = "ma_lop", nullable = false)
	private String maLop;
	
	@Column(name = "hoc_phan", nullable = false)
	private String hocPhan;
	
	@Column(name = "giang_vien", nullable = false)
	private String giangVien;

	@Column(name = "hoc_ki", nullable = false)
	private Integer hocKi;
	
	@Column(name = "nam_hoc", nullable = false)
	private String namHoc;
}
