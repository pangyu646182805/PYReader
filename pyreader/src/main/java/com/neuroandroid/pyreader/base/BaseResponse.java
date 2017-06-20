package com.neuroandroid.pyreader.base;

import java.io.Serializable;

/**
 * Created by NeuroAndroid on 2017/6/14.
 */

public class BaseResponse implements Serializable {
    private boolean ok;

    public boolean isOk() {
        return ok;
    }

    public void setOk(boolean ok) {
        this.ok = ok;
    }
}
