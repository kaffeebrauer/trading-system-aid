package au.com.openbiz.trading.logic.dao.security.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.StringTokenizer;

import org.apache.log4j.Logger;

import au.com.openbiz.commons.helper.date.DateHelper;
import au.com.openbiz.commons.helper.web.YahooUrlHelper;
import au.com.openbiz.commons.web.WebDownloader;
import au.com.openbiz.trading.logic.dao.security.SecurityPriceDao;
import au.com.openbiz.trading.persistent.security.Security;
import au.com.openbiz.trading.persistent.security.SecurityPrice;


public class SecurityPriceDaoImpl implements SecurityPriceDao {

	private static Logger LOGGER = Logger.getLogger(SecurityPriceDaoImpl.class);
	
	// TODO Split Logic and DAOs
	// TODO Centralise URL management
	// TODO Exception treatment must be enhanced
	@SuppressWarnings("unchecked")
	public List<SecurityPrice> findStockPricesByDate(Security security, Date startDate, Date endDate) {
		LOGGER.debug("Finding stock prices for [" + security.getCompleteCode() 
				+ "] by date from[" + startDate + "] to [" + endDate + "]");
		String url = YahooUrlHelper.buildHistoricStockPricesUrl(security.getCompleteCode(), startDate, endDate);
		String response = WebDownloader.downloadWebPage(url);
		
		StringTokenizer linesTokenizer = new StringTokenizer(response, "\n");
		//First line is a header
		linesTokenizer.nextToken();
		
		String[] stockPriceArray;
		SecurityPrice stockPrice;
		List<SecurityPrice> stockPriceList = new ArrayList<SecurityPrice>();
		LOGGER.info("Number of stock prices[" + linesTokenizer.countTokens() + "]");
		while(linesTokenizer.hasMoreTokens()) {
			stockPriceArray = linesTokenizer.nextToken().split(",");
			stockPrice = new SecurityPrice();
			stockPrice.setSecurity(security);
			stockPrice.setDate(DateHelper.parseDate(stockPriceArray[0], DateHelper.FORMAT_YEAR_MONTH_DAY_WITH_DASHES, true));
			stockPrice.setOpenPrice(Double.parseDouble(stockPriceArray[1]));
			stockPrice.setHighPrice(Double.parseDouble(stockPriceArray[2]));
			stockPrice.setLowPrice(Double.parseDouble(stockPriceArray[3]));
			stockPrice.setClosePrice(Double.parseDouble(stockPriceArray[4]));
			stockPrice.setVolume(Long.parseLong(stockPriceArray[5]));
			stockPrice.setAdjustedPrice(Double.parseDouble(stockPriceArray[6]));
			if(LOGGER.isDebugEnabled()) LOGGER.debug(stockPrice);
			stockPriceList.add(stockPrice);
		}
		
		return stockPriceList;
	}
	
	public SecurityPrice findLastTradeStockPrice(Security security) {
		LOGGER.debug("Finding last trade stock price for [" + security.getCompleteCode() + "]");
		String url = YahooUrlHelper.buildOnLineStockPriceUrl(security.getCompleteCode());
		String response = WebDownloader.downloadWebPage(url);
		String[] stockPriceArray = response.split(",");
		SecurityPrice stockPrice = new SecurityPrice();
		
		stockPrice.setSecurity(security);
		stockPrice.setClosePrice(Double.parseDouble(stockPriceArray[1]));
		stockPrice.setDate(DateHelper.parseDate(patchDate(stockPriceArray[2], stockPriceArray[3]), 
				DateHelper.FORMAT_DAY_MONTH_YEAR_HOUR_MIN, true));
		stockPrice.setChange(Double.parseDouble(stockPriceArray[4]));
		stockPrice.setOpenPrice(Double.parseDouble(stockPriceArray[5]));
		stockPrice.setHighPrice(Double.parseDouble(stockPriceArray[6]));
		stockPrice.setLowPrice(Double.parseDouble(stockPriceArray[7]));
		stockPrice.setVolume(Long.parseLong(stockPriceArray[8].substring(0, stockPriceArray[8].length()-2)));

		if(LOGGER.isDebugEnabled()) LOGGER.debug(stockPrice);
		return stockPrice;
	}
	
	//TODO if not found return an empty object
	public SecurityPrice findStockPriceForADate(Security security, Date date) {
		return findStockPricesByDate(security, date, date).get(0);
	}
	
	private String patchDate(String date, String time) {
		String dateResult = date.substring(1, date.length()-1);
		String[] dateSplitted = dateResult.split("/");
		if(dateSplitted[0].length() == 1) {
			dateResult = "0" + dateResult;
		}
		
		String timeResult = time.substring(1, time.length()-1);
		String[] timeSplitted = timeResult.split(":");
		if(timeSplitted[0].length() == 1) {
			timeResult = "0" + timeResult;
		}
		return dateResult + timeResult;
	}

}
