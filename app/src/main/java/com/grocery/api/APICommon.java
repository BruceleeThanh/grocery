package com.grocery.api;

import android.util.Log;

import com.grocery.common.FunctionCommon;
import com.grocery.request.AddBuildingAreaRequest;
import com.grocery.request.AddDeliveryBoyRequest;
import com.grocery.request.BulkOrderSettingRequest;
import com.grocery.request.CreatComboRequest;
import com.grocery.request.DeleteBuildingRequest;
import com.grocery.request.DeleteComboOfferRequest;
import com.grocery.request.DeleteDeliveryBoyRequest;
import com.grocery.request.LoginRequest;
import com.grocery.request.SendOrderConfirmRequest;
import com.grocery.request.SendReplacementRequest;
import com.grocery.request.UpdateDeliveryBoyStatusRequest;
import com.grocery.request.UpdateDeliveryTimeRequest;
import com.grocery.request.UpdateDespatchOrderRequest;
import com.grocery.request.UpdateInStockStatusRequest;
import com.grocery.request.UpdateNormalOrderRequets;
import com.grocery.request.SuggestProductsRequest;
import com.grocery.request.UpdateBrandRequest;
import com.grocery.request.UpdateCategoryRequest;
import com.grocery.request.UpdateDetailRequest;
import com.grocery.request.UpdateManagerProductsRequest;
import com.grocery.request.UpdatePreparingOrderRequest;
import com.grocery.request.UpdateSubCategoryRequest;
import com.grocery.response.AlternativeResponse;
import com.grocery.response.BaseResponse;
import com.grocery.response.BrandResponse;
import com.grocery.response.BulkOrderSettingResponse;
import com.grocery.response.ComboOfferResponse;
import com.grocery.response.DeliveryBoyResponse;
import com.grocery.response.DeliveryTimeResponse;
import com.grocery.response.GetAreaResponse;
import com.grocery.response.GetBuildingResponse;
import com.grocery.response.GetCategoryResponse;
import com.grocery.response.GetUserResponse;
import com.grocery.response.InforOrderResponse;
import com.grocery.response.LoginResponse;
import com.grocery.response.ManagerProductsToUpdatResqonse;
import com.grocery.response.MangerProductsToAddResponse;
import com.grocery.response.OrderDetailResponse;
import com.grocery.response.OrdersResponse;
import com.grocery.response.ProductsDetailResponse;
import com.grocery.response.SubCategoryResponse;
import com.grocery.response.SuggestionProductsResponse;
import com.grocery.response.UserOrderResponse;
import com.grocery.response.UserPurchaseReponse;

import retrofit.Call;
import retrofit.http.Body;
import retrofit.http.GET;
import retrofit.http.POST;
import retrofit.http.Query;

public class APICommon {
    public static final String BASE_URL = "http://159.65.14.89/grocery/api/";
    public static final int ANDROID = 4;
    public static final int IOS = 3;
    public static final int WEB = 2;
    private static final String TAG = APICommon.class.getSimpleName();

    public static String getApiKey() {
        return "";
    }

    public static String getAppId() {
        return "com.grocery";
    }

    public static int getDeviceType() {
        return ANDROID;
    }

    public static String getSign(String api_key, String path_url) {
        try {
            long time = System.currentTimeMillis();
            StringBuilder builder = new StringBuilder();
            builder.append(time);
            builder.append(".");
            String md5_hash = FunctionCommon.hashString(path_url + ":" + time + ":" + api_key);
            builder.append(md5_hash);
            return builder.toString();
        } catch (Exception ex) {
            Log.e(TAG, ex.getMessage());
            return null;
        }
    }


    public interface Grocery {
        @POST("grocery/login")
        Call<LoginResponse> adminLogin(@Body LoginRequest loginRequest);

        @GET("grocery/getAllCategory")
        Call<GetCategoryResponse> getAllCategory(@Query("UserID") int UserID, @Query("ApiToken") String ApiToken,
                                                 @Query("grocery_id") int grocery_id, @Query("is_groceryAdmin") int is_groceryAdmin);

        @GET("grocery/getAllSubCategory")
        Call<SubCategoryResponse> getSubCategory(@Query("UserID") int UserID, @Query("ApiToken") String ApiToken,
                                                 @Query("grocery_id") int grocery_id, @Query("category_id") int category_id,
                                                 @Query("page_size") int page_size, @Query("page_number") int page_number,
                                                 @Query("sort_type") int sort_type, @Query("is_groceryAdmin") int is_groceryAdmin);

        @GET("grocery/getAllBrand")
        Call<BrandResponse> getAllBrand(@Query("UserID") int UserID, @Query("ApiToken") String ApiToken,
                                        @Query("grocery_id") int grocery_id, @Query("sort_type") int sort_type,
                                        @Query("is_groceryAdmin") int is_groceryAdmin);

