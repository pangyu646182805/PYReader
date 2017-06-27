package com.neuroandroid.pyreader.event;

/**
 * Created by NeuroAndroid on 2017/5/19.
 */

public class BaseEvent {
    public static final int EVENT_CHOOSE_SEX = 200;
    public static final int EVENT_RECOMMEND = 300;
    private int eventFlag;

    public int getEventFlag() {
        return eventFlag;
    }

    public BaseEvent setEventFlag(int eventFlag) {
        this.eventFlag = eventFlag;
        return this;
    }
}
