package au.com.openbiz.trading.logic.manager.technicalanalysis.impl;

import au.com.openbiz.trading.persistent.technicalanalysis.TechnicalAnalysis;

public interface TechnicalAnalysisManager {

	void save(TechnicalAnalysis technicalAnalysis);
	
	TechnicalAnalysis findById(Integer id);
}