        @POST("grocery/updateBrand")
        Call<BaseResponse> updateBrand(@Body UpdateBrandRequest updateBrandRequest);

        @POST("grocery/updateSubCategory")
        Call<BaseResponse> updateSubCategory(@Body UpdateSubCategoryRequest updateSubCategoryRequest);

        @POST("grocery/updateCategory")
        Call<BaseResponse> updateCategory(@Body UpdateCategoryRequest updateCategoryRequest);

        @GET("grocery/getAllProductToAddAndRemove")
        Call<MangerProductsToAddResponse> getListManageProductsToAdd(@Query("UserID") int UserID, @Query("ApiToken") String ApiToken,
                                                                     @Query("category_id") int category_id, @Query("sub_category_id") int sub_category_id,
                                                                     @Query("brand_id") int brand_id, @Query("text_search") String text_search, @Query("page_size") int page_size, @Query("page_number") int page_number, @Query("is_groceryAdmin") int is_groceryAdmin);

        @GET("grocery/getAllProduct")
        Call<ManagerProductsToUpdatResqonse> getListManageProductsToUpdate(@Query("UserID") int UserID, @Query("ApiToken") String ApiToken,
                                                                           @Query("category_id") int category_id, @Query("sub_category_id") int sub_category_id,
                                                                           @Query("brand_id") int brand_id, @Query("text_search") String text_search, @Query("page_size") int page_size, @Query("page_number") int page_number, @Query("is_groceryAdmin") int is_groceryAdmin);

        @POST("grocery/updateProduct")
        Call<BaseResponse> updateManageProducts(@Body UpdateManagerProductsRequest updateManagerProductsRequest);

        @GET("grocery/getProductDetails")
        Call<ProductsDetailResponse> getproductsDetails(@Query("UserID") int UserID, @Query("ApiToken") String ApiToken,
                                                        @Query("product_id") int product_id, @Query("is_groceryAdmin") int is_groceryAdmin);

        @GET("grocery/getAllSuggestionProduct")
        Call<SuggestionProductsResponse> getSuggestProducts(@Query("UserID") int UserID, @Query("ApiToken") String ApiToken,
                                                            @Query("page_size") int page_size, @Query("page_number") int page_number,
                                                            @Query("is_groceryAdmin") int is_groceryAdmin);

        @POST("grocery/suggestProduct")
        Call<BaseResponse> suggestProducts(@Body SuggestProductsRequest suggestProductsRequest);

        @POST("grocery/updateProductDetails")
        Call<BaseResponse> updateDetails(@Body UpdateDetailRequest updateDetailRequest);

        @GET("grocery/getAllComboOffer")
        Call<ComboOfferResponse> getListComboOffer(@Query("UserID") int UserID, @Query("ApiToken") String ApiToken,
                                                   @Query("text_search") String text_search, @Query("page_size") int page_size,
                                                   @Query("page_number") int page_number, @Query("is_groceryAdmin") int is_groceryAdmin);

        @POST("grocery/createComboOffer")
        Call<BaseResponse> creatComboOffer(@Body CreatComboRequest creatComboRequest);

        @POST("grocery/deleteComboOffer")
        Call<BaseResponse> deleteComboOffer(@Body DeleteComboOfferRequest deleteComboOfferRequest);

        @GET("groceryUser/getAllDeliveryBoy")
        Call<DeliveryBoyResponse> getDeliveryBoy(@Query("UserID") int UserID, @Query("ApiToken") String ApiToken,
                                                 @Query("is_groceryAdmin") int is_groceryAdmin);

        @POST("groceryUser/addDeliveryBoy")
        Call<BaseResponse> addDeliveryBoy(@Body AddDeliveryBoyRequest addDeliveryBoyRequest);

        @POST("groceryUser/deleteDeliveryBoy")
        Call<BaseResponse> deleteDeiveryBoy(@Body DeleteDeliveryBoyRequest deleteDeliveryBoyRequest);

        @GET("grocery/getAllOrder")
        Call<OrdersResponse> getAllOders(@Query("UserID") int UserID, @Query("ApiToken") String ApiToken,
                                         @Query("order_type") int order_type, @Query("filter_type") int filter_type,
                                         @Query("flat_search") String flat_search,@Query("order_id_search") String order_id_search, @Query("page_size") int page_size,
                                         @Query("page_number") int page_number, @Query("is_groceryAdmin") int is_groceryAdmin);

        @GET("grocery/getOrderDetails")
        Call<OrderDetailResponse> getOrderDetail(@Query("UserID") int UserID, @Query("ApiToken") String ApiToken,
                                                 @Query("order_id") int order_id, @Query("order_type") int order_type, @Query("is_groceryAdmin") int is_groceryAdmin);

        @POST("grocery/updateNormalOrder")
        Call<BaseResponse> updateNormalOrder(@Body UpdateNormalOrderRequets updateNormalOrderRequets);

