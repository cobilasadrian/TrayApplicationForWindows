package com.inther.test;

import static org.junit.Assert.*;

import org.junit.Test;

import com.inther.main.JsonParser;
import com.inther.main.Message;


public class ParseJsonFromUrlTest {
	private static String  url = "http://172.17.41.117:8080/RESTService_v2/sensor/current/"; //address to REST API

	@Test
	public void test() {
		Message actualMessage = new Message(false, true, 46, "2016-01-17 15:04:40.0");
		Message expectedMessage = new JsonParser().parseJsonFromUrl(url);
		assertEquals(actualMessage.isHeartbeat(), expectedMessage.isHeartbeat()); 
		assertEquals(actualMessage.getTimeReceived(), expectedMessage.getTimeReceived()); 
		assertEquals(actualMessage.getLightSensorVal(), expectedMessage.getLightSensorVal()); 
		assertEquals(actualMessage.getPirSensorVal(), expectedMessage.getPirSensorVal()); 
	}

}
