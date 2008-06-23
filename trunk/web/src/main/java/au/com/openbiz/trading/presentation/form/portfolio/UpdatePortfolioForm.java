package au.com.openbiz.trading.presentation.form.portfolio;

public class UpdatePortfolioForm {

	private Integer portfolioId;
	private String name;
	private String description;
	private String currencyCode;
	private String securityIds;
	
	public Integer getPortfolioId() {
		return portfolioId;
	}
	public void setPortfolioId(Integer portfolioId) {
		this.portfolioId = portfolioId;
	}
	public String getCurrencyCode() {
		return currencyCode;
	}
	public void setCurrencyCode(String currencyCode) {
		this.currencyCode = currencyCode;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getSecurityIds() {
		return securityIds;
	}
	public void setSecurityIds(String securityIds) {
		this.securityIds = securityIds;
	}
	
}
