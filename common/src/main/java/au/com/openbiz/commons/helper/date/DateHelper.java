package au.com.openbiz.commons.helper.date;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.log4j.Logger;

public class DateHelper {

	public static String FORMAT_MONTH_YEAR_WITH_SLASHES = "MM/yyyy";
	public static String FORMAT_DAY_MONTH_YEAR_WITH_SLASHES = "dd/MM/yyyy";
	public static String FORMAT_DAY_MONTH_YEAR_WITH_DASHES = "dd-MMM-yy";
	public static String FORMAT_YEAR_MONTH_DAY_WITH_DASHES = "yyyy-MM-dd";
	public static String FORMAT_DAY_MONTH_YEAR_HOUR_MIN = "MM/dd/yyyyhh:mmaa";
	
	private static Logger LOGGER = Logger.getLogger(DateHelper.class);
	
	/**
	 * Wrapper of parseDate, but returning timestamp
	 * @return resulting timestamp 
	 */
	public static Timestamp parseDateToTimestamp(String date, String pattern, boolean lenient) {
		return new Timestamp(parseDate(date, pattern, lenient).getTime());
	}
	
	/**
	 * Parses a String date to a Date object.
	 * By default, parsing is lenient: If the input is not in the form used by this object's format 
	 * method but can still be parsed as a date, then the parse succeeds. Clients may insist on strict 
	 * adherence to the format by calling setLenient(false).
	 * @param date String date to parse
	 * @param pattern pattern for parsing
	 * @param lenient false means strict adherence to the pattern provided
	 * @return resulting Date object
	 */
	public static Date parseDate(String date, String pattern, boolean lenient) {
		SimpleDateFormat dateFormatter = new SimpleDateFormat(pattern);
		dateFormatter.setLenient(lenient);
		Date result = null;
		try {
			result = dateFormatter.parse(date);
		} catch (ParseException e) {
			LOGGER.error("While parsing date.", e);
		}
	    return result;
	}
	
	/**
	 * Formats a date object into a string.
	 * @param date date object to be formatted
	 * @param pattern pattern for parsing
	 * @param lenient false means strict adherence to the pattern provided
	 * @return resulting string
	 */
	public static String formatDate(Date date, String pattern, boolean lenient) {
		SimpleDateFormat dateFormatter = new SimpleDateFormat(pattern);
		dateFormatter.setLenient(lenient);
		return dateFormatter.format(date);
	}
	
	/**
	 * Returns true if the input date is todays date
	 * @param date
	 * @return
	 */
	public static boolean isTodayDate(final Date date) {
		String inputDate = formatDate(date, FORMAT_DAY_MONTH_YEAR_WITH_SLASHES, true);
		String todayDate = formatDate(new Date(System.currentTimeMillis()), FORMAT_DAY_MONTH_YEAR_WITH_SLASHES, true);
		return inputDate.equals(todayDate);
	} 
}
