package au.com.openbiz.trading.presentation.utils;

import javax.servlet.http.HttpServletRequest;


public final class ControllerUtils {

	public static void publishMessage(HttpServletRequest request, String message) {
		request.getSession().setAttribute("message", message);
	}
}
