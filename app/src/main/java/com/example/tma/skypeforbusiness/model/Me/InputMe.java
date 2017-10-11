package com.example.tma.skypeforbusiness.model.Me;

import java.io.Serializable;

/**
 * Created by tmvien on 2/17/17.
 */

public class InputMe implements Serializable {
    private int errorCode;
    private Me me;

    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

    public Me getMe() {
        return me;
    }

    public void setMe(Me me) {
        this.me = me;
    }
}
