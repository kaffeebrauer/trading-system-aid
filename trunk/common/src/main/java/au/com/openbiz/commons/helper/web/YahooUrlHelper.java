package au.com.openbiz.commons.helper.web;

import java.util.Calendar;
import java.util.Date;

import org.apache.log4j.Logger;

public class YahooUrlHelper {

	private static Logger LOGGER = Logger.getLogger(YahooUrlHelper.class);
	
	/*
	 * Data is available approx 20 days later
	 * s - ticker symbol
	 * a - start month
	 * b - start day 
	 * c - start year
	 * d - end month
	 * e - end day
	 * f - end year
	 * g - resolution (e.g. 'd' is daily, 'w' is weekly, 'm' is monthly)
	 */
	public static String buildHistoricStockPricesUrl(String stockName, Date startDate, Date endDate, QuoteGrouping quoteGrouping) {
		Calendar calendarStart = Calendar.getInstance();
		calendarStart.setTime(startDate);
		Calendar calendarEnd = Calendar.getInstance();
		calendarEnd.setTime(endDate);
		
		StringBuffer url = new StringBuffer();
		url.append("http://table.finance.yahoo.com/table.csv?")
			.append("s=" + stockName.toLowerCase())
			.append("&a=" + calendarStart.get(Calendar.MONTH))
			.append("&b=" + calendarStart.get(Calendar.DAY_OF_MONTH))
			.append("&c=" + calendarStart.get(Calendar.YEAR))
			.append("&d=" + calendarEnd.get(Calendar.MONTH))
			.append("&e=" + calendarEnd.get(Calendar.DAY_OF_MONTH))
			.append("&f=" + calendarEnd.get(Calendar.YEAR))
			.append("&g=" + quoteGrouping.value());
		LOGGER.debug("Url created[" + url.toString() + "]");
		return url.toString();
	}
	
	public static String buildHistoricStockPricesUrl(String stockName, Date startDate, Date endDate) {
		return buildHistoricStockPricesUrl(stockName, startDate, endDate, QuoteGrouping.DAILY);
	}
	
	public static String buildOnLineStockPriceUrl(String stockName) {
		return "http://finance.yahoo.com/d/quotes.csv?s=" + stockName.toLowerCase() 
			+ "&f=sl1d1t1c1ohgv&e=.csv";
	}
}
