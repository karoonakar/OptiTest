package com.sg.app.utils;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.Callable;

import com.sg.app.entities.HttpResponse;

/**
 * @author Karoonakar
 *
 */
public class HttpWorker implements Callable<HttpResponse> {

	private String url;
	
	private final String USER_AGENT = "Mozilla/5.0";
	
	public HttpWorker (String url){
		this.url=url;
	}

	public HttpResponse call() throws Exception {

		String inputLine;
		HttpResponse httpResponse = new HttpResponse();
		URL obj = new URL(url);
		HttpURLConnection con = (HttpURLConnection) obj.openConnection();
		con.setRequestMethod("GET");
		con.setRequestProperty("User-Agent", USER_AGENT);
				
		BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
		StringBuffer response = new StringBuffer();

		while ((inputLine = in.readLine()) != null) {
			response.append(inputLine);
		}
		in.close();

		httpResponse.setResponseCode(con.getResponseCode());
		httpResponse.setResponseString(response.toString());
		httpResponse.setUrl(this.url);
		
		return httpResponse;

	}
}
