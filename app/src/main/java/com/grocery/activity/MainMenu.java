package com.grocery.activity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.grocery.R;
import com.grocery.adapter.Menu1Adapter;
import com.grocery.common.CRecyclerView;
import com.grocery.common.Config;
import com.grocery.common.RecyclerItemClickListener;
import com.grocery.common.SwitchView;
import com.grocery.dialog.DialogCloseShop;
import com.grocery.dialog.DialogNotifiOrder;
import com.grocery.fragment.fragmentMenu.FragmentAboutGrocery;
import com.grocery.fragment.fragmentMenu.FragmentAddBuilding;
import com.grocery.fragment.fragmentMenu.FragmentAddDeliveryBoy;
import com.grocery.fragment.fragmentMenu.FragmentBulkOrderSetting;
import com.grocery.fragment.fragmentMenu.FragmentContact;
import com.grocery.fragment.fragmentMenu.FragmentDeliveryTiming;
import com.grocery.model.ItemMenu;
import com.grocery.utils.FragmentUtils;

import java.util.ArrayList;

/**
 * Created by ThanhBeo on 16/06/2017.
 */

public class MainMenu extends AppCompatActivity implements View.OnClickListener {
    private ArrayList<ItemMenu> arrItem;
    private CRecyclerView lvMenu;
    private Menu1Adapter menuAdapter;
    private ImageView imCloseMenu;
    private RelativeLayout lnListMenu;
    private View lnShowMenu;
    private TextView tvNameMenu;
    private ImageView imIconMenu;
    public static TextView tvTime;
    public SwitchView swCloseShop;
    public LinearLayout layoutHome;

