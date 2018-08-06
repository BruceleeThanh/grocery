package com.grocery.response;

import com.grocery.model.ItemBuilding;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by ThanhBeo on 15/10/2017.
 */

public class GetBuildingResponse extends BaseResponse implements Serializable{
    public ArrayList<ItemBuilding> result;
}
