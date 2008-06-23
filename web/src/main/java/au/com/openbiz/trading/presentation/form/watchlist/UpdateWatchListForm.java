package au.com.openbiz.trading.presentation.form.watchlist;

public class UpdateWatchListForm {

	private Integer watchListId;
	private String name;
	private String description;
	private String securityIds;

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getSecurityIds() {
		return securityIds;
	}
	public void setSecurityIds(String securityIds) {
		this.securityIds = securityIds;
	}
	public Integer getWatchListId() {
		return watchListId;
	}
	public void setWatchListId(Integer watchListId) {
		this.watchListId = watchListId;
	}

}
