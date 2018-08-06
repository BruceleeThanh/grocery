package com.grocery.request;

import java.io.Serializable;

/**
 * Created by ducth on 10/18/2017.
 */

public class UpdateInStockStatusRequest extends BaseRequest implements Serializable {
    public static final String PATH_URL = "/api/grocery/updateInstockStatus";
    private int UserID;
    private String ApiToken;
    private int product_id;
    private int instock_status;
    private int is_groceryAdmin;

    public UpdateInStockStatusRequest( int userID, String apiToken, int product_id, int instock_status, int is_groceryAdmin) {
        super(PATH_URL);
        UserID = userID;
        ApiToken = apiToken;
        this.product_id = product_id;
        this.instock_status = instock_status;
        this.is_groceryAdmin = is_groceryAdmin;
    }
}
