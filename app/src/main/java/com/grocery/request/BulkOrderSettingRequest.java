package com.grocery.request;

import java.io.Serializable;

/**
 * Created by ThanhBeo on 29/09/2017.
 */

public class BulkOrderSettingRequest extends BaseRequest implements Serializable {
    public static final String PATH_URL = "/api/grocery/updateBulkOrderSetting";
    private int UserID;
    private String ApiToken;
    private int setting_type;
    private int is_groceryAdmin;

    public BulkOrderSettingRequest(int userID, String apiToken, int setting_type, int is_groceryAdmin) {
        super(PATH_URL);
        UserID = userID;
        ApiToken = apiToken;
        this.setting_type = setting_type;
        this.is_groceryAdmin = is_groceryAdmin;
    }
}
