package api_CTDT.Models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "chuyennganh")
@JsonIgnoreProperties(value={"hibernateLazyInitializer","handler","fieldHandler"})
public class ChuyenNganh {
	@Id
	@Column(name = "id", nullable = false)
	@PrimaryKeyJoinColumn(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	@Column(name = "ma_chuyen_nganh", nullable = false)
	private String ma_chuyen_nganh;
	@Column(name = "ten_chuyen_nganh", nullable = false)
	private String ten_chuyen_nganh;
	@Column(name = "status", columnDefinition = "tinyInt(1)", nullable = false)
	private Boolean stt;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_linhvuc")
	private LinhVuc linhvuc;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_khoaquanly")
	private KhoaQuanLy khoaquanly;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_nganh")
	private Nganh nganh;
}