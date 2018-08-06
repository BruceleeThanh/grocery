package com.grocery.response;

import com.grocery.model.ItemDeliveryBoy;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by ThanhBeo on 29/09/2017.
 */

public class DeliveryBoyResponse extends BaseResponse implements Serializable {
    public ArrayList<ItemDeliveryBoy> result;
}
