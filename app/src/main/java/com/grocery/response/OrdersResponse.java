package com.grocery.response;

import com.grocery.model.ItemOrders;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by ThanhBeo on 29/09/2017.
 */

public class OrdersResponse extends BaseResponse implements Serializable {
    public ArrayList<ItemOrders> result;
}
