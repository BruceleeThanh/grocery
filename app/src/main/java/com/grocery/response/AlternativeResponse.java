package com.grocery.response;

import com.grocery.model.ItemAlternavite;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by ThanhBeo on 04/10/2017.
 */

public class AlternativeResponse extends BaseResponse implements Serializable {
    public ArrayList<ItemAlternavite> result;
}
