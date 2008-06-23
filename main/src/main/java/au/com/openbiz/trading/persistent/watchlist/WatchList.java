package au.com.openbiz.trading.persistent.watchlist;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;

import au.com.openbiz.trading.persistent.security.Security;

@Entity
public class WatchList implements Serializable {

	private static final long serialVersionUID = 2416460625347805486L;

	private Integer id;
	private String name;
	private String description;
	private Set<Security> securities = new HashSet<Security>();
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="id")
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	
	@Column(name="name", nullable=false, length=50, unique=true)
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	@Column(name="description", length=250)
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
	@OneToMany(fetch=FetchType.EAGER)
	@JoinColumn(name="watchlist_fk", nullable=true)
	public Set<Security> getSecurities() {
		return new HashSet<Security>(securities);
	}
	protected void setSecurities(Set<Security> securities) {
		this.securities = securities;
	}
	public void addSecurity(Security security) {
		securities.add(security);
	}
	public void removeSecurity(Security security) {
		securities.remove(security);
	}
	
}
