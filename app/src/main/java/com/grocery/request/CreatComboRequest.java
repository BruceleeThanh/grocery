package com.grocery.request;

import java.io.Serializable;

/**
 * Created by ThanhBeo on 26/09/2017.
 */

public class CreatComboRequest extends BaseRequest implements Serializable {
    public static final String PATH_URL = "/api/grocery/createComboOffer";
    private int UserID;
    private String ApiToken;
    private String end_date;
    private float combo_price;
    private String description;
    private String listProducts;
    private int is_groceryAdmin;

    public CreatComboRequest(int userID, String apiToken, String end_date, float combo_price, String description, String listProducts, int is_groceryAdmin) {
        super(PATH_URL);
        UserID = userID;
        ApiToken = apiToken;
        this.end_date = end_date;
        this.combo_price = combo_price;
        this.description = description;
        this.listProducts = listProducts;
        this.is_groceryAdmin = is_groceryAdmin;
    }
}
