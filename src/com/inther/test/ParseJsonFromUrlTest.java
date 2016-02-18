package com.inther.test;

import static org.junit.Assert.*;

import org.junit.Test;

import com.inther.main.JsonParser;
import com.inther.main.Message;
import static com.inther.model.AppConfig.URL;


public class ParseJsonFromUrlTest {

	@Test
	public void test() {
		Message actualMessage = new Message(false, true, 46, "2016-01-17 15:04:40.0");
		Message expectedMessage = new JsonParser().parseJsonFromUrl(URL);
		assertEquals(actualMessage.isHeartbeat(), expectedMessage.isHeartbeat()); 
		assertEquals(actualMessage.getTimeReceived(), expectedMessage.getTimeReceived()); 
		assertEquals(actualMessage.getLightSensorVal(), expectedMessage.getLightSensorVal()); 
		assertEquals(actualMessage.getPirSensorVal(), expectedMessage.getPirSensorVal()); 
	}

}
