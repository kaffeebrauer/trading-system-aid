package au.com.openbiz.trading.presentation.form.reports;


public class HistoricalPerformanceForm {

	private Integer portfolioId;
	private String from;
	private String to;
	private String fromMonthYear;
	private String toMonthYear;

	public Integer getPortfolioId() {
		return portfolioId;
	}
	public void setPortfolioId(Integer portfolioId) {
		this.portfolioId = portfolioId;
	}
	public String getFrom() {
		return from;
	}
	public void setFrom(String from) {
		this.from = from;
	}
	public String getTo() {
		return to;
	}
	public void setTo(String to) {
		this.to = to;
	}
	public String getFromMonthYear() {
		return fromMonthYear;
	}
	public void setFromMonthYear(String fromMonthYear) {
		this.fromMonthYear = fromMonthYear;
	}
	public String getToMonthYear() {
		return toMonthYear;
	}
	public void setToMonthYear(String toMonthYear) {
		this.toMonthYear = toMonthYear;
	}
}
