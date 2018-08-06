package com.grocery.response;

import com.grocery.model.OrderDetails;

import java.io.Serializable;

/**
 * Created by ThanhBeo on 02/10/2017.
 */

public class OrderDetailResponse extends BaseResponse implements Serializable {
    public OrderDetails result;
}
