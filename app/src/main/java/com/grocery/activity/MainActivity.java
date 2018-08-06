package com.grocery.activity;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.chauthai.swipereveallayout.SwipeRevealLayout;
import com.google.gson.Gson;
import com.grocery.R;
import com.grocery.adapter.Menu1Adapter;
import com.grocery.common.CRecyclerView;
import com.grocery.common.Config;
import com.grocery.common.CustomEditText;
import com.grocery.common.CustomTextView;
import com.grocery.common.RecyclerItemClickListener;
import com.grocery.common.ServiceUtils;
import com.grocery.common.SwitchView;
import com.grocery.controller.Controller;
import com.grocery.dialog.DialogCloseShop;
import com.grocery.dialog.DialogNotifiOrder;
import com.grocery.dialog.DialogPingShop;
import com.grocery.fragment.fragmentMainScreen.FragmentAllProducts;
import com.grocery.fragment.fragmentMainScreen.FragmentAllUser;
import com.grocery.fragment.fragmentMainScreen.FragmentLiveCustomer;
import com.grocery.fragment.fragmentMainScreen.FragmentManagerOffers;
import com.grocery.fragment.fragmentMainScreen.FragmentMissedSales;
import com.grocery.fragment.fragmentMainScreen.FragmentMyStock;
import com.grocery.fragment.fragmentMainScreen.FragmentOrders;
import com.grocery.fragment.fragmentMainScreen.FragmentPromotions;
import com.grocery.fragment.fragmentMainScreen.FragmentRewardPoint;
import com.grocery.fragment.fragmentMainScreen.FragmentSearch;
import com.grocery.fragment.fragmentMainScreen.FragmentStockNotifi;
import com.grocery.fragment.fragmentOrders.FragmentNewOrders;
import com.grocery.model.AdminModel;
import com.grocery.model.InforOrdersModel;
import com.grocery.model.ItemMenu;
import com.grocery.response.InforOrderResponse;
import com.grocery.utils.FragmentUtils;
import com.grocery.utils.HideSoftKeyBroad;
import com.grocery.utils.ProgerssBarUntil;
import com.grocery.utils.StringUtil;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private SwitchView aSwitch;
    private CustomTextView tvTime;
    private ImageView imMenu;
    private LinearLayout layoutHome;
    private LinearLayout layoutMapView;
    private CustomTextView tvMoneyToday, tvMoneyWeek, tvMoneyMonth, tvMoneyYear, tvTodayOrder;
    public static CustomTextView tvMainName;
    private CustomTextView tvYourManager;
    private LinearLayout layoutAllUser;
    private LinearLayout layoutLiveCustomer;
    private CRecyclerView lvcategory;
    public ArrayList<ItemMenu> arrItemMenu;
    private Menu1Adapter menu1Adapter;
    private ImageView imShow;
    private LinearLayout layoutShow;
    private ImageView imHide;
    private LinearLayout layoutClickShow;
    public static MyTask myTask;
    private LinearLayout layoutSearch;
    private SwipeRevealLayout swipeRevealLayout;
    private ImageView imhideLayoutSearch;
    private LinearLayout layoutGone;
    private boolean doubleBackToExitPressedOnce = false;
    private LinearLayout layoutFlatNumber;
    private LinearLayout layoutOderId;
    private CustomTextView tvFlatNumber;
    private CustomTextView tvOrderId;
    private LinearLayout rdFlatNumber;
    private LinearLayout rdOrderId;
    private FragmentUtils fragmentUtils;
    private SharedPreferences sharedPreferences;
    private AdminModel adminModel;
    private ProgerssBarUntil progerssBarUntil;
    private SwipeRefreshLayout swipeRefreshLayout;
    private static boolean isFirtLoadMenu = true;
    private static int timeReloadMenu = 0;
    private static boolean checkReloadMenu = false;
    private CustomTextView tvAllUserNumber;
    private CustomTextView tvNumberBulkOrder;
    private CustomTextView tvCustomerNumber;
    private InforOrdersModel inforOrdersModel;
    private LinearLayout btnSearch2;
    private CustomEditText edtSearch;
    public static Activity instanceMain;
    public static DialogNotifiOrder dialogNotifi;
    public static DialogPingShop dialogPingShop;

    private static OnClickListener onClickListener;

    public interface OnClickListener {
        void onItemClickedSearch();
    }

    @Override
    protected void onResume() {
        super.onResume();
        instanceMain = this;
        if (Config.isCallFromNotifi) {
            Intent intent = new Intent(this, MainViewOrders.class);
            intent.putExtra(Config.KEY_VIEW_ORDER, Config.ItemOrdersView);
            startActivity(intent);
            Config.isCallFromNotifi = false;
        }
    }

    public static void setOnClickListener(OnClickListener listener) {
        onClickListener = listener;
    }

    public InforOrdersModel getInforOrdersModel() {
        return inforOrdersModel;
    }

    public void setInforOrdersModel(InforOrdersModel inforOrdersModel) {
        this.inforOrdersModel = inforOrdersModel;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.main);
        progerssBarUntil = new ProgerssBarUntil(getWindow().getDecorView());
        initView();
        binData();
        initListener();
    }

    private void binData() {
        if (myTask != null) {
            myTask.cancel(true);
        }
        myTask = new MyTask();
        if (Build.VERSION.SDK_INT >= 11) {
            myTask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
        } else {
            myTask.execute();
        }
        swipeRevealLayout.setLockDrag(true);
        lvcategory.setNestedScrollingEnabled(false);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 4,
                GridLayoutManager.VERTICAL, false);
        lvcategory.setLayoutManager(gridLayoutManager);
        arrItemMenu = new ArrayList<>();
        menu1Adapter = new Menu1Adapter(this, arrItemMenu);
        lvcategory.setAdapter(menu1Adapter);
        loadMenu();
    }

    private void initListener() {
        aSwitch.setOnCheckedChangeListener(new SwitchView.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(SwitchView switchView, boolean isChecked) {
                if (!isChecked) {
                    DialogCloseShop dialogCloseShop = new DialogCloseShop(MainActivity.this,
                            android.R.style.Theme_Light_NoTitleBar_Fullscreen);
                    dialogCloseShop.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                    dialogCloseShop.show();
                    dialogCloseShop.setOnCancelListener(new DialogInterface.OnCancelListener() {
                        @Override
                        public void onCancel(DialogInterface dialog) {
                            aSwitch.setChecked(true);
                        }
                    });
                }
            }
        });
        imMenu.setOnClickListener(this);
        tvYourManager.setOnClickListener(this);
        layoutLiveCustomer.setOnClickListener(this);
        layoutAllUser.setOnClickListener(this);

        lvcategory.addOnItemTouchListener(new RecyclerItemClickListener(this, new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                if (arrItemMenu.get(position).getName().equals(getString(R.string.orders))) {
                    Config.KEY_FRAGMENT_MAIN = arrItemMenu.get(position).getName();
                    statusTitleSearch(1);
                    arrItemMenu.get(position).checkBG = false;
                    replaceFragment(true, new FragmentOrders());
                } else if (arrItemMenu.get(position).getName().equals(getString(R.string.preparing_orders))) {
                    Config.KEY_FRAGMENT_MAIN = arrItemMenu.get(position).getName();
                    statusTitleSearch(1);
                    replaceFragment(true, new FragmentOrders());
                } else if (arrItemMenu.get(position).getName().equals(getString(R.string.despatched_orders))) {
                    Config.KEY_FRAGMENT_MAIN = arrItemMenu.get(position).getName();
                    statusTitleSearch(1);
                    replaceFragment(true, new FragmentOrders());
                } else if (arrItemMenu.get(position).getName().equals(getString(R.string.Canceled_Order))) {
                    Config.KEY_FRAGMENT_MAIN = arrItemMenu.get(position).getName();
                    setStatusImageMenu(false);
                    statusTitleSearch(1);
                    replaceFragment(true, new FragmentOrders());
                } else if (arrItemMenu.get(position).getName().equals(getString(R.string.All_products))) {
                    replaceFragment(false, new FragmentAllProducts());
                } else if (arrItemMenu.get(position).getName().equals(getString(R.string.my_stock))) {
                    replaceFragment(false, new FragmentMyStock());
                } else if (arrItemMenu.get(position).getName().equals(getString(R.string.manage_offers))) {
                    replaceFragment(false, new FragmentManagerOffers());
                } else if (arrItemMenu.get(position).getName().equals(getString(R.string.stock_notifictaions))) {
                    replaceFragment(false, new FragmentStockNotifi());
                } else if (arrItemMenu.get(position).getName().equals(getString(R.string.manage_reward_point))) {
                    replaceFragment(false, new FragmentRewardPoint());
                } else if (arrItemMenu.get(position).getName().equals(getString(R.string.search))) {
                    setStatusImageMenu(false);
                    replaceFragment(false, new FragmentSearch());
                } else if (arrItemMenu.get(position).getName().equals(getString(R.string.missed_sales))) {
                    setStatusImageMenu(false);
                    replaceFragment(true, new FragmentMissedSales());
                } else if (arrItemMenu.get(position).getName().equals(getString(R.string.promotions))) {
                    replaceFragment(false, new FragmentPromotions());
                }
                tvMainName.setText(arrItemMenu.get(position).getName().toString());
            }
        }));
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                isFirtLoadMenu = true;
                swipeRefreshLayout.setRefreshing(false);
                loadMenu();
            }
        });
        imHide.setOnClickListener(this);
        imShow.setOnClickListener(this);
        layoutSearch.setOnClickListener(this);
        imhideLayoutSearch.setOnClickListener(this);
        layoutFlatNumber.setOnClickListener(this);
        layoutOderId.setOnClickListener(this);
        btnSearch2.setOnClickListener(this);
        layoutHome.setOnClickListener(this);
    }

    private void initView() {
        sharedPreferences = getSharedPreferences(Config.Pref, MODE_PRIVATE);
        String profile = sharedPreferences.getString(Config.KEY_ADMIN_PROFILE, "");
        Gson gson = new Gson();
        adminModel = gson.fromJson(profile, AdminModel.class);
        Config.AdminId = adminModel.getId();
        Config.apiToken = adminModel.getApiToken();
        Config.city_id = adminModel.getCity_id();

        //Tao cac nut va switch
        aSwitch = (SwitchView) findViewById(R.id.switch_closeShop);
        tvMainName = (CustomTextView) findViewById(R.id.tv_main_name);
        tvTime = (CustomTextView) findViewById(R.id.tv_time);
        imMenu = (ImageView) findViewById(R.id.imMenu);

        tvYourManager = (CustomTextView) findViewById(R.id.tv_your_manager);

        layoutSearch = (LinearLayout) findViewById(R.id.layout_btn_search);
        swipeRevealLayout = (SwipeRevealLayout) findViewById(R.id.swipe_layout);
        imhideLayoutSearch = (ImageView) findViewById(R.id.im_hide_layout_search);
        layoutGone = (LinearLayout) findViewById(R.id.layout_gone);
        imHide = (ImageView) findViewById(R.id.im_hide);
        imShow = (ImageView) findViewById(R.id.im_show);
        layoutShow = (LinearLayout) findViewById(R.id.layout_main_2);
        layoutClickShow = (LinearLayout) findViewById(R.id.layout_click_show);
        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swiperefresh);

        // anh xa cac layout_search_main duoi cung
        layoutAllUser = (LinearLayout) findViewById(R.id.layout_all_users);
        layoutLiveCustomer = (LinearLayout) findViewById(R.id.layout_live_customer);
        tvAllUserNumber = (CustomTextView) findViewById(R.id.tvAllUserNumber);
        tvNumberBulkOrder = (CustomTextView) findViewById(R.id.tvNumberBulkOrder);
        tvCustomerNumber = (CustomTextView) findViewById(R.id.tvCustomerNumber);
        // Tao list category1
        lvcategory = (CRecyclerView) findViewById(R.id.lv_category);
        /*init layout_search_main search*/
        layoutFlatNumber = (LinearLayout) findViewById(R.id.layout_radio_flat_number_search);
        layoutOderId = (LinearLayout) findViewById(R.id.layout_radio_order_id_search);
        tvFlatNumber = (CustomTextView) findViewById(R.id.tv_flat_search);
        tvOrderId = (CustomTextView) findViewById(R.id.tv_order_id_search);
        rdFlatNumber = (LinearLayout) findViewById(R.id.rd_flat_search);
        rdOrderId = (LinearLayout) findViewById(R.id.rd_order_id_search);
        btnSearch2 = (LinearLayout) findViewById(R.id.btnSearch2);
        edtSearch = (CustomEditText) findViewById(R.id.edtSearch);
        layoutHome = findViewById(R.id.layout_home);
        layoutHome.setVisibility(View.GONE);
        tvMoneyToday = findViewById(R.id.tv_money_today);
        tvMoneyWeek = findViewById(R.id.tv_money_week);
        tvMoneyMonth = findViewById(R.id.tv_money_month);
        tvMoneyYear = findViewById(R.id.tv_money_year);
        tvTodayOrder = findViewById(R.id.tv_today_order);
        layoutMapView = findViewById(R.id.layout_map_view);
    }

    public static void initDialogNotifi(final int orderId, int orderType) {
        try {
            DialogNotifiOrder.orderId = orderId;
            DialogNotifiOrder.orderType = orderType;
            if (dialogNotifi != null && dialogNotifi.isShowing()) {
                dialogNotifi.cancel();
            }
            dialogNotifi = new DialogNotifiOrder(instanceMain, android.R.style.Theme_Light_NoTitleBar_Fullscreen);
            dialogNotifi.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            dialogNotifi.show();
        } catch (Exception err) {

        }
    }

    public static void initDialogPing(final int orderId, String flat, String delivery) {
        DialogPingShop.flatNo = flat;
        DialogPingShop.orderNo = orderId;
        DialogPingShop.deliveryTime = delivery;
        if (dialogPingShop != null && dialogPingShop.isShowing()) {
            dialogPingShop.cancel();
        }
        dialogPingShop = new DialogPingShop(instanceMain, android.R.style.Theme_Light_NoTitleBar_Fullscreen);
        dialogPingShop.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialogPingShop.show();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.imMenu:
                if (Config.checkImMenu) {
                    Intent intent = new Intent(getApplicationContext(), MainMenu.class);
                    startActivity(intent);
                } else {
                    onBackPressed();
                }
                break;
            case R.id.layout_all_users:
                tvMainName.setText(getResources().getString(R.string.all_users));
                replaceFragment(true, new FragmentAllUser());
                setStatusImageMenu(false);
                statusTitleSearch(3);
                break;
            case R.id.layout_live_customer:
                tvMainName.setText(getResources().getString(R.string.live_customer));
                tvYourManager.setVisibility(LinearLayout.VISIBLE);
                replaceFragment(false, new FragmentLiveCustomer());
                setStatusImageMenu(false);
                statusTitleSearch(3);
                break;
            case R.id.tv_your_manager:
                onBackPressed();
                setStatusImageMenu(true);
                tvMainName.setText(getResources().getString(R.string.manage_offers));
                replaceFragment(false, new FragmentManagerOffers());
                break;
            case R.id.im_hide:
                layoutShow.setVisibility(LinearLayout.GONE);
                layoutClickShow.setVisibility(LinearLayout.VISIBLE);
                break;
            case R.id.im_show:
                layoutClickShow.setVisibility(LinearLayout.GONE);
                layoutShow.setVisibility(LinearLayout.VISIBLE);
                break;
            case R.id.layout_btn_search:
                swipeRevealLayout.setLockDrag(false);
                swipeRevealLayout.open(true);
                swipeRevealLayout.setLockDrag(true);
                statusTitleSearch(2);
                break;
            case R.id.im_hide_layout_search:
                swipeRevealLayout.setLockDrag(false);
                swipeRevealLayout.close(true);
                statusTitleSearch(1);
                swipeRevealLayout.setLockDrag(true);
                cleanSearch();
                break;
            case R.id.layout_radio_flat_number_search:
                edtSearch.setEnabled(true);
                if (rdFlatNumber.isSelected()) {
                    edtSearch.setText("");
                    edtSearch.setEnabled(false);
                    rdFlatNumber.setSelected(false);
                    tvFlatNumber.setTextColor(getResources().getColor(R.color.text_search_main));
                    layoutFlatNumber.setBackgroundResource(R.drawable.render_search_2);
                    break;
                }
                edtSearch.setText("");
                rdFlatNumber.setSelected(true);
                tvFlatNumber.setTextColor(Color.WHITE);
                layoutFlatNumber.setBackgroundResource(R.drawable.render_search);
                rdOrderId.setSelected(false);
                tvOrderId.setTextColor(getResources().getColor(R.color.text_search_main));
                layoutOderId.setBackgroundResource(R.drawable.render_search_2);
                break;
            case R.id.layout_radio_order_id_search:
                edtSearch.setEnabled(true);
                if (rdOrderId.isSelected()) {
                    edtSearch.setText("");
                    edtSearch.setEnabled(false);
                    rdOrderId.setSelected(false);
                    tvOrderId.setTextColor(getResources().getColor(R.color.text_search_main));
                    layoutOderId.setBackgroundResource(R.drawable.render_search_2);
                    break;
                }
                edtSearch.setText("");
                rdOrderId.setSelected(true);
                tvOrderId.setTextColor(Color.WHITE);
                layoutOderId.setBackgroundResource(R.drawable.render_search);
                rdFlatNumber.setSelected(false);
                tvFlatNumber.setTextColor(getResources().getColor(R.color.text_search_main));
                layoutFlatNumber.setBackgroundResource(R.drawable.render_search_2);
                break;
            case R.id.btnSearch2:
                HideSoftKeyBroad.hideSoftKeyboard(this, getCurrentFocus());
                if (!rdOrderId.isSelected() && !rdFlatNumber.isSelected()) {
                    Config.txtSearchId = "";
                    Config.txtSearchFlat = "";
                    onClickListener.onItemClickedSearch();
                } else {
                    if (edtSearch.getText().toString().isEmpty()) {
                        Toast.makeText(this, getResources().getString(R.string.enter_search), Toast.LENGTH_LONG).show();
                        break;
                    }
                    if (rdOrderId.isSelected()) {
                        Config.txtSearchId = edtSearch.getText().toString();
                        Config.txtSearchFlat = "";
                        onClickListener.onItemClickedSearch();
                    } else if (rdFlatNumber.isSelected()) {
                        Config.txtSearchId = "";
                        Config.txtSearchFlat = edtSearch.getText().toString();
                        onClickListener.onItemClickedSearch();
                    }
                }
                break;
            case R.id.layout_home:
                onBackPressed();
                showLayoutHome(false);
                showLayoutMapView(false);
                statusTitleSearch(3);
                break;
        }
    }

    public void cleanSearch() {
        Config.txtSearchId = "";
        Config.txtSearchFlat = "";
        edtSearch.setText("");
        rdOrderId.setSelected(false);
        tvOrderId.setTextColor(getResources().getColor(R.color.text_search_main));
        layoutOderId.setBackgroundResource(R.drawable.render_search_2);
        rdFlatNumber.setSelected(false);
        tvFlatNumber.setTextColor(getResources().getColor(R.color.text_search_main));
        layoutFlatNumber.setBackgroundResource(R.drawable.render_search_2);
        edtSearch.setEnabled(false);
        swipeRevealLayout.setLockDrag(false);
        swipeRevealLayout.close(true);
        statusTitleSearch(1);
        swipeRevealLayout.setLockDrag(true);
        onClickListener.onItemClickedSearch();
    }

    public void statusTitleSearch(int check) {
        if (check == 1) {
            layoutSearch.setVisibility(LinearLayout.VISIBLE);
            layoutGone.setVisibility(LinearLayout.GONE);
            swipeRevealLayout.close(true);
        } else if (check == 2) {
            layoutSearch.setVisibility(LinearLayout.GONE);
            layoutGone.setVisibility(LinearLayout.VISIBLE);
            swipeRevealLayout.open(true);
        } else {
            layoutSearch.setVisibility(LinearLayout.GONE);
            layoutGone.setVisibility(LinearLayout.GONE);
            swipeRevealLayout.close(true);
        }
    }

    public void setStatusImageMenu(boolean status) {
        if (status) {
            imMenu.setImageResource(R.mipmap.icon_menu_1);
        } else {
            imMenu.setImageResource(R.mipmap.icon_back);
        }
        Config.checkImMenu = status;
    }

    @Override
    public void onBackPressed() {
        if (tvMainName.getText().equals(getResources().getString(R.string.dashbroad))) {
            if (doubleBackToExitPressedOnce) {
                finish();
                return;
            }
            this.doubleBackToExitPressedOnce = true;
            Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show();

            new Handler().postDelayed(new Runnable() {

                @Override
                public void run() {
                    doubleBackToExitPressedOnce = false;
                }
            }, 2000);
            return;
        }
        setStatusImageMenu(true);
        tvYourManager.setVisibility(LinearLayout.GONE);
        statusTitleSearch(2);
        layoutGone.setVisibility(LinearLayout.GONE);
        showLayoutMapView(false);
        showLayoutHome(false);
        swipeRevealLayout.close(true);
        tvMainName.setText(getResources().getString(R.string.dashbroad));
        fragmentUtils.hideFragment(fragmentUtils.getCurrentFragment());
        loadMenu();
    }

    public void loadMenu() {
        if (!FragmentNewOrders.isProgessingLoadMore) {
            ServiceUtils.sendLocalBroadcast(getApplicationContext(), Config.ACTION_RELOAD_DATA);
        }
        getInforOreder(this, Config.AdminId, Config.apiToken, Config.is_groceryAdmin);
    }


    // update timer.
    public class MyTask extends AsyncTask<Void, Integer, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(Void... params) {
            int i = 1;
            synchronized (this) {
                while (i > 0) {
                    publishProgress(i);
                    SystemClock.sleep(1000);
                    Log.e("TAG", i + "");
                    i++;
                    if (isCancelled()) break;
                }
            }
            return null;
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            SimpleDateFormat format = new SimpleDateFormat("hh:mm aaa");
            format.setTimeZone(Calendar.getInstance().getTimeZone());
            String time = format.format(Calendar.getInstance().getTime());
            tvTime.setText(time);
            if (checkReloadMenu) {
                timeReloadMenu = values[0];
                checkReloadMenu = false;
            }
            if (values[0] - timeReloadMenu == 60) {
                loadMenu();
                checkReloadMenu = true;
                try {

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            try {
                MainMenu.tvTime.setText(time);
            } catch (Exception e) {
                // e.printStackTrace();
            }
            try {
                MainViewUser.tvTime.setText(time);
            } catch (Exception e) {
                // e.printStackTrace();
            }
            try {
                MainViewOrders.tvTime.setText(time);
            } catch (Exception e) {
                // e.printStackTrace();
            }
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            //tvTime.setText(result);
        }
    }

    private void getInforOreder(final Context context, int userID, String apiToken, int is_groceryAdmin) {
        if (isFirtLoadMenu) {
            progerssBarUntil.setInvisibeLayoutMain();
        }

        Controller controller = new Controller();
        Call<InforOrderResponse> call = controller.service.getInforOrder(userID, apiToken, is_groceryAdmin);
        call.enqueue(new Callback<InforOrderResponse>() {
            @Override
            public void onResponse(Response<InforOrderResponse> response, Retrofit retrofit) {
                progerssBarUntil.setVisibeLayoutMain();
                isFirtLoadMenu = false;
                if (response != null) {
                    InforOrderResponse inforOrderResponse = response.body();
                    if (inforOrderResponse != null) {
                        if (inforOrderResponse.code == 200) {
                            setInforOrdersModel(inforOrderResponse.result);
                            if (!StringUtil.isEmpty(inforOrderResponse.result.getMoney_today())) {
                                tvMoneyToday.setText(getString(R.string.aed) + " " + inforOrderResponse.result.getMoney_today());
                            } else {
                                tvMoneyToday.setText(getString(R.string.aed) + " 0");
                            }

                            if (!StringUtil.isEmpty(inforOrderResponse.result.getMoney_week())) {
                                tvMoneyWeek.setText(getString(R.string.aed) + " " + inforOrderResponse.result.getMoney_week());
                            } else {
                                tvMoneyWeek.setText(getString(R.string.aed) + " 0");
                            }

                            if (!StringUtil.isEmpty(inforOrderResponse.result.getMoney_month())) {
                                tvMoneyMonth.setText(getString(R.string.aed) + " " + inforOrderResponse.result.getMoney_month());
                            } else {
                                tvMoneyMonth.setText(getString(R.string.aed) + " 0");
                            }

                            if (!StringUtil.isEmpty(inforOrderResponse.result.getMoney_year())) {
                                tvMoneyYear.setText(getString(R.string.aed) + " " + inforOrderResponse.result.getMoney_year());
                            } else {
                                tvMoneyYear.setText(getString(R.string.aed) + " 0");
                            }

                            tvAllUserNumber.setText(inforOrderResponse.result.getAll_user() + "");
                            tvNumberBulkOrder.setText(inforOrderResponse.result.getBulk_order() + "");
                            tvCustomerNumber.setText(inforOrderResponse.result.getLive_user() + "");
                            tvTodayOrder.setText(inforOrderResponse.result.getToday_order() + "");
                            arrItemMenu = new ArrayList<>();
                            arrItemMenu.add(new ItemMenu(inforOrderResponse.result.getNew_order(), getString(R.string.orders), -1, R.drawable.bg_newoders));
                            arrItemMenu.add(new ItemMenu(inforOrderResponse.result.getPreparing_order(), getString(R.string.preparing_orders), -1, R.drawable.bg_preparing_oreders));
                            arrItemMenu.add(new ItemMenu(inforOrderResponse.result.getDespatched_order(), getString(R.string.despatched_orders), -1, R.drawable.bg_despatched_orders));
                            arrItemMenu.add(new ItemMenu(inforOrderResponse.result.getCancellled_order(), getString(R.string.Canceled_Order), -1, R.drawable.bg_canceled_orders));
                            arrItemMenu.add(new ItemMenu(-1, getString(R.string.All_products), -1, R.drawable.bg_produce));
                            arrItemMenu.add(new ItemMenu(-1, getString(R.string.my_stock), -1, R.drawable.bg_my_stock));
                            arrItemMenu.add(new ItemMenu(-1, getString(R.string.manage_offers), -1, R.drawable.bg_maganer_offers));
                            arrItemMenu.add(new ItemMenu(-1, getString(R.string.stock_notifictaions), inforOrderResponse.result.getNumber_notification(), R.drawable.bg_stock_notifi));
                            arrItemMenu.add(new ItemMenu(-1, getString(R.string.manage_reward_point), -1, R.drawable.bg_reward_point));
                            arrItemMenu.add(new ItemMenu(-1, getString(R.string.missed_sales), -1, R.drawable.bg_missed_sales));
                            arrItemMenu.add(new ItemMenu(-1, getString(R.string.promotions), -1, R.drawable.bg_promotions));
                            arrItemMenu.add(new ItemMenu(-1, getString(R.string.search), -1, R.drawable.bg_search));

                            try {
                                menu1Adapter.clear();
                                menu1Adapter.addAll(arrItemMenu);
                                updateTitleFragmentOrder(inforOrderResponse.result);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            return;
                        }
                    } else {
                        Toast.makeText(context, getResources().getString(R.string.load_data_fail), Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Throwable t) {
                t.printStackTrace();
            }
        });
    }

    public void updateTitleFragmentOrder(InforOrdersModel inforOrdersModel) {
        try {
            FragmentOrders.arrItemMenu2.clear();
            FragmentOrders.arrItemMenu2.add(new ItemMenu(inforOrdersModel.getNew_order(), getString(R.string.orders), -1, R.drawable.bg_newoders));
            FragmentOrders.arrItemMenu2.add(new ItemMenu(inforOrdersModel.getPreparing_order(), getString(R.string.preparing_orders), -1, R.drawable.bg_preparing_oreders));
            FragmentOrders.arrItemMenu2.add(new ItemMenu(inforOrdersModel.getSchedule_order(), getString(R.string.schedule_orders), 4, R.drawable.bg_despatched_orders));
            FragmentOrders.arrItemMenu2.add(new ItemMenu(inforOrdersModel.getBulk_order(), getString(R.string.bulk), 1, R.drawable.bg_canceled_orders));
            FragmentOrders.arrItemMenu2.add(new ItemMenu(inforOrdersModel.getDespatched_order(), getString(R.string.despatched_orders), -1, R.drawable.bg_produce));
            FragmentOrders.arrItemMenu2.add(new ItemMenu(inforOrdersModel.getDelivered_order(), getString(R.string.delivered_orders), -1, R.drawable.bg_my_stock));
            FragmentOrders.arrItemMenu2.get(Config.statusTypeOrder).checkBG = false;
            if (FragmentOrders.menu2Adapter != null) FragmentOrders.menu2Adapter.clear();
            FragmentOrders.menu2Adapter.addAll(FragmentOrders.arrItemMenu2);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void replaceFragment(boolean showLayoutBottom, Fragment fragment) {
        if (!showLayoutBottom) {
            fragmentUtils = new FragmentUtils(getSupportFragmentManager(), R.id.layout_show_fragment_2);
        } else {
            fragmentUtils = new FragmentUtils(getSupportFragmentManager(), R.id.layout_show_fragment);
        }
        fragmentUtils.replaceFragment(fragment);
    }

    public void showLayoutHome(boolean isShow) {
        if (isShow) layoutHome.setVisibility(View.VISIBLE);
        else layoutHome.setVisibility(View.GONE);
    }

    public void showLayoutMapView(boolean isShow) {
        if (isShow) layoutMapView.setVisibility(View.VISIBLE);
        else layoutMapView.setVisibility(View.GONE);
    }
}
