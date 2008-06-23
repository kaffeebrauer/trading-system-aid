package au.com.openbiz.trading.persistent.security;

import java.io.Serializable;
import java.util.Date;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

/**
 * Represents the security prices downloaded from a web source
 */
public class SecurityPrice implements Serializable {

	private static final long serialVersionUID = -3748756671044876344L;

	private Date date;
	private double openPrice;
	private double highPrice;
	private double lowPrice;
	private double closePrice; //Last trade
	private long volume;
	private double adjustedPrice; //Close price adjusted for dividends and splits
	private double change;
	private Security security;
	
	public Security getSecurity() {
		return security;
	}
	public void setSecurity(Security security) {
		this.security = security;
	}
	public double getChange() {
		return change;
	}
	public void setChange(double change) {
		this.change = change;
	}
	public double getAdjustedPrice() {
		return adjustedPrice;
	}
	public void setAdjustedPrice(double adjustedPrice) {
		this.adjustedPrice = adjustedPrice;
	}
	public double getClosePrice() {
		return closePrice;
	}
	public void setClosePrice(double closePrice) {
		this.closePrice = closePrice;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public double getHighPrice() {
		return highPrice;
	}
	public void setHighPrice(double highPrice) {
		this.highPrice = highPrice;
	}
	public double getLowPrice() {
		return lowPrice;
	}
	public void setLowPrice(double lowPrice) {
		this.lowPrice = lowPrice;
	}
	public double getOpenPrice() {
		return openPrice;
	}
	public void setOpenPrice(double openPrice) {
		this.openPrice = openPrice;
	}
	public long getVolume() {
		return volume;
	}
	public void setVolume(long volume) {
		this.volume = volume;
	}
	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return new ToStringBuilder(this, ToStringStyle.SIMPLE_STYLE)
			.append("date", this.date)
			.append("adjustedPrice", this.adjustedPrice)
			.append("highPrice", this.highPrice)
			.append("change", this.change)
			.append("volume", this.volume)
			.append("closePrice", this.closePrice)
			.append("lowPrice", this.lowPrice)
			.append("openPrice", this.openPrice)
			.toString();
	}
}
