package au.com.openbiz.trading.persistent.security;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

/**
 * Represents a price indicator used for calculating a moving average
 */
public class SecurityPriceIndicator extends SecurityPrice {

	private static final long serialVersionUID = 8996170320243350227L;
	private double value;
	
	public void setValue(double value) {
		this.value = value;
	}
	
	public double getValue() {
		return value;
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return new ToStringBuilder(this, ToStringStyle.SIMPLE_STYLE)
			.append("date", this.getDate())
			.append("value", this.value)
			.append("security", this.getSecurity())
			.toString();
	}
}
