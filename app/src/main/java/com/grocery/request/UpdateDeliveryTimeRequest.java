package com.grocery.request;

import java.io.Serializable;

/**
 * Created by ThanhBeo on 15/10/2017.
 */

public class UpdateDeliveryTimeRequest extends BaseRequest implements Serializable {
    public static final String PATH_URL = "/api/groceryUser/updateDeliveryTiming";
    private int UserID;
    private String ApiToken;
    private int delivery_timing;
    private int despatching_timing;
    private String monday;
    private String tuesday;
    private String wednesday;
    private String thursday;
    private String friday;
    private String saturday;
    private String sunday;
    private int is_groceryAdmin;

    public UpdateDeliveryTimeRequest(int userID, String apiToken, int delivery_timing, int despatching_timing, String monday, String tuesday, String wednesday, String thursday, String friday, String saturday, String sunday, int is_groceryAdmin) {
        super(PATH_URL);
        UserID = userID;
        ApiToken = apiToken;
        this.delivery_timing = delivery_timing;
        this.despatching_timing = despatching_timing;
        this.monday = monday;
        this.tuesday = tuesday;
        this.wednesday = wednesday;
        this.thursday = thursday;
        this.friday = friday;
        this.saturday = saturday;
        this.sunday = sunday;
        this.is_groceryAdmin = is_groceryAdmin;
    }
}
