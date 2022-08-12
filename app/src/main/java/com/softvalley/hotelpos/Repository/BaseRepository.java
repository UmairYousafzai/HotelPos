package com.softvalley.hotelpos.Repository;

import com.softvalley.hotelpos.Interface.CallBackListener;

public abstract class BaseRepository {
    protected CallBackListener callBackListener;

    public void setCallBackListener(CallBackListener callBackListener) {
        this.callBackListener = callBackListener;
    }
}
