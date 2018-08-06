package com.grocery.response;

import com.grocery.model.BulkOrderSetting;

import java.io.Serializable;

public class BulkOrderSettingResponse extends BaseResponse implements Serializable {
    public BulkOrderSetting result;
}
