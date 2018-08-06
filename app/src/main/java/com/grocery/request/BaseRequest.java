package com.grocery.request;

import com.grocery.api.APICommon;

public class BaseRequest {
    public String app_id;
    public int device_type;
    public String sign;

    public BaseRequest(String path_url) {
        app_id = APICommon.getAppId();
        device_type = APICommon.getDeviceType();
        sign = APICommon.getSign(APICommon.getApiKey(), path_url);
    }
}
