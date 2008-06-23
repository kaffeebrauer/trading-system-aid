package au.com.openbiz.trading.presentation.form.transaction;

public class UpdateBuySellTransactionForm {

	private String buyTransactionIds;
	private String sellTransactionIds;

	public String getBuyTransactionIds() {
		return buyTransactionIds;
	}
	public void setBuyTransactionIds(String buyTransactionIds) {
		this.buyTransactionIds = buyTransactionIds;
	}
	public String getSellTransactionIds() {
		return sellTransactionIds;
	}
	public void setSellTransactionIds(String sellTransactionIds) {
		this.sellTransactionIds = sellTransactionIds;
	}
}
