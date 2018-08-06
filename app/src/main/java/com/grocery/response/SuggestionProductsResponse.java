package com.grocery.response;

import com.grocery.model.ItemSuggestionProducts;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by ThanhBeo on 20/09/2017.
 */

public class SuggestionProductsResponse extends BaseResponse implements Serializable {
    public ArrayList<ItemSuggestionProducts> result;
}
