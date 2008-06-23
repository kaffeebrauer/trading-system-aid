package au.com.openbiz.trading.persistent.technicalanalysis;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.apache.commons.lang.builder.ToStringBuilder;

@Entity
public class TechnicalAnalysis implements Serializable {

	private static final long serialVersionUID = -2026922949592328051L;

	private Integer id;
	private BigDecimal stopLoss;
	private BigDecimal lowerChannel;
	private BigDecimal upperChannel;
	
	public TechnicalAnalysis() {}
	
	public TechnicalAnalysis(BigDecimal stopLoss) {
		this(stopLoss, null, null);
	}
	
	public TechnicalAnalysis(BigDecimal stopLoss, BigDecimal lowerChannel, BigDecimal upperChannel) {
		this.stopLoss = stopLoss;
		this.lowerChannel = lowerChannel;
		this.upperChannel = upperChannel;
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
	
	@Column(name="lower_channel", nullable=true)
	public BigDecimal getLowerChannel() {
		return lowerChannel;
	}
	public void setLowerChannel(BigDecimal lowerChannel) {
		this.lowerChannel = lowerChannel;
	}
	
	@Column(name="stop_loss", nullable=false)
	public BigDecimal getStopLoss() {
		return stopLoss;
	}
	public void setStopLoss(BigDecimal stopLoss) {
		this.stopLoss = stopLoss;
	}
	
	@Column(name="upper_channel", nullable=true)
	public BigDecimal getUpperChannel() {
		return upperChannel;
	}
	public void setUpperChannel(BigDecimal upperChannel) {
		this.upperChannel = upperChannel;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int PRIME = 31;
		int result = 1;
		result = PRIME * result + ((lowerChannel == null) ? 0 : lowerChannel.hashCode());
		result = PRIME * result + ((stopLoss == null) ? 0 : stopLoss.hashCode());
		result = PRIME * result + ((upperChannel == null) ? 0 : upperChannel.hashCode());
		return result;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		final TechnicalAnalysis other = (TechnicalAnalysis) obj;
		if (lowerChannel == null) {
			if (other.lowerChannel != null)
				return false;
		} else if (!lowerChannel.equals(other.lowerChannel))
			return false;
		if (stopLoss == null) {
			if (other.stopLoss != null)
				return false;
		} else if (!stopLoss.equals(other.stopLoss))
			return false;
		if (upperChannel == null) {
			if (other.upperChannel != null)
				return false;
		} else if (!upperChannel.equals(other.upperChannel))
			return false;
		return true;
	}
	
	@Override
	public String toString() {
		return new ToStringBuilder(this)
			.append("id", id)
			.append("stopLoss", stopLoss)
			.append("lowerChannel", lowerChannel)
			.append("upperChannel", upperChannel)
			.toString();
	}
	
}
