package com.grocery.fragment.fragmentRewardPoint;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListPopupWindow;
import android.widget.Spinner;

import com.grocery.R;
import com.grocery.adapter.RewardManageAdapter;
import com.grocery.common.CRecyclerView;
import com.grocery.model.ItemMyStock;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by ThanhBeo on 04/07/2017.
 */

public class FragmentRewadManager extends Fragment implements View.OnClickListener {
    private CRecyclerView lvList;
    private ArrayList<ItemMyStock> arrItemMyStock;
    private RewardManageAdapter rewardManageAdapter;
    private ImageView imSearch;
    private Spinner spnChooseRrand;
    private Spinner spnChooseCategory;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view =inflater.inflate(R.layout.reward_manager,container,false);
        initView(view);
        initListener();
        return view;
    }

    private void initListener() {
        imSearch.setOnClickListener(this);
    }

    private void initView(View view) {
        imSearch = (ImageView) view.findViewById(R.id.im_searc_reward_manage);
        //        hien thi list view
        arrItemMyStock = new ArrayList<>();
        arrItemMyStock.add(new ItemMyStock(5, 3, 600, "ABC"));
        arrItemMyStock.add(new ItemMyStock(6, 4, 700, "SDS"));
        arrItemMyStock.add(new ItemMyStock(7, 5, 500, "ABC"));
        arrItemMyStock.add(new ItemMyStock(8, 2, 800, "VXC"));
        arrItemMyStock.add(new ItemMyStock(8, 1, 900, "ADA"));
        arrItemMyStock.add(new ItemMyStock(9, 6, 1000, "ABC"));
        arrItemMyStock.add(new ItemMyStock(10, 3, 400, "RWE"));
        arrItemMyStock.add(new ItemMyStock(3, 5, 800, "PPP"));
        arrItemMyStock.add(new ItemMyStock(15, 2, 1400, "AAC"));
        arrItemMyStock.add(new ItemMyStock(8, 1, 900, "ADA"));
        arrItemMyStock.add(new ItemMyStock(5, 3, 600, "ABC"));

        lvList = (CRecyclerView) view.findViewById(R.id.lv_reward_mangage);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(),
                5, GridLayoutManager.VERTICAL, false);
        lvList.setLayoutManager(gridLayoutManager);
        rewardManageAdapter = new RewardManageAdapter(getActivity(), arrItemMyStock);
        lvList.setAdapter(rewardManageAdapter);

        spnChooseRrand = view.findViewById(R.id.spn_choose_brand);
        spnChooseCategory = view.findViewById(R.id.spn_choose_category);

        try {
            Field popup = Spinner.class.getDeclaredField("mPopup");
            popup.setAccessible(true);
            // set popup choose Brand
            android.widget.ListPopupWindow popupWindow = (android.widget.ListPopupWindow) popup.get(spnChooseRrand);
            popupWindow.setBackgroundDrawable(getResources().getDrawable(R.drawable.radius_edt));
            popupWindow.setHeight(ListPopupWindow.WRAP_CONTENT);

            // set popup choose category
            android.widget.ListPopupWindow popupWindow1 = (android.widget.ListPopupWindow) popup.get(spnChooseCategory);
            popupWindow1.setBackgroundDrawable(getResources().getDrawable(R.drawable.radius_edt));
            popupWindow1.setHeight(ListPopupWindow.WRAP_CONTENT);
        } catch (NoClassDefFoundError | ClassCastException | NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }

        ArrayAdapter adapterBrand = new ArrayAdapter(getActivity(),
                R.layout.item_spinner, getListOption());
        spnChooseRrand.setAdapter(adapterBrand);

        ArrayAdapter adapterCategory = new ArrayAdapter(getActivity(),
                R.layout.item_spinner, getListOption());
        spnChooseCategory.setAdapter(adapterCategory);

        /*spnChooseRrand.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });*/
    }

    @Override
    public void onClick(View v) {

    }

    private List<String> getListOption() {
        List<String> list = new ArrayList<>();
        list.add("Option 1");
        list.add("Option 2");
        list.add("Option 3");
        list.add("Option 4");
        list.add("Option 5");

        return list;
    }
}
