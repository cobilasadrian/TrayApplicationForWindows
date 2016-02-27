package com.inther.main;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;

public class EditThresholds {
	/**
	 * The sendPostRequest() set Sensors settings, HBFrequency or LightThreshold
	 * @url address for Post request
	 * @json can be HBFrequency or lightThreshold in format JSON
	 */
	public int sendPostRequest(String url,String json){
		
		HttpClient httpClient = new DefaultHttpClient();
		int status = 0;
		try {
		    HttpPost request = new HttpPost(url);
		    StringEntity params =new StringEntity(json);
		    request.addHeader("content-type", "application/json");
		    request.addHeader("Accept","application/json");
		    request.setEntity(params);
		    HttpResponse response = httpClient.execute(request);
		    status = response.getStatusLine().getStatusCode();
		}catch (Exception ex) {
		    // handle exception here
		} finally {
		    httpClient.getConnectionManager().shutdown();
		}
		return status;	
	}

}
