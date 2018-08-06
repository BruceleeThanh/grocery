package com.grocery.response;

import com.grocery.model.ItemOrders;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by ducth on 10/23/2017.
 */

public class UserOrderResponse extends BaseResponse implements Serializable {
    public ArrayList<ItemOrders> result;
}
