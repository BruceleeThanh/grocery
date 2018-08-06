package com.grocery.request;

import java.io.Serializable;

/**
 * Created by ThanhBeo on 14/10/2017.
 */

public class SendOrderConfirmRequest extends BaseRequest implements Serializable {
    public static final String PATH_URL = "/api/grocery/sendOrderConfirmation";
    private int UserID;
    private String ApiToken;
    private int order_id;
    private int is_groceryAdmin;

    public SendOrderConfirmRequest(int userID, String apiToken, int order_id, int is_groceryAdmin) {
        super(PATH_URL);
        UserID = userID;
        ApiToken = apiToken;
        this.order_id = order_id;
        this.is_groceryAdmin = is_groceryAdmin;
    }
}
