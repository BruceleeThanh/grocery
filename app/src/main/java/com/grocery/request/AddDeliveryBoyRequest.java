package com.grocery.request;

import java.io.Serializable;

/**
 * Created by ThanhBeo on 29/09/2017.
 */

public class AddDeliveryBoyRequest extends BaseRequest implements Serializable {
    public static final String PATH_URL = "/api/groceryUser/addDeliveryBoy";
    private int UserID;
    private String ApiToken;
    private int is_groceryAdmin;
    private String delivery_boy_name;
    private String delivery_boy_email;
    private String password;

    public AddDeliveryBoyRequest(int userID, String apiToken, int is_groceryAdmin,
                                 String delivery_boy_name, String delivery_boy_email, String password) {
        super(PATH_URL);
        UserID = userID;
        ApiToken = apiToken;
        this.is_groceryAdmin = is_groceryAdmin;
        this.delivery_boy_name = delivery_boy_name;
        this.delivery_boy_email = delivery_boy_email;
        this.password = password;
    }
}
