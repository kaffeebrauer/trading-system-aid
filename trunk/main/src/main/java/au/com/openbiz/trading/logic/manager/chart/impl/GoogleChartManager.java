package au.com.openbiz.trading.logic.manager.chart.impl;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.log4j.Logger;

import au.com.openbiz.commons.helper.decimal.BigDecimalHelper;
import au.com.openbiz.trading.logic.calculator.impl.MarketValueCalculator;
import au.com.openbiz.trading.logic.manager.chart.ChartManager;
import au.com.openbiz.trading.logic.manager.security.SecurityPriceManager;
import au.com.openbiz.trading.logic.manager.transaction.TransactionManager;
import au.com.openbiz.trading.persistent.portfolio.Portfolio;
import au.com.openbiz.trading.persistent.security.SecurityPrice;
import au.com.openbiz.trading.persistent.transaction.BuyTransaction;

public class GoogleChartManager implements ChartManager {

	private final Logger LOGGER = Logger.getLogger(GoogleChartManager.class);
	
	private TransactionManager transactionManager;
	private SecurityPriceManager securityPriceManager;

	private final String AMP = "&";
	private final String COMMA = ",";
	private final String PIPE = "|";
	
	private String baseUrl;
	private String typeTag;
	private String sizeTag;
	private String dataTag;
	private String labelTag;
	
	public GoogleChartManager(final String baseUrl, final String typeTag, final String sizeTag,
			final String dataTag, final String labelTag) {
		this.baseUrl = baseUrl;
		this.typeTag = typeTag;
		this.sizeTag = sizeTag;
		this.dataTag = dataTag;
		this.labelTag = labelTag;
	}
	
	
	public String buildChartUrl(final Portfolio portfolio, final GoogleChartType chartType, final String size) {
		if(portfolio == null || portfolio.getId() == null) {
			throw new IllegalArgumentException("A portfolio is required to build a chart url.");
		}
		
		List<BuyTransaction> buyTransactions = transactionManager.findAllBuyTransactionsThatHaveNotBeenSold(portfolio.getId());
		if(buyTransactions.size() == 0) {
			throw new RuntimeException("Not enough buy transactions to build a chart url.");
		}
		
		StringBuilder chartUrl = new StringBuilder(baseUrl);
		chartUrl
			.append(typeTag).append(chartType.getName()).append(AMP)
			.append(sizeTag).append(size).append(AMP);
		
		Map<String, BigDecimal> marketValueMap = new HashMap<String, BigDecimal>(); 
		for(BuyTransaction buyTransaction : buyTransactions) {
			final SecurityPrice securityPrice = securityPriceManager.findLastTradeStockPrice(buyTransaction.getSecurity());
			final BigDecimal marketValue = new MarketValueCalculator(securityPrice.getClosePrice(), buyTransaction.getQuantity()).calculate();
			final String securityCompleteCode = buyTransaction.getSecurity().getCompleteCode();
			
			if(marketValueMap.containsKey(securityCompleteCode)) {
				BigDecimal accumulatedMarketValue = marketValueMap.get(securityCompleteCode);
				accumulatedMarketValue = accumulatedMarketValue.add(marketValue);
				marketValueMap.put(securityCompleteCode, accumulatedMarketValue);
			} else {
				marketValueMap.put(securityCompleteCode, marketValue);
			}
		}
		
		final BigDecimal totalPortfolioValue = BigDecimalHelper.scaleAndRound(calculateTotalPortfolioValue(marketValueMap));
		LOGGER.info("Total portfolio value [" + totalPortfolioValue + "]");
		
		StringBuilder datatagBuilder = new StringBuilder(dataTag);
		StringBuilder labelstagBuilder = new StringBuilder(labelTag);
		for(Entry<String, BigDecimal> entry : marketValueMap.entrySet()) {
			BigDecimal value = BigDecimalHelper.scaleAndRound(entry.getValue());
			LOGGER.info("Calculating percentage for [" + entry.getKey() + "] with a slice value of [" + value + "]");
			
			BigDecimal slicePercent = BigDecimalHelper.scaleAndRound(value.divide(totalPortfolioValue, BigDecimalHelper.SCALE));
			datatagBuilder.append(slicePercent.multiply(new BigDecimal(100))).append(COMMA);
			labelstagBuilder.append(entry.getKey()).append(PIPE);
		}
		
		int indexOfLastComma = datatagBuilder.lastIndexOf(COMMA);
		datatagBuilder.replace(indexOfLastComma, indexOfLastComma + 1, AMP);
		chartUrl.append(datatagBuilder.toString());
		
		int indexOfLastPipe = labelstagBuilder.lastIndexOf(PIPE);
		labelstagBuilder.replace(indexOfLastPipe, indexOfLastPipe + 1, AMP);
		chartUrl.append(labelstagBuilder.toString());
		
		return chartUrl.toString();
	}
	
	private BigDecimal calculateTotalPortfolioValue(final Map<String, BigDecimal> marketValueMap) {
		BigDecimal totalPortfolioValue = BigDecimal.ZERO;
		for(BigDecimal value : marketValueMap.values()) {
			totalPortfolioValue = totalPortfolioValue.add(value);
		}
		return totalPortfolioValue;
	}
	
	public void setTransactionManager(TransactionManager transactionManager) {
		this.transactionManager = transactionManager;
	}
	public void setSecurityPriceManager(SecurityPriceManager securityPriceManager) {
		this.securityPriceManager = securityPriceManager;
	}
}
