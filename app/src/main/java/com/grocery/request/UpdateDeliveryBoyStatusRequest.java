package com.grocery.request;

import java.io.Serializable;

/**
 * Created by ThanhBeo on 15/10/2017.
 */

public class UpdateDeliveryBoyStatusRequest extends BaseRequest implements Serializable {
    public static final String PATH_URL = "/api/groceryUser/updateDeliveryBoyStatus";
    private int UserID;
    private String ApiToken;
    private int delivery_boy_id;
    private int is_groceryAdmin;
    private int is_active;


    public UpdateDeliveryBoyStatusRequest(int userID, String apiToken, int delivery_boy_id,
                                          int is_groceryAdmin, int is_active) {
        super(PATH_URL);
        UserID = userID;
        ApiToken = apiToken;
        this.delivery_boy_id = delivery_boy_id;
        this.is_groceryAdmin = is_groceryAdmin;
        this.is_active = is_active;
    }
}
