package au.com.openbiz.commons.web;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.methods.GetMethod;

public class WebDownloader {
	
	public static String downloadWebPage(String url) {
		HttpClient httpClient = new HttpClient();
		HttpMethod httpMethod = new GetMethod(url);
		String response = "";
		try {
			httpClient.executeMethod(httpMethod);
			// TODO Change to getResponseBodyAsStream
			response = httpMethod.getResponseBodyAsString();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			httpMethod.releaseConnection();
		}
		return response;
	}
}
