package com.grocery.request;

import java.io.Serializable;

/**
 * Created by ThanhBeo on 03/10/2017.
 */

public class UpdateNormalOrderRequets extends BaseRequest implements Serializable {
    public static final String PATH_URL = "/api/grocery/updateNormalOrder";
    private int UserID;
    private String ApiToken;
    private int order_id;
    private int update_type;
    private int delivery_boy_id;
    private String delivery_time;
    private int reject_type;
    private String reject_comment;
    private int is_groceryAdmin;

    public UpdateNormalOrderRequets(int userID, String apiToken, int order_id, int update_type, int delivery_boy_id, String delivery_time, int reject_type, String reject_comment, int is_groceryAdmin) {
        super(PATH_URL);
        UserID = userID;
        ApiToken = apiToken;
        this.order_id = order_id;
        this.update_type = update_type;
        this.delivery_boy_id = delivery_boy_id;
        this.delivery_time = delivery_time;
        this.reject_type = reject_type;
        this.reject_comment = reject_comment;
        this.is_groceryAdmin = is_groceryAdmin;
    }
}
