package com.grocery.response;

import com.grocery.model.ItemManagerProductsAdd;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by ThanhBeo on 19/09/2017.
 */

public class MangerProductsToAddResponse extends BaseResponse implements Serializable {
    public ArrayList<ItemManagerProductsAdd> result;
}
