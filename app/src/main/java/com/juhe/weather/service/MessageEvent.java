package com.juhe.weather.service;

/**
 * Created by anjing on 2016/5/17.
 */
public class MessageEvent {
    private String address;

    public MessageEvent(String address) {
        this.address = address;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
