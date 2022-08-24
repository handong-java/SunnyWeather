package com.chen.sunnyweather.logic.model.daily;

import java.util.Date;

public class Skycon {
    private String value;
    private Date date;

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Skycon() {
    }

    public Skycon(String value, Date date) {
        this.value = value;
        this.date = date;
    }
}
