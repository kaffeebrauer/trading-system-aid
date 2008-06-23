package au.com.openbiz.trading.logic.manager.positionsummary.impl;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;

public final class PositionSummaryTransferObject implements Serializable {

	@SuppressWarnings("unchecked")
	public static final PositionSummaryTransferObject EMPTY_POSITION_SUMMARY = PositionSummaryTransferObject.valueOf(
			Collections.EMPTY_LIST, 
			BigDecimal.ZERO, 
			BigDecimal.ZERO, 
			BigDecimal.ZERO, 
			BigDecimal.ZERO);
	
	private static final long serialVersionUID = -8980779851182071159L;

	private final List<PositionSummaryValueObject> positionSummaryValueObject;
	private final BigDecimal marketValueTotal;
	private final BigDecimal differenceTotal;
	private final BigDecimal boughtAmountTotal;
	private final BigDecimal rocAverage;
	
	public static PositionSummaryTransferObject valueOf(
			List<PositionSummaryValueObject> positionSummaryValueObject,
			BigDecimal marketValueTotal,
			BigDecimal differenceTotal,
			BigDecimal boughtAmountTotal,
			BigDecimal rocAverage) {
		return new PositionSummaryTransferObject(
				positionSummaryValueObject, 
				marketValueTotal, 
				differenceTotal, 
				boughtAmountTotal, 
				rocAverage);
	}
	
	private PositionSummaryTransferObject(
			List<PositionSummaryValueObject> positionSummaryValueObject,
			BigDecimal marketValueTotal,
			BigDecimal differenceTotal,
			BigDecimal boughtAmountTotal,
			BigDecimal rocAverage) {
		this.positionSummaryValueObject = positionSummaryValueObject;
		this.marketValueTotal = marketValueTotal;
		this.differenceTotal = differenceTotal;
		this.boughtAmountTotal = boughtAmountTotal;
		this.rocAverage = rocAverage;
	}
	
	public List<PositionSummaryValueObject> getPositionSummaryValueObject() {
		return Collections.unmodifiableList(positionSummaryValueObject);
	}
	public BigDecimal getMarketValueTotal() {
		return marketValueTotal;
	}
	public BigDecimal getDifferenceTotal() {
		return differenceTotal;
	}
	public BigDecimal getBoughtAmountTotal() {
		return boughtAmountTotal;
	}
	public BigDecimal getRocAverage() {
		return rocAverage;
	}
}
