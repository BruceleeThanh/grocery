package com.grocery.response;

import com.grocery.model.UserPurchase;

import java.io.Serializable;

/**
 * Created by ducth on 10/23/2017.
 */

public class UserPurchaseReponse extends BaseResponse implements Serializable {
    public UserPurchase result;
}
