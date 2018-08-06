package com.grocery.response;

import com.grocery.model.ItemSubCategory;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by ThanhBeo on 16/09/2017.
 */

public class SubCategoryResponse extends BaseResponse implements Serializable {
    public ArrayList<ItemSubCategory> result;
}
