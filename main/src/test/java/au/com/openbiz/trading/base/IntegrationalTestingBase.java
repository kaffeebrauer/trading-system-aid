package au.com.openbiz.trading.base;

import javax.sql.DataSource;

import org.springframework.test.AbstractTransactionalDataSourceSpringContextTests;

import au.com.openbiz.trading.logic.dao.currency.CurrencyDao;
import au.com.openbiz.trading.logic.dao.dividend.DividendDao;
import au.com.openbiz.trading.logic.dao.portfolio.PortfolioDao;
import au.com.openbiz.trading.logic.dao.security.SecurityDao;
import au.com.openbiz.trading.logic.dao.security.SnapshotDao;
import au.com.openbiz.trading.logic.dao.shareholding.ShareHoldingDao;
import au.com.openbiz.trading.logic.dao.technicalanalysis.TechnicalAnalysisDao;
import au.com.openbiz.trading.logic.dao.transaction.BuySellTransactionDao;
import au.com.openbiz.trading.logic.dao.transaction.TransactionDao;
import au.com.openbiz.trading.logic.dao.watchlist.WatchListDao;
import au.com.openbiz.trading.presentation.inputcontroller.MainInputController;


public abstract class IntegrationalTestingBase extends AbstractTransactionalDataSourceSpringContextTests {

	protected DividendDao dividendDao;
	protected CurrencyDao currencyDao;
	protected SecurityDao securityDao;
	protected TransactionDao transactionDao;
	protected ShareHoldingDao shareHoldingDao;
	protected TechnicalAnalysisDao technicalAnalysisDao;
	protected PortfolioDao portfolioDao;
	protected WatchListDao watchListDao;
	protected SnapshotDao snapshotDao;
	protected BuySellTransactionDao buySellTransactionDao;
	protected DataSource dataSource;
	
	public IntegrationalTestingBase() {
		setPopulateProtectedVariables(true);
		System.setProperty(MainInputController.ENV_VARIABLE, MainInputController.TEST_ENV);
	}
	
	@Override
	protected String[] getConfigLocations() {
		return new String[] {"applicationContext.xml"};
	}
	
	
	@Override
	protected void onSetUpInTransaction() throws Exception {
		setDataSource(dataSource);
		super.onSetUpInTransaction();
	}

	@Override
	protected void onTearDownInTransaction() throws Exception {
		cleanup();
		super.onTearDownInTransaction();
	}

	protected void cleanup() {
		deleteFromTables(new String[] {"TechnicalAnalysis", "Transaction", 
				"Dividend", "ShareHolding", "Portfolio", "Security", "WatchList", 
				"Currency", "Snapshot", "BuySellTransaction"});
	}
	
}