    private FragmentUtils fragmentUtils;
    private FragmentAddBuilding fragmentAddBuilding;
    private FragmentAddDeliveryBoy fragmentAddDeliveryBoy;
    private FragmentBulkOrderSetting fragmentBulkOrderSetting;
    private FragmentDeliveryTiming fragmentDeliveryTiming;
    private FragmentContact fragmentContact;
    private FragmentAboutGrocery fragmentAboutGrocery;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.menu);
        intiView();
        initListener();
    }

    @Override
    protected void onResume() {
        super.onResume();
        MainActivity.instanceMain = this;
    }

    public void initDialogNotifi(String body, final int orderId, int orderType) {
        DialogNotifiOrder.orderId = orderId;
        DialogNotifiOrder.orderType = orderType;
        MainActivity.dialogNotifi = new DialogNotifiOrder(this, android.R.style.Theme_Light_NoTitleBar_Fullscreen);
        MainActivity.dialogNotifi.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        MainActivity.dialogNotifi.show();
    }

    private void initListener() {
        swCloseShop.setOnCheckedChangeListener(new SwitchView.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(SwitchView switchView, boolean isChecked) {
                if (!isChecked) {
                    DialogCloseShop dialogCloseShop = new DialogCloseShop(MainMenu.this,
                            android.R.style.Theme_Light_NoTitleBar_Fullscreen);
                    dialogCloseShop.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                    dialogCloseShop.show();
                    dialogCloseShop.setOnCancelListener(new DialogInterface.OnCancelListener() {
                        @Override
                        public void onCancel(DialogInterface dialog) {
                            swCloseShop.setChecked(true);
                        }
                    });
                }
            }
        });

        layoutHome.setOnClickListener(this);
        imCloseMenu.setOnClickListener(this);
        lvMenu.addOnItemTouchListener(new RecyclerItemClickListener(getApplicationContext(), new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                if (arrItem.get(position).getName().equals(getString(R.string.menu_add_delivery_boy))) {
                    fragmentUtils.replaceFragment(fragmentAddDeliveryBoy);
                } else if (arrItem.get(position).getName().equals(getString(R.string.menu_bulk_orders))) {
                    fragmentUtils.replaceFragment(fragmentBulkOrderSetting);
                } else if (arrItem.get(position).getName().equals(getString(R.string.menu_Add_delivery_timing))) {
                    fragmentUtils.replaceFragment(fragmentDeliveryTiming);
                } else if (arrItem.get(position).getName().equals(getString(R.string.menu_add_building))) {
                    fragmentUtils.replaceFragment(fragmentAddBuilding);
                } else if (arrItem.get(position).getName().equals(getString(R.string.menu_About_Groccery))) {
                    fragmentUtils.replaceFragment(fragmentAboutGrocery);
                } else if (arrItem.get(position).getName().equals(getString(R.string.menu_Contact_Details))) {
                    fragmentUtils.replaceFragment(fragmentContact);
                } else if (arrItem.get(position).getName().equals(getString(R.string.menu_logout))) {

                    SharedPreferences sharedPreferences = getSharedPreferences(Config.Pref, MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString(Config.KEY_ADMIN_PROFILE, "").commit();

                    Intent intent = new Intent(MainMenu.this, MainLogin.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                    return;
                }
                lnListMenu.setVisibility(LinearLayout.INVISIBLE);
                lnShowMenu.setVisibility(LinearLayout.VISIBLE);
                tvNameMenu.setText(arrItem.get(position).getName());
            }
        }));

        imIconMenu.setOnClickListener(this);
    }

    private void intiView() {
        lvMenu = (CRecyclerView) findViewById(R.id.lvMenu2);
        lvMenu.setNestedScrollingEnabled(false);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 3, GridLayoutManager.VERTICAL, false);
        lvMenu.setLayoutManager(gridLayoutManager);
        arrItem = new ArrayList<>();
        arrItem.add(new ItemMenu(-1, getResources().getString(R.string.menu_add_delivery_boy), -1, R.drawable.bg_preparing_oreders));
        arrItem.add(new ItemMenu(-1, getResources().getString(R.string.menu_bulk_orders), -1, R.drawable.bg_maganer_offers));
        arrItem.add(new ItemMenu(-1, getResources().getString(R.string.menu_Add_delivery_timing), -1, R.drawable.bg_all_products));
        arrItem.add(new ItemMenu(-1, getResources().getString(R.string.menu_add_building), -1, R.drawable.bg_my_stock));
        arrItem.add(new ItemMenu(-1, getResources().getString(R.string.menu_About_Groccery), -1, R.drawable.bg_newoders));
        arrItem.add(new ItemMenu(-1, getResources().getString(R.string.menu_Contact_Details), -1, R.drawable.bg_contact_details));
        arrItem.add(new ItemMenu(-1, getResources().getString(R.string.menu_logout), -1, R.drawable.bg_canceled_orders));
        menuAdapter = new Menu1Adapter(this, arrItem);
        lvMenu.setAdapter(menuAdapter);

        imCloseMenu = (ImageView) findViewById(R.id.imCloseMenu);
        imIconMenu = (ImageView) findViewById(R.id.imMenu2);
        tvNameMenu = (TextView) findViewById(R.id.tv_name_item_menu);
        lnListMenu = (RelativeLayout) findViewById(R.id.layout_list_menu);
        lnShowMenu = (View) findViewById(R.id.layout_fragment_menu);
        tvTime = (TextView) lnShowMenu.findViewById(R.id.tv_main_time_view_user);
        swCloseShop = lnShowMenu.findViewById(R.id.switch_closeShop);
        layoutHome = lnShowMenu.findViewById(R.id.layout_home);

        /*init Fragment*/
        fragmentUtils = new FragmentUtils(getSupportFragmentManager(), R.id.add_fragment);
        fragmentAddBuilding = new FragmentAddBuilding();
        fragmentContact = new FragmentContact();
        fragmentAddDeliveryBoy = new FragmentAddDeliveryBoy();
        fragmentBulkOrderSetting = new FragmentBulkOrderSetting();
        fragmentDeliveryTiming = new FragmentDeliveryTiming();
        fragmentAboutGrocery = new FragmentAboutGrocery();
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.imCloseMenu:
                finish();
                break;
            case R.id.imMenu2:
                if (fragmentUtils.getCurrentFragment() == fragmentAddDeliveryBoy) {
                    try {
                        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                        imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
                    } catch (Exception err) {

                    }
                }
                lnListMenu.setVisibility(LinearLayout.VISIBLE);
                lnShowMenu.setVisibility(LinearLayout.INVISIBLE);
                break;
            case R.id.layout_home:
                finish();
                break;
        }
    }


    @Override
    public void onBackPressed() {
        if (fragmentUtils.getCurrentFragment() == fragmentAddDeliveryBoy) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        }
        if (lnListMenu.isShown()) {
            finish();
        } else {
            lnListMenu.setVisibility(LinearLayout.VISIBLE);
            lnShowMenu.setVisibility(LinearLayout.INVISIBLE);
        }
    }
}
