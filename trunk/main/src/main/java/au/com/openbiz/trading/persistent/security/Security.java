package au.com.openbiz.trading.persistent.security;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;

import org.apache.commons.lang.builder.ToStringBuilder;

import au.com.openbiz.commons.cache.ehcache.Cacheable;
import au.com.openbiz.trading.persistent.portfolio.Portfolio;

@Entity
public class Security implements Serializable, Cacheable, Comparable<Security> {

	private static final long serialVersionUID = 5224203200282149994L;

	private Integer id;
	private String code; //GGAL
	private String country; //BA
	private String description; //Grupo Financiero Galicia
	private Portfolio portfolio;

	public Security() {}
	
	/**
	 * @param code Yahoo code for the security e.g. GGAL, QBE, SGC
	 * @param country Yahoo country denomination for the share market e.g. AX for Australia, BA for Bs. As., Argentina
	 */
	public Security(String code, String country) {
		this(code, country, null);
	}
	
	public Security(String code, String country, String description) {
		this.code = code;
		this.country = country;
		this.description = description;
	}

	//Not persisted. e.g. GGAL.BA
	@Transient
	public String getCompleteCode() {
		if(country != null && !country.equals("")) {
			return code + "." + country;
		}
		return code;
	}
	
	@Id @GeneratedValue(strategy=GenerationType.AUTO) @Column(name="id")
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}

	@Column(name="code", nullable=false, length=5, unique=true)
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}

	@Column(name="country", nullable=false, length=3)
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}

	@Column(name="description", nullable=true, length=50)
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
	@ManyToOne
	@JoinColumn(name="portfolio_fk", nullable=true )
	public Portfolio getPortfolio() {
		return portfolio;
	}
	public void setPortfolio(Portfolio portfolio) {
		this.portfolio = portfolio;
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return new ToStringBuilder(this)
			.append("description", this.description)
			.append("completeCode", this.getCompleteCode())
			.append("country", this.country)
			.append("code", this.code)
			.toString();
	}

	public String generateCacheKey() {
		return this.id.toString();
	}

	public int compareTo(Security other) {
		return this.getCode().compareTo(other.getCode());
	}
	
	
	
}
