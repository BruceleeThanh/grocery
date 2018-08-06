package com.grocery.response;

import com.grocery.model.AdminModel;

import java.io.Serializable;

/**
 * Created by ThanhBeo on 16/09/2017.
 */

public class LoginResponse extends BaseResponse implements Serializable {
    public AdminModel result;
}
