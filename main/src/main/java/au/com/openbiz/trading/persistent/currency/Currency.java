package au.com.openbiz.trading.persistent.currency;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.apache.commons.lang.builder.ToStringBuilder;

@Entity
public class Currency implements Serializable {

	private static final long serialVersionUID = -1781192216554719796L;
	
	private Integer id;
	private String code;
	private String description;
	private BigDecimal fxRate;
	private Timestamp creationTimestamp;
	
	public Currency() {}
	
	/**
	 * This constructor will initialize times with current time
	 * @param code Yahoo currency code
	 */
	public Currency(String code) {
		this(code, null, null, null);
	}
	
	public Currency(String code, String description, BigDecimal fxRate, Timestamp creationTimestamp) {
		this.code = code;
		this.description = description;
		this.fxRate = fxRate;
		this.creationTimestamp = creationTimestamp;
	}
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="id")
	public Integer getId() {
		return id;
	}
	protected void setId(Integer id) {
		this.id = id;
	}
	
	@Column(name="creation_timestamp", nullable=true )
	public Timestamp getCreationTimestamp() {
		return creationTimestamp;
	}
	public void setCreationTimestamp(Timestamp creationTimestamp) {
		this.creationTimestamp = creationTimestamp;
	}
	
	@Column(name="description", nullable=true, length=50)
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
	@Column(name="fx_rate", nullable=true)
	public BigDecimal getFxRate() {
		return fxRate;
	}
	public void setFxRate(BigDecimal fxRate) {
		this.fxRate = fxRate;
	}
	
	@Column(name="code", length=3, nullable=false, unique=true)
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	
	@Override
	public String toString() {
		return new ToStringBuilder(this)
			.append("id", id)
			.append("code", code)
			.append("description", description)
			.append("fxRate", fxRate)
			.append("creationTimestamp", creationTimestamp).toString();
	}
	
}
