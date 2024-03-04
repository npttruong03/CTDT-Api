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
@Table(name = "hpthaythe")
@JsonIgnoreProperties(value={"hibernateLazyInitializer","handler","fieldHandler"})
public class HPThayThe {
	@Id
	@Column(name = "id", nullable = false)
	@PrimaryKeyJoinColumn(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	@Column(name = "ma_hoc_phan", nullable = false)
	private String ma_hoc_phan;
	@Column(name = "hp_thaythe", nullable = false)
	private String hp_thaythe;
	@Column(name = "status", nullable = false)
	private boolean stt;
	

}
