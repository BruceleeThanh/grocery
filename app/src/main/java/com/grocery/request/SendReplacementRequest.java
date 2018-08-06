package com.grocery.request;

import com.grocery.model.ItemProductsOrders;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by ThanhBeo on 07/10/2017.
 */

public class SendReplacementRequest extends BaseRequest implements Serializable {
    public static final String PATH_URL = "/api/grocery/sendReplacementItems";
    private int UserID;
    private String ApiToken;
    private int order_id;
    private int is_groceryAdmin;
    private ArrayList<ItemProductsOrders> listProducts;

    public SendReplacementRequest(int userID, String apiToken, int order_id, int is_groceryAdmin, ArrayList<ItemProductsOrders> listProducts) {
        super(PATH_URL);
        UserID = userID;
        ApiToken = apiToken;
        this.order_id = order_id;
        this.is_groceryAdmin = is_groceryAdmin;
        this.listProducts = listProducts;
    }
}
