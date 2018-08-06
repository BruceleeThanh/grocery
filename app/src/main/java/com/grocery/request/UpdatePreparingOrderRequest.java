package com.grocery.request;

import java.io.Serializable;

/**
 * Created by ThanhBeo on 07/10/2017.
 */

public class UpdatePreparingOrderRequest extends BaseRequest implements Serializable {
    public static final String PATH_URL = "/api/grocery/updatePreparingOrder";
    private int UserID;
    private String ApiToken;
    private int order_id;
    private int delivery_boy_id;
    private int is_groceryAdmin;

    public UpdatePreparingOrderRequest( int userID, String apiToken, int order_id, int delivery_boy_id,
                                        int is_groceryAdmin) {
        super(PATH_URL);
        UserID = userID;
        ApiToken = apiToken;
        this.order_id = order_id;
        this.delivery_boy_id = delivery_boy_id;
        this.is_groceryAdmin = is_groceryAdmin;
    }
}
