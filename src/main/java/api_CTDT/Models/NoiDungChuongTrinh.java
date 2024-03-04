package api_CTDT.Models;

import java.sql.Timestamp;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "noidung_chuongtrinh")
@JsonIgnoreProperties(value={"hibernateLazyInitializer","handler","fieldHandler"})
public class NoiDungChuongTrinh {
	@Id
	@Column(name = "id", nullable = false)
	@PrimaryKeyJoinColumn(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	
	@Column(name = "ma_hoc_phan", nullable = false)
	private String ma_hoc_phan;
	
	@Column(name = "id_kkt", nullable = false)
	private int id_ktt;
	
	@Column(name = "id_danh_muc_lhp", nullable = false)
	private int id_danh_muc_lhp;
	
	@Column(name = "id_dinh_huong", nullable = false)
	private int id_dinh_huong;
	
	@Column(name = "id_mqh", nullable = true)
	private int id_mqh;
	
	@Column(name = "id_hptt", nullable = true)
	private int id_hptt;
	
	@Column(name = "id_chuongtrinh", nullable = false)
	private int id_chuongtrinh;
	
	@Column(name = "hoc_ky_du_kien", nullable = false)
	private int hoc_ky_du_kien;
	
	@Column(name = "status")
	private boolean stt;
	
}
