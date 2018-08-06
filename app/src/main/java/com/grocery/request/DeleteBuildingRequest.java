package com.grocery.request;

import java.io.Serializable;

/**
 * Created by ThanhBeo on 15/10/2017.
 */

public class DeleteBuildingRequest extends BaseRequest implements Serializable {
    public static final String PATH_URL = "/api/groceryUser/deleteBuilding";
    private int UserID;
    private String ApiToken;
    private int building_id;
    private int is_groceryAdmin;

    public DeleteBuildingRequest(int userID, String apiToken, int building_id, int is_groceryAdmin) {
        super(PATH_URL);
        UserID = userID;
        ApiToken = apiToken;
        this.building_id = building_id;
        this.is_groceryAdmin = is_groceryAdmin;
    }
}
