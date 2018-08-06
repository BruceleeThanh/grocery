package com.grocery.response;

import com.grocery.model.UserModel;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by ducth on 10/21/2017.
 */

public class GetUserResponse extends BaseResponse implements Serializable {
    public ArrayList<UserModel> result;
}
