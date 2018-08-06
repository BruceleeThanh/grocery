package com.grocery.response;

import com.grocery.model.ItemArea;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by ThanhBeo on 16/10/2017.
 */

public class GetAreaResponse extends BaseResponse implements Serializable {
    public ArrayList<ItemArea> result;
}
