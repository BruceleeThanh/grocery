package com.grocery.response;

import com.grocery.model.ProductsDetail;

import java.io.Serializable;

/**
 * Created by ThanhBeo on 20/09/2017.
 */

public class ProductsDetailResponse extends BaseResponse implements Serializable {
    public ProductsDetail result;
}
