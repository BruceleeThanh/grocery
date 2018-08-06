package com.grocery.request;

import com.grocery.model.ItemProduct;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by ThanhBeo on 20/09/2017.
 */

public class SuggestProductsRequest extends BaseRequest implements Serializable {
    public static final String PATH_URL = "/api/grocery/suggestProduct";
    private int UserID;
    private String ApiToken;
    private ArrayList<ItemProduct> listProducts;
    private int is_groceryAdmin;

    public SuggestProductsRequest(int userID, String apiToken, ArrayList<ItemProduct> listProducts, int is_groceryAdmin) {
        super(PATH_URL);
        UserID = userID;
        ApiToken = apiToken;
        this.listProducts = listProducts;
        this.is_groceryAdmin = is_groceryAdmin;
    }
}
