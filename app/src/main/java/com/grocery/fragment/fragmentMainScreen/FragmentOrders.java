package com.grocery.fragment.fragmentMainScreen;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.grocery.R;
import com.grocery.activity.MainActivity;
import com.grocery.adapter.Menu2Adapter;
import com.grocery.common.CRecyclerView;
import com.grocery.common.Config;
import com.grocery.common.CustomTextView;
import com.grocery.common.RecyclerItemClickListener;
import com.grocery.fragment.fragmentOrders.FragmentBulkOrders;
import com.grocery.fragment.fragmentOrders.FragmentCanceledOrders;
import com.grocery.fragment.fragmentOrders.FragmentDeliveredOrders;
import com.grocery.fragment.fragmentOrders.FragmentDespatchedOrders;
import com.grocery.fragment.fragmentOrders.FragmentNewOrders;
import com.grocery.fragment.fragmentOrders.FragmentPreparingOrders;
import com.grocery.fragment.fragmentOrders.FragmentScheduleOrders;
import com.grocery.model.InforOrdersModel;
import com.grocery.model.ItemMenu;
import com.grocery.utils.FragmentUtils;

import java.util.ArrayList;

/**
 * Created by ThanhBeo on 13/09/2017.
 */

public class FragmentOrders extends Fragment implements View.OnClickListener {

