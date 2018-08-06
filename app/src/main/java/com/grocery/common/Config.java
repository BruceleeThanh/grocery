package com.grocery.common;

import com.grocery.model.ItemCategory;
import com.grocery.model.ItemOrders;

import java.util.ArrayList;

public class Config {
    public static final String Pref = "Pref";
    public static final String KEY_TOKEN = "Token";
    public static final String KEY_ID = "user_id";
    public static final String KEY_EMAIL = "Email";
    public static final String KEY_ADMIN_PROFILE = "admin_profile";

    public static final String KEY_CART = "cart_detail";
    public static final String KEY_REORDER = "re_order";
    public static final String KEY_ORDER_DETAIL = "order_detail";
    public static final String KEY_LIST_PRODUCT = "list_product";
    public static final String KEY_LIST_PROVIDER = "list_provider";
    public static final String KEY_SPLASH = "key_splash";

    public static final int PAGE_SIZE = 20;
    public static final int PAGE_SIZE_10 = 10;
    public static final String KEY_VIEW_ORDER = "key view order";
    public static boolean checkUpdate = false;
    public static int number_update = 0;

    public static int is_groceryAdmin = 1;
    public static int AdminId = 0;
    public static int city_id = 0;
    public static String apiToken = "";

    public static String KEY_FRAGMENT_MAIN = "";
    public static boolean checkImMenu = true;
    public static ArrayList<ItemCategory> arrItemCategory = new ArrayList<>();
    public static boolean isUpdateOrder = false;
    public static ItemOrders ItemOrdersView = new ItemOrders();
    public static boolean showBulkOrder = false;
    public static boolean showScheduleOrder = false;
    public static int statusTypeOrder = 0;
    public static final String ACTION_RELOAD_DATA = Config.class.getPackage() + ".action.ACTION_RELOAD_DATA";
    public static final int ACCEPT_ORDER = 1;
    public static int FILTER = 0;

    public static String txtSearchId = "";
    public static String txtSearchFlat = "";
    public static int scrollItem = 140;
    public static boolean isCallFromNotifi = false;
}