        @GET("grocery/getAlternativeProduct")
        Call<AlternativeResponse> getAlternavite(@Query("UserID") int UserID, @Query("ApiToken") String ApiToken, @Query("product_id") int product_id,
                                                 @Query("brand_id") int brand_id, @Query("page_size") int page_size, @Query("page_number") int page_number,
                                                 @Query("is_groceryAdmin") int is_groceryAdmin, @Query("text_search") String text_search);

        @POST("grocery/sendReplacementItems")
        Call<BaseResponse> sendReplacementAlternavite(@Body SendReplacementRequest sendReplacementRequest);

        @POST("grocery/updatePreparingOrder")
        Call<BaseResponse> updatePreparingOrder(@Body UpdatePreparingOrderRequest updatePreparingOrderRequest);

        @POST("grocery/updateDespatchedOrder")
        Call<BaseResponse> updateDespatchOrder(@Body UpdateDespatchOrderRequest updateDespatchOrderRequest);

        @GET("grocery/getAllOrderInfor")
        Call<InforOrderResponse> getInforOrder(@Query("UserID") int UserID, @Query("ApiToken") String ApiToken, @Query("is_groceryAdmin") int is_groceryAdmin);

        @POST("grocery/sendOrderConfirmation")
        Call<BaseResponse> sendOrderConfirm(@Body SendOrderConfirmRequest sendOrderConfirmRequest);

        @GET("groceryUser/getAllBuilding")
        Call<GetBuildingResponse> getBuildingArea(@Query("UserID") int UserID, @Query("ApiToken") String ApiToken, @Query("is_groceryAdmin") int is_groceryAdmin);

        @POST("groceryUser/deleteBuilding")
        Call<BaseResponse> deleteBuildingArea(@Body DeleteBuildingRequest deleteBuildingRequest);

        @GET("location/getAreaAndBuilding")
        Call<GetAreaResponse> getArea(@Query("city_id") int city_id);

        @POST("groceryUser/addBuilding")
        Call<BaseResponse> addbuildingArea(@Body AddBuildingAreaRequest addBuildingAreaRequest);

        @GET("groceryUser/getDeliveryTiming")
        Call<DeliveryTimeResponse> getDeliveryTime(@Query("UserID") int UserID, @Query("ApiToken") String ApiToken, @Query("is_groceryAdmin") int is_groceryAdmin);

        @POST("groceryUser/updateDeliveryTiming")
        Call<BaseResponse> updateDelieveryTime(@Body UpdateDeliveryTimeRequest updateDeliveryTimeRequest);

        @POST("grocery/updateInstockStatus")
        Call<BaseResponse> updateInStockStatus(@Body UpdateInStockStatusRequest updateInStockStatusRequest);

        @GET("grocery/searchOrders")
        Call<OrdersResponse> getSearchOrder(@Query("UserID") int UserID, @Query("ApiToken") String ApiToken, @Query("order_id") int order_id,
                                            @Query("flat_search") String flat_search, @Query("page_size") int page_size, @Query("page_number") int page_number,
                                            @Query("is_groceryAdmin") int is_groceryAdmin);

        @GET("groceryUser/getAllUsers")
        Call<GetUserResponse> getAllUser(@Query("UserID") int UserID, @Query("ApiToken") String ApiToken, @Query("customer_name") String customer_name,
                                         @Query("flat_number") String flat_number, @Query("building_name") String building_name,
                                         @Query("page_size") int page_size, @Query("page_number") int page_number, @Query("is_groceryAdmin") int is_groceryAdmin);

        @GET("groceryUser/getOrderHistory")
        Call<UserOrderResponse> getOrderHistory(@Query("UserID") int UserID, @Query("ApiToken") String ApiToken, @Query("user_id") int user_id,
                                                @Query("start_date") String start_date, @Query("end_date") String end_date,
                                                @Query("page_size") int page_size, @Query("page_number") int page_number, @Query("is_groceryAdmin") int is_groceryAdmin);

        @GET("groceryUser/getUserPurchaseAnalytics")
        Call<UserPurchaseReponse> getUserPurchase(@Query("UserID") int UserID, @Query("ApiToken") String ApiToken,
                                                  @Query("user_id") int user_id, @Query("is_groceryAdmin") int is_groceryAdmin);

        @GET("grocery/getBulkOrderSetting")
        Call<BulkOrderSettingResponse> getBulkOrderSetting(@Query("UserID") int UserID,
                                                           @Query("ApiToken") String ApiToken,
                                                           @Query("is_groceryAdmin") int is_groceryAdmin);

        @POST("grocery/updateBulkOrderSetting")
        Call<BaseResponse> updateBulkOrderSetting(@Body BulkOrderSettingRequest bulkOrderSettingRequest);

        @POST("groceryUser/updateDeliveryBoyStatus")
        Call<BaseResponse> updateDeliveryBoyStatus(@Body UpdateDeliveryBoyStatusRequest updateDeliveryBoyStatusRequest);
    }
}
