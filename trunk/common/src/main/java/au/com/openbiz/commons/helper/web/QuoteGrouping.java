package au.com.openbiz.commons.helper.web;

public enum QuoteGrouping {

	DAILY ("d"), 
	WEEKLY ("w"), 
	MONTHLY ("m");
	
	private final String value;
	
	private QuoteGrouping(String value) {
		this.value = value;
	}
	
	public String value() {return value;}
}
