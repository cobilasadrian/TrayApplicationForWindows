package com.inther.test;

import org.junit.Assert;
import org.junit.Test;

import com.inther.model.Message;
import com.inther.main.JsonParser;

import static com.inther.model.AppConfig.SENZOR_CURRENT_STATE_URL;



public class GetSensorCurrentDataTest {

	@Test
	public void test() {
		Message actualMessage = new Message(false, true, 46, "2016-01-17 15:04:40.0");
		Message expectedMessage = new JsonParser().getSensorCurrentData(SENZOR_CURRENT_STATE_URL);
		Assert.assertEquals(actualMessage.isHeartbeat(), expectedMessage.isHeartbeat()); 
		Assert.assertEquals(actualMessage.getTimeReceived(), expectedMessage.getTimeReceived()); 
		Assert.assertEquals(actualMessage.getLightSensorVal(), expectedMessage.getLightSensorVal()); 
		Assert.assertEquals(actualMessage.getPirSensorVal(), expectedMessage.getPirSensorVal()); 
	}

}
