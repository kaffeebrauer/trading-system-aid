package au.com.openbiz.trading.presentation.controller.security;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.validation.BindException;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.CancellableFormController;

import au.com.openbiz.trading.logic.manager.security.SecurityManager;
import au.com.openbiz.trading.persistent.security.Security;
import au.com.openbiz.trading.presentation.utils.ControllerUtils;


public class UpdateSecurityController extends CancellableFormController {

	private SecurityManager securityManager;

	@Override
	protected Object formBackingObject(HttpServletRequest request)
			throws Exception {
		Security security = null;

		if (request.getParameter("code") != null
				&& request.getParameter("country") != null) {
			String code = request.getParameter("code").toString();
			String country = request.getParameter("country").toString();
			security = securityManager.findByCodeAndCountry(code, country);
		}
		if (security == null) {
			security = new Security();
		}
		return security;
	}

	@Override
	protected ModelAndView onSubmit(HttpServletRequest request,
			HttpServletResponse response, Object command, BindException errors)
			throws Exception {
		Security security = (Security) command;
		securityManager.save(security);
		ControllerUtils.publishMessage(request, "The security " + security.getCode() + "." + security.getCountry() + " has been saved successfully.");
		return new ModelAndView(getSuccessView());
	}

	public void setSecurityManager(SecurityManager securityManager) {
		this.securityManager = securityManager;
	}
}
