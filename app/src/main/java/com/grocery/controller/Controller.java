package com.grocery.controller;

import android.os.StrictMode;

import com.grocery.api.APICommon;
import com.grocery.api.ServiceGenerator;
import com.grocery.request.CreatComboRequest;
import com.grocery.request.DeleteComboOfferRequest;
import com.grocery.request.LoginRequest;
import com.grocery.request.SendOrderConfirmRequest;
import com.grocery.request.SendReplacementRequest;
import com.grocery.request.SuggestProductsRequest;
import com.grocery.request.UpdateBrandRequest;
import com.grocery.request.UpdateCategoryRequest;
import com.grocery.request.UpdateDetailRequest;
import com.grocery.request.UpdateInStockStatusRequest;
import com.grocery.request.UpdateManagerProductsRequest;
import com.grocery.request.UpdateNormalOrderRequets;
import com.grocery.request.UpdateSubCategoryRequest;
import com.grocery.response.AlternativeResponse;
import com.grocery.response.BaseResponse;
import com.grocery.response.BrandResponse;
import com.grocery.response.ComboOfferResponse;
import com.grocery.response.GetCategoryResponse;
import com.grocery.response.LoginResponse;
import com.grocery.response.ManagerProductsToUpdatResqonse;
import com.grocery.response.MangerProductsToAddResponse;
import com.grocery.response.ProductsDetailResponse;
import com.grocery.response.SubCategoryResponse;
import com.grocery.response.SuggestionProductsResponse;

import retrofit.Call;

/**
 * Created by ThanhBeo on 16/09/2017.
 */

public class Controller {
    public APICommon.Grocery service;

    public Controller() {
        service = ServiceGenerator.GetInstance();
    }
}
