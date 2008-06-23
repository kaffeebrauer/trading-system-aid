package au.com.openbiz.trading.logic.manager.positionsummary;

import au.com.openbiz.trading.logic.manager.positionsummary.impl.PositionSummaryTransferObject;
import au.com.openbiz.trading.persistent.portfolio.Portfolio;

public interface PositionSummaryManager {

	PositionSummaryTransferObject calculatePositionSummary(final Portfolio portfolio);
}
