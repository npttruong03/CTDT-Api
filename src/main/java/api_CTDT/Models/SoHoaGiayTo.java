package api_CTDT.Models;

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
@Table(name = "sohoagiayto")
@JsonIgnoreProperties(value={"hibernateLazyInitializer","handler","fieldHandler"})
public class SoHoaGiayTo {
	@Id
	@Column(name = "id", nullable = false)
	@PrimaryKeyJoinColumn(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@Column(name = "ten_giay_to", nullable = false)
	private String ten_giay_to;
	
	@Column(name = "phan_can_cu", nullable = false)
	private String phan_can_cu;
	
	@Column(name = "phan_noi_dung", nullable = false, length = 5000)
	private String phan_noi_dung;
	
	@Column(name = "chiu_trach_nhiem", nullable = false)
	private String chiu_trach_nhiem;
	
	@Column(name = "status", nullable = false)
	private boolean status;
}
