package au.com.openbiz.trading.logic.manager.technicalanalysis.impl;

import au.com.openbiz.trading.logic.dao.technicalanalysis.TechnicalAnalysisDao;
import au.com.openbiz.trading.logic.manager.technicalanalysis.TechnicalAnalysisManager;
import au.com.openbiz.trading.persistent.technicalanalysis.TechnicalAnalysis;

public class DefaultTechnicalAnalysisManager implements TechnicalAnalysisManager {

	private TechnicalAnalysisDao technicalAnalysisDao;
	
	public void save(TechnicalAnalysis technicalAnalysis) {
		technicalAnalysisDao.saveOrUpdate(technicalAnalysis);
	}
	
	public TechnicalAnalysis findById(Integer id) {
		return technicalAnalysisDao.findById(id);
	}

	public void setTechnicalAnalysisDao(TechnicalAnalysisDao technicalAnalysisDao) {
		this.technicalAnalysisDao = technicalAnalysisDao;
	}
}
