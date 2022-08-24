package com.chen.sunnyweather.logic.model.realtime;

public class RealtimeResult {
    private Realtime realtime;

    public Realtime getRealtime() {
        return realtime;
    }

    public void setRealtime(Realtime realtime) {
        this.realtime = realtime;
    }

    public RealtimeResult() {
    }

    public RealtimeResult(Realtime realtime) {
        this.realtime = realtime;
    }
}
