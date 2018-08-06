package com.grocery.request;

import com.grocery.model.UnitModel;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by ThanhBeo on 26/09/2017.
 */

public class UpdateDetailRequest extends BaseRequest implements Serializable {
    public static final String PATH_URL = "/api/grocery/updateProductDetails";
    private int UserID;
    private String ApiToken;
    private int product_id;
    private int instock;
    private int min_stock;
    private int instock_status;
    private int offer_status;
    private String from_date;
    private String end_date;
    private int limit_number;
    private float offer_price;
    private int is_groceryAdmin;
    private ArrayList<UnitModel> listUnits;

    public UpdateDetailRequest(int userID, String apiToken, int product_id, int instock,
                               int min_stock, int instock_status, int offer_status,
                               String from_date, String end_date, int limit_number,
                               float offer_price, int is_groceryAdmin, ArrayList<UnitModel> listUnits) {
        super(PATH_URL);
        UserID = userID;
        ApiToken = apiToken;
        this.product_id = product_id;
        this.instock = instock;
        this.min_stock = min_stock;
        this.instock_status = instock_status;
        this.offer_status = offer_status;
        this.from_date = from_date;
        this.end_date = end_date;
        this.limit_number = limit_number;
        this.offer_price = offer_price;
        this.is_groceryAdmin = is_groceryAdmin;
        this.listUnits = listUnits;
    }
}
