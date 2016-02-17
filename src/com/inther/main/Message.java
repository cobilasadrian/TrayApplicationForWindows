package com.inther.main;

public class Message {

    private boolean isHeartbeat;
    private String timeReceived;

    private int lightSensorVal;
    private boolean pirSensorVal;

    // ---------------- GETTERS & SETTERS --------------------

    public void setPirSensorVal(boolean pirSensorVal) { this.pirSensorVal = pirSensorVal; }

    public void setLightSensorVal(int lightSensorVal) { this.lightSensorVal = lightSensorVal; }

    public void setTimeReceived(String timeReceived) { this.timeReceived = timeReceived; }

    public void setHeartbeat(boolean heartbeat) { isHeartbeat = heartbeat; }

    public boolean getPirSensorVal() { return pirSensorVal; }

    public int getLightSensorVal() { return lightSensorVal; }

    public String getTimeReceived() { return timeReceived; }

    public boolean isHeartbeat() { return isHeartbeat; }

    // -------------------------------------------------------

    /**
     * Default CONTRUCTOR
     */
    public Message(){

    }

    /**
     * CONSTRUCTOR
     * @param isHeartbeat boolean - Specifies if it is a control message from Arduino
     * @param pirSensorVal boolean - Value indicated by the PIR Sensor can be either 1 (movement present) or 0 (no movement)
     * @param lightSensorVal
     * @param timeReceived
     */
    public Message(boolean isHeartbeat, boolean pirSensorVal, int lightSensorVal, String timeReceived) {

        this.timeReceived = timeReceived;
        this.isHeartbeat = isHeartbeat;
        this.pirSensorVal = pirSensorVal;
        this.lightSensorVal = lightSensorVal;
    }

    /**
     * Returns the textual representation of a message
     * @Override
     * @return String
     */
    public String toString() {
        return "Message{" +
        		"isHeartbeat=" + isHeartbeat +
                "pirSensorVal=" + pirSensorVal +
                ", lightSensorVal=" + lightSensorVal +
                ", timeReceived='" + timeReceived + '}';
    }

}
