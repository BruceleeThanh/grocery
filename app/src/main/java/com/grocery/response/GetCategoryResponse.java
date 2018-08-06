package com.grocery.response;

import com.grocery.model.ItemCategory;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by ThanhBeo on 16/09/2017.
 */

public class GetCategoryResponse extends BaseResponse implements Serializable{
    public ArrayList<ItemCategory> result;
}
