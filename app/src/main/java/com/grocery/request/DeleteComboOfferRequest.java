package com.grocery.request;

import java.io.Serializable;

/**
 * Created by ThanhBeo on 28/09/2017.
 */

public class DeleteComboOfferRequest extends BaseRequest implements Serializable {
    public static final String PATH_URL = "/api/grocery/deleteComboOffer";
    private int UserID;
    private String ApiToken;
    private int combo_offer_id;
    private int is_groceryAdmin;

    public DeleteComboOfferRequest(int userID, String apiToken, int combo_offer_id, int is_groceryAdmin) {
        super(PATH_URL);
        UserID = userID;
        ApiToken = apiToken;
        this.combo_offer_id = combo_offer_id;
        this.is_groceryAdmin = is_groceryAdmin;
    }
}
