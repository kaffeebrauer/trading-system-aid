package au.com.openbiz.trading.logic.manager.chart;

import au.com.openbiz.trading.logic.manager.chart.impl.GoogleChartType;
import au.com.openbiz.trading.persistent.portfolio.Portfolio;

public interface ChartManager {

	String buildChartUrl(final Portfolio portfolio, final GoogleChartType chartType, final String size);
}
