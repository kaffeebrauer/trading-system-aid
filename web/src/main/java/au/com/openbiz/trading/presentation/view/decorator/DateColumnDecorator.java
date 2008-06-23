package au.com.openbiz.trading.presentation.view.decorator;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;

import org.displaytag.decorator.ColumnDecorator;


public class DateColumnDecorator implements ColumnDecorator {
	
	public String decorate(Object object) {
		Timestamp timestamp = (Timestamp) object;
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd MMM yy");
		return dateFormat.format(timestamp);
	}
	
}
