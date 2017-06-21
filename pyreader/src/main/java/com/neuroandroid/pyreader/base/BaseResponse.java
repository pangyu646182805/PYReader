package com.neuroandroid.pyreader.base;

import java.io.Serializable;

/**
 * Created by NeuroAndroid on 2017/6/14.
 */

public class BaseResponse implements Serializable {
    private boolean ok;
    private boolean noOk;

    public boolean isNoOk() {
        return noOk;
    }

    public void setNoOk(boolean noOk) {
        this.noOk = noOk;
    }

    public boolean isOk() {
        return ok;
    }

    public void setOk(boolean ok) {
        this.ok = ok;
    }
}