    private CRecyclerView lvcategory2;
    public static ArrayList<ItemMenu> arrItemMenu2 = new ArrayList<>();
    public static Menu2Adapter menu2Adapter;
    private MainActivity mainActivity;
    private FragmentUtils fragmentUtils;
    private FragmentBulkOrders fragmentBulkOrders;
    private FragmentCanceledOrders fragmentCanceledOrders;
    private FragmentDeliveredOrders fragmentDeliveredOrders;
    private FragmentDespatchedOrders fragmentDespatchedOrders;
    private FragmentNewOrders fragmentNewOrders;
    private FragmentPreparingOrders fragmentPreparingOrders;
    private FragmentScheduleOrders fragmentScheduleOrders;
    private LinearLayout layoutCheckBoxFilter;
    public static InforOrdersModel inforOrdersModel;
    private LinearLayout cbNormal;
    private LinearLayout cbBulk;
    private LinearLayout cbSchedule;
    private LinearLayout cbPickUp;
    private LinearLayout cbTimeLeft;
    private LinearLayout cbTips;
    private CustomTextView tvNumberNotification;
    public static ArrayList<LinearLayout> arrCb;
    public static Integer[] arrStatusFilterfrm;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.main_fragment, container, false);
        mainActivity = (MainActivity) getActivity();
        inforOrdersModel = mainActivity.getInforOrdersModel();
        initView(view);
        getBtnSearch();
        initListener(view);
        return view;
    }

    public void getBtnSearch() {
        MainActivity.setOnClickListener(new MainActivity.OnClickListener() {
            @Override
            public void onItemClickedSearch() {
                try {
                    fragmentNewOrders.cleanAdapter();
                    fragmentNewOrders.loadMore();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                try {
                    fragmentPreparingOrders.cleanAdapter();
                    fragmentPreparingOrders.loadMore();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                try {
                    fragmentScheduleOrders.cleanAdapter();
                    fragmentScheduleOrders.loadMore();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                try {
                    fragmentBulkOrders.cleanAdapter();
                    fragmentBulkOrders.loadMore();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                try {
                    fragmentDespatchedOrders.cleanAdapter();
                    fragmentDespatchedOrders.loadMore();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                try {
                    fragmentDeliveredOrders.cleanAdapter();
                    fragmentDeliveredOrders.loadMore();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                try {
                    fragmentCanceledOrders.cleanAdapter();
                    fragmentCanceledOrders.loadMore();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void initView(View view) {
        tvNumberNotification = view.findViewById(R.id.tv_number_notification);
        /*init check box*/
        cbNormal = (LinearLayout) view.findViewById(R.id.cbNormal);
        cbBulk = (LinearLayout) view.findViewById(R.id.cbBulk);
        cbSchedule = (LinearLayout) view.findViewById(R.id.cbSchedule);
        cbPickUp = (LinearLayout) view.findViewById(R.id.cbPickUp);
        cbTimeLeft = (LinearLayout) view.findViewById(R.id.cbTimeLeft);
        cbTips = (LinearLayout) view.findViewById(R.id.cbTips);
        Config.FILTER = 0;
        arrCb = new ArrayList<>();
        arrCb.add(cbNormal);
        arrCb.add(cbBulk);
        arrCb.add(cbSchedule);
        arrCb.add(cbPickUp);
        arrCb.add(cbTimeLeft);
        arrCb.add(cbTips);
        arrStatusFilterfrm = new Integer[]{0, 0, 0, 0, 0, 0, 0};
        /**/
        layoutCheckBoxFilter = (LinearLayout) view.findViewById(R.id.layoutCheckBoxFilter);
        lvcategory2 = (CRecyclerView) view.findViewById(R.id.lv_list_category);
        lvcategory2.setNestedScrollingEnabled(false);
        GridLayoutManager gridLayoutManager2 = new GridLayoutManager(getActivity(), 6, GridLayoutManager.VERTICAL, false);
        lvcategory2.setLayoutManager(gridLayoutManager2);
        menu2Adapter = new Menu2Adapter(getActivity(), new ArrayList<ItemMenu>());
        lvcategory2.setAdapter(menu2Adapter);
        try {
            setListTitle();
        } catch (Exception e) {
            e.printStackTrace();
        }
        initStartFragment();
    }

    public void setListTitle() {
        // Set number notification
        tvNumberNotification.setText(inforOrdersModel.getNumber_notification() + "");

        arrItemMenu2.clear();
        arrItemMenu2.add(new ItemMenu(inforOrdersModel.getNew_order(), getString(R.string.orders), -1, R.drawable.bg_newoders));
        arrItemMenu2.add(new ItemMenu(inforOrdersModel.getPreparing_order(), getString(R.string.preparing_orders), -1, R.drawable.bg_preparing_oreders));
        arrItemMenu2.add(new ItemMenu(inforOrdersModel.getSchedule_order(), getString(R.string.schedule_orders), 4, R.drawable.bg_despatched_orders));
        arrItemMenu2.add(new ItemMenu(inforOrdersModel.getBulk_order(), getString(R.string.bulk), 1, R.drawable.bg_canceled_orders));
        arrItemMenu2.add(new ItemMenu(inforOrdersModel.getDespatched_order(), getString(R.string.despatched_orders), -1, R.drawable.bg_produce));
        arrItemMenu2.add(new ItemMenu(inforOrdersModel.getDelivered_order(), getString(R.string.delivered_orders), -1, R.drawable.bg_my_stock));
        menu2Adapter.clear();
        menu2Adapter.addAll(arrItemMenu2);
    }

    private void initStartFragment() {
        fragmentUtils = new FragmentUtils(getChildFragmentManager(), R.id.layout_show_fragment);
        fragmentNewOrders = new FragmentNewOrders();
        fragmentPreparingOrders = new FragmentPreparingOrders();
        fragmentDespatchedOrders = new FragmentDespatchedOrders();
        fragmentCanceledOrders = new FragmentCanceledOrders();
        fragmentScheduleOrders = new FragmentScheduleOrders();
        fragmentBulkOrders = new FragmentBulkOrders();
        fragmentDeliveredOrders = new FragmentDeliveredOrders();
        if (Config.KEY_FRAGMENT_MAIN.equals(getString(R.string.orders))) {
            Config.statusTypeOrder = 0;
            fragmentUtils.addToFragment(fragmentNewOrders);
            mainActivity.showLayoutHome(true);
            mainActivity.showLayoutMapView(true);
        }
        if (Config.KEY_FRAGMENT_MAIN.equals(getString(R.string.preparing_orders))) {
            Config.statusTypeOrder = 1;
            fragmentUtils.addToFragment(fragmentPreparingOrders);
            mainActivity.showLayoutHome(true);
            mainActivity.showLayoutMapView(true);
        }
        if (Config.KEY_FRAGMENT_MAIN.equals(getString(R.string.despatched_orders))) {
            Config.statusTypeOrder = 4;
            fragmentUtils.addToFragment(fragmentDespatchedOrders);
            mainActivity.showLayoutHome(true);
            mainActivity.showLayoutMapView(false);
        }
        if (Config.KEY_FRAGMENT_MAIN.equals(getString(R.string.Canceled_Order))) {
            Config.statusTypeOrder = 6;
            lvcategory2.setVisibility(View.GONE);
            fragmentUtils.addToFragment(fragmentCanceledOrders);
            mainActivity.showLayoutHome(true);
            mainActivity.showLayoutMapView(false);
        }
        for (int i = 0; i < arrItemMenu2.size(); i++) {
            if (Config.KEY_FRAGMENT_MAIN.equals(arrItemMenu2.get(i).getName())) {
                arrItemMenu2.get(i).checkBG = false;
            }
        }
        arrStatusFilterfrm[Config.statusTypeOrder] = 0;
        menu2Adapter.notifyDataSetChanged();
    }

    public void showScheduleOrder() {
        fragmentUtils.changeFragment(fragmentScheduleOrders);
        mainActivity.tvMainName.setText(getResources().getString(R.string.schedule_orders));
        Config.statusTypeOrder = 2;
        for (int i = 0; i < arrItemMenu2.size(); i++) {
            arrItemMenu2.get(i).checkBG = true;
            if (arrItemMenu2.get(i).getName().equals(getResources().getString(R.string.schedule_orders))) {
                arrItemMenu2.get(i).checkBG = false;
            }
        }
        menu2Adapter.notifyDataSetChanged();
    }

    public void showBulkOrder() {
        fragmentUtils.changeFragment(fragmentBulkOrders);
        Config.statusTypeOrder = 3;
        mainActivity.tvMainName.setText(getResources().getString(R.string.bulk));
        for (int i = 0; i < arrItemMenu2.size(); i++) {
            arrItemMenu2.get(i).checkBG = true;
            if (arrItemMenu2.get(i).getName().equals(getResources().getString(R.string.bulk))) {
                arrItemMenu2.get(i).checkBG = false;
            }
        }
        menu2Adapter.notifyDataSetChanged();
    }

    @Override
    public void onResume() {
        super.onResume();
        if (Config.showBulkOrder) {
            showBulkOrder();
            Config.showBulkOrder = false;
        }
        if (Config.showScheduleOrder) {
            showScheduleOrder();
            Config.showScheduleOrder = false;
        }
    }

    private void initListener(View view) {
        layoutCheckBoxFilter.setOnClickListener(this);
        view.findViewById(R.id.layout_show_fragment).setOnClickListener(this);
        lvcategory2.addOnItemTouchListener(new RecyclerItemClickListener(getActivity(), new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                for (int i = 0; i < arrItemMenu2.size(); i++) {
                    arrItemMenu2.get(i).checkBG = true;
                }
                try {
                    arrItemMenu2.get(position).checkBG = false;
                    menu2Adapter.notifyDataSetChanged();
                    if (arrItemMenu2.get(position).getName().equals(getString(R.string.orders))) {
                        Config.statusTypeOrder = 0;
                        fragmentUtils.changeFragment(fragmentNewOrders);
                        mainActivity.showLayoutHome(true);
                        mainActivity.showLayoutMapView(true);
                    } else if (arrItemMenu2.get(position).getName().equals(getString(R.string.preparing_orders))) {
                        Config.statusTypeOrder = 1;
                        fragmentUtils.changeFragment(fragmentPreparingOrders);
                        mainActivity.showLayoutHome(true);
                        mainActivity.showLayoutMapView(true);
                    } else if (arrItemMenu2.get(position).getName().equals(getString(R.string.schedule_orders))) {
                        Config.statusTypeOrder = 2;
                        fragmentUtils.changeFragment(fragmentScheduleOrders);
                        mainActivity.showLayoutHome(true);
                        mainActivity.showLayoutMapView(false);
                    } else if (arrItemMenu2.get(position).getName().equals(getString(R.string.bulk))) {
                        Config.statusTypeOrder = 3;
                        fragmentUtils.changeFragment(fragmentBulkOrders);
                        mainActivity.showLayoutHome(true);
                        mainActivity.showLayoutMapView(false);
                    } else if (arrItemMenu2.get(position).getName().equals(getString(R.string.despatched_orders))) {
                        Config.statusTypeOrder = 4;
                        fragmentUtils.changeFragment(fragmentDespatchedOrders);
                        mainActivity.showLayoutHome(true);
                        mainActivity.showLayoutMapView(false);
                    } else if (arrItemMenu2.get(position).getName().equals(getString(R.string.delivered_orders))) {
                        Config.statusTypeOrder = 5;
                        fragmentUtils.changeFragment(fragmentDeliveredOrders);
                        mainActivity.showLayoutHome(true);
                        mainActivity.showLayoutMapView(false);
                    }
                    setStatusFilter(Config.statusTypeOrder);
                    mainActivity.tvMainName.setText(arrItemMenu2.get(position).getName());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }));

        for (int i = 0; i < arrCb.size(); i++) {
            final int finalI = i;
            arrCb.get(i).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (Config.statusTypeOrder == 3 || Config.statusTypeOrder == 2) {
                        return;
                    } else {
                        for (int i = 0; i < arrCb.size(); i++) {
                            if (finalI == i) {
                                if (arrCb.get(finalI).isSelected()) {
                                    arrCb.get(finalI).setSelected(false);
                                } else {
                                    arrCb.get(finalI).setSelected(true);
                                }
                            } else {
                                arrCb.get(i).setSelected(false);
                            }
                        }
                    }
                    switch (Config.statusTypeOrder) {
                        case 0:
                            fragmentNewOrders.filter(getTypeFilter());
                            break;
                        case 1:
                            fragmentPreparingOrders.filter(getTypeFilter());
                            break;
                        case 2:
                            break;
                        case 3:
                            break;
                        case 4:
                            fragmentDespatchedOrders.filter(getTypeFilter());
                            break;
                        case 5:
                            fragmentDeliveredOrders.filter(getTypeFilter());
                            break;
                        case 6:
                            fragmentCanceledOrders.filter(getTypeFilter());
                            break;
                    }
                    arrStatusFilterfrm[Config.statusTypeOrder] = getTypeFilter();
                }
            });
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Config.FILTER = 0;
    }

    public void setStatusFilter(int statusTypeOrder) {
        for (int i = 0; i < arrCb.size(); i++) {
            arrCb.get(i).setSelected(false);
        }
        Config.FILTER = arrStatusFilterfrm[statusTypeOrder];
        if (Config.FILTER == 0) {
            return;
        }
        arrCb.get(Config.FILTER - 1).setSelected(true);
    }

    public int getTypeFilter() {
        for (int i = 0; i < arrCb.size(); i++) {
            if (arrCb.get(i).isSelected()) {
                return i + 1;
            }
        }
        return 0;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.layoutCheckBoxFilter:
                break;
            case R.id.layout_show_fragment:
                break;
        }
    }
}
