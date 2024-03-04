package api_CTDT.Models;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "nganh")
@JsonIgnoreProperties(value={"hibernateLazyInitializer","handler","fieldHandler"})
public class Nganh {
	@Id
	@Column(name = "id", nullable = false)
	@PrimaryKeyJoinColumn(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	@Column(name = "ma_nganh", nullable = false)
	private String ma_nganh;
	@Column(name = "ten_nganh", nullable = false)
	private String ten_nganh;
	@Column(name = "status", nullable = false)
	private Boolean stt;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_linhvuc")
	private LinhVuc linhvuc;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_khoaquanly")
	private KhoaQuanLy khoaquanly;


}
