package com.grocery.response;

import com.grocery.model.ItemComboOffer;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by ThanhBeo on 26/09/2017.
 */

public class ComboOfferResponse extends BaseResponse implements Serializable {
    public ArrayList<ItemComboOffer> result;
}
