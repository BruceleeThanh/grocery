package com.grocery.response;

import com.grocery.model.ItemManagerProductsUpdate;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by ThanhBeo on 26/09/2017.
 */

public class ManagerProductsToUpdatResqonse extends BaseResponse implements Serializable {
    public ArrayList<ItemManagerProductsUpdate> result;
}
