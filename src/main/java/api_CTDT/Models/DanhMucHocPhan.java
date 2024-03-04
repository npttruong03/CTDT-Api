package api_CTDT.Models;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "danhmuchocphan")
@JsonIgnoreProperties(value={"hibernateLazyInitializer","handler","fieldHandler"})
public class DanhMucHocPhan {
	@Id
	@Column(name = "id", nullable = false)
	@PrimaryKeyJoinColumn(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@Column(name = "ma_hoc_phan", nullable = false, unique = true)
	private String ma_hoc_phan;
	
	@Column(name = "ten_hoc_phan", nullable = false)
	private String ten_hoc_phan;
	
	@Column(name = "id_loai_hoc_phan", nullable = false)
	private int id_loai_hoc_phan;
	
	@Column(name = "id_dml_hp", nullable = false)
	private int id_dml_hp;
	
	@Column(name = "so_tin_chi", nullable = false)
	private int so_tin_chi;
	
	@Column(name = "tc_ly_thuyet", nullable = false)
	private float tc_ly_thuyet;
	
	@Column(name = "tc_thuc_hanh", nullable = false)
	private float tc_thuc_hanh;
	
	@Column(name = "tc_do_an", nullable = false)
	private float tc_do_an;
	
	@Column(name = "hp_cot_loi", nullable = false)
	private boolean hp_cot_loi;
	
	@Column(name = "status", nullable = false)
	private boolean stt;
	
	@Column(name = "tg_batdau", length = 20)
	private String tg_batdau;
	
	@Column(name = "tg_ketthuc", length = 20)
	private String tg_ketthuc;
	
	@Column(name = "ghi_chu")
	private String ghi_chu;
	
}
