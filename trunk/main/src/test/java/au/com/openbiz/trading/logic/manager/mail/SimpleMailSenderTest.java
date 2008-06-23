package au.com.openbiz.trading.logic.manager.mail;

import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.test.AbstractDependencyInjectionSpringContextTests;

import au.com.openbiz.trading.presentation.inputcontroller.MainInputController;

public class SimpleMailSenderTest extends AbstractDependencyInjectionSpringContextTests {

	protected MailSender mailSender;
	
	public SimpleMailSenderTest() {
		setPopulateProtectedVariables(true);
		System.setProperty(MainInputController.ENV_VARIABLE, MainInputController.TEST_ENV);
	}
	
	@Override
	protected String[] getConfigLocations() {
		return new String[] {"applicationContext.xml", "applicationContext-mailing.xml"};
	}
	
	public void testSendEmail() throws Exception {
		SimpleMailMessage simpleMessage = new SimpleMailMessage();
		simpleMessage.setFrom("pablo.iorio@openbiz.com.au");
		simpleMessage.setTo("pablo_iorio@hotmail.com");
		simpleMessage.setText("Testing text");
		simpleMessage.setSubject("Testing subject");
		//mailSender.send(simpleMessage);
	}

}
