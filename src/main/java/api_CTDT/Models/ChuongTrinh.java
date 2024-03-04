package api_CTDT.Models;

import java.sql.Timestamp;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "chuongtrinh")
@JsonIgnoreProperties(value={"hibernateLazyInitializer","handler","fieldHandler"})
public class ChuongTrinh {
	@Id
	@Column(name = "id", nullable = false)
	@PrimaryKeyJoinColumn(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@Column(name = "ma_chuong_trinh", nullable = false)
	private String ma_chuong_trinh;
	
	@Column(name = "ten_chuong_trinh", nullable = false)
	private String ten_chuong_trinh;
	
	@Column(name = "nam_ap_dung")
	private int nam_ap_dung;
	
	@Column(name = "nam_het_han")
	private int nam_het_han;
	
	@Column(name = "khoa_ap_dung", nullable = false)
	private String khoa_ap_dung;
	
	@Column(name = "tong_so_tin_chi", nullable = false)
	private int tong_so_tin_chi;
	
	@Column(name = "id_nganh", nullable = false)
	private int id_nganh;
	
	@Column(name = "id_chuyen_nganh", nullable = false)
	private Integer id_chuyen_nganh;
	
	@Column(name = "status")
	private Boolean stt;
	
	@OneToOne
	@PrimaryKeyJoinColumn
	private KhoiKienThuc khoiKienThuc;

	@OneToOne
	@PrimaryKeyJoinColumn
	private Nganh nganh;

}
