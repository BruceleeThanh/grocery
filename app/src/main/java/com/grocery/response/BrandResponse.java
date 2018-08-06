package com.grocery.response;

import com.grocery.model.ItemBrand;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by ThanhBeo on 18/09/2017.
 */

public class BrandResponse extends BaseResponse implements Serializable {
    public ArrayList<ItemBrand> result;
}
