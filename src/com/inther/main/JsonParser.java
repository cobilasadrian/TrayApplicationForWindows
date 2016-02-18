package com.inther.main;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;

import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

public class JsonParser {

	/**
	 * The readAll() return JSON string from reader
	 */
	private static String readAll(Reader rd) throws IOException {
	    StringBuilder sb = new StringBuilder();
	    int cp;
	    while ((cp = rd.read()) != -1) {
	        sb.append((char) cp);
	    }
	    return sb.toString();
	}
	
	/**
	 * The parseJsonFromUrl() return Message object from JSON
	 * @url address where is Json
	 */
	public Message parseJsonFromUrl(String url){
		
		Message message = null;
		try {
			InputStream is = new URL(url).openStream();
			BufferedReader rd = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
			String jsonText = readAll(rd);
			JSONObject  json = new JSONObject(jsonText);
			message = new Message(((Boolean) json.get("isHeartbeat")).booleanValue(),
								((Boolean)json.get("pirSensorVal")).booleanValue(),
								(Integer) json.get("lightSensorVal"),
								json.get("timeReceived").toString());
			
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
		
			e.printStackTrace();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return message;
	}

}
