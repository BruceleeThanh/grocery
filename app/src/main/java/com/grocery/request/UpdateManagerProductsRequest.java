package com.grocery.request;

import java.io.Serializable;

/**
 * Created by ThanhBeo on 20/09/2017.
 */

public class UpdateManagerProductsRequest extends BaseRequest implements Serializable {
    public static final String PATH_URL = "/api/grocery/updateProduct";private int UserID;
    private String ApiToken;
    private String listDelete;
    private String listAdd;
    private int is_groceryAdmin;

    public UpdateManagerProductsRequest(int userID, String apiToken, String listDelete, String listAdd, int is_groceryAdmin) {
        super(PATH_URL);
        UserID = userID;
        ApiToken = apiToken;
        this.listDelete = listDelete;
        this.listAdd = listAdd;
        this.is_groceryAdmin = is_groceryAdmin;
    }

}
