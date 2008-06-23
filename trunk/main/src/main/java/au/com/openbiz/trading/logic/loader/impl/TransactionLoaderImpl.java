package au.com.openbiz.trading.logic.loader.impl;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.StringTokenizer;

import au.com.openbiz.trading.logic.dao.currency.CurrencyDao;
import au.com.openbiz.trading.logic.dao.security.SecurityDao;
import au.com.openbiz.trading.logic.dao.technicalanalysis.TechnicalAnalysisDao;
import au.com.openbiz.trading.logic.dao.transaction.BuySellTransactionDao;
import au.com.openbiz.trading.logic.dao.transaction.TransactionDao;
import au.com.openbiz.trading.logic.loader.AbstractTemplateLoader;
import au.com.openbiz.trading.persistent.currency.Currency;
import au.com.openbiz.trading.persistent.security.Security;
import au.com.openbiz.trading.persistent.technicalanalysis.TechnicalAnalysis;
import au.com.openbiz.trading.persistent.transaction.BuySellTransaction;
import au.com.openbiz.trading.persistent.transaction.BuyTransaction;
import au.com.openbiz.trading.persistent.transaction.SellTransaction;
import au.com.openbiz.trading.persistent.transaction.Transaction;

public class TransactionLoaderImpl extends AbstractTemplateLoader {

	private TransactionDao transactionDao;
	private SecurityDao securityDao;
	private CurrencyDao currencyDao;
	private TechnicalAnalysisDao technicalAnalysisDao;
	private BuySellTransactionDao buySellTransactionDao;
	private String inputFile;
	
	@Override
	protected void createNewObject(StringTokenizer stringTokenizer) {
		String buySellType = stringTokenizer.nextToken();
		String securityCode = stringTokenizer.nextToken();
		// TODO Receive country code from input files
		Security security = securityDao.findByCode(securityCode, "AX");
		String currencyCode = stringTokenizer.nextToken();
		Currency currency = currencyDao.findByCode(currencyCode);
		BigDecimal price = new BigDecimal(stringTokenizer.nextToken());
		Long quantity = new Long(stringTokenizer.nextToken());
		Calendar calendar = Calendar.getInstance();
		String[] dateSplitted = stringTokenizer.nextToken().split("/");
		calendar.set(
				new Integer(dateSplitted[2]).intValue(), 
				new Integer(dateSplitted[0]).intValue()-1, 
				new Integer(dateSplitted[1]).intValue(), 
				10, 0, 0); //10AM by default
		BigDecimal brokerage = new BigDecimal(stringTokenizer.nextToken());
		String referenceNumber = stringTokenizer.nextToken();
		
		Transaction transaction;
		if(buySellType.equals("BUY")) {
			transaction = new BuyTransaction(security, currency, price, quantity);

			//Technical analysis in case of buy transaction
			BigDecimal stopLoss = new BigDecimal(stringTokenizer.nextToken());
			BigDecimal lowerChannel = new BigDecimal(stringTokenizer.nextToken());
			BigDecimal upperChannel = new BigDecimal(stringTokenizer.nextToken());
			TechnicalAnalysis technicalAnalysis = new TechnicalAnalysis(stopLoss, lowerChannel, upperChannel);
			technicalAnalysisDao.saveOrUpdate(technicalAnalysis);
			transaction.setTechnicalAnalysis(technicalAnalysis);
			
			transaction.setBrokerage(brokerage);
			transaction.setTimestamp(new Timestamp(calendar.getTimeInMillis()));
			transaction.setReferenceNumber(referenceNumber);
			transactionDao.saveOrUpdate((BuyTransaction)transaction);
		} else {
			transaction = new SellTransaction(security, currency, price, quantity);
			//Get the reference number of the buy transaction
			String buyReferenceNumber = stringTokenizer.nextToken();
			//Find the buy transaction using the unique ref num
			BuyTransaction buyTransaction = (BuyTransaction)transactionDao.findByReferenceNumber(buyReferenceNumber);
			//Create a buy sell transaction
			BuySellTransaction buySellTransaction = new BuySellTransaction();
			
			transaction.setBrokerage(brokerage);
			transaction.setTimestamp(new Timestamp(calendar.getTimeInMillis()));
			transaction.setReferenceNumber(referenceNumber);
			transactionDao.saveOrUpdate((SellTransaction)transaction);

			buyTransaction.addBuySellTransaction(buySellTransaction);
			((SellTransaction)transaction).addBuySellTransaction(buySellTransaction);
			
			//transactionDao.saveOrUpdate(buyTransaction);
			
			buySellTransactionDao.saveOrUpdate(buySellTransaction);
			
		}		
	}

	@Override
	protected String getInputFile() {
		return inputFile;
	}
	
	public void setInputFile(String inputFile) {
		this.inputFile = inputFile;
	}

	public void setTransactionDao(TransactionDao transactionDao) {
		this.transactionDao = transactionDao;
	}
	
	public void setSecurityDao(SecurityDao securityDao) {
		this.securityDao = securityDao;
	}
	
	public void setCurrencyDao(CurrencyDao currencyDao) {
		this.currencyDao = currencyDao;
	}
	
	public void setTechnicalAnalysisDao(
			TechnicalAnalysisDao technicalAnalysisDao) {
		this.technicalAnalysisDao = technicalAnalysisDao;
	}
	
	public void setBuySellTransactionDao(
			BuySellTransactionDao buySellTransactionDao) {
		this.buySellTransactionDao = buySellTransactionDao;
	}
}
