package com.grocery.fragment.fragmentLiveCustomer;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.grocery.R;
import com.grocery.adapter.LiveCustomerAdapter;
import com.grocery.common.CRecyclerView;
import com.grocery.model.ItemCategory;

import java.util.ArrayList;

/**
 * Created by ThanhBeo on 18/07/2017.
 */

public class FragmentLiveCusAllCategory extends Fragment {
    private ArrayList<ItemCategory> arrData;
    private CRecyclerView lvLiveCustomer;
    private LiveCustomerAdapter liveCustomerAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.live_customers_all_category, container, false);
        initView(view);
        initListener();
        return view;
    }

    private void initListener() {
    }

    private void initView(View view) {
        arrData = new ArrayList<>();

        lvLiveCustomer = (CRecyclerView) view.findViewById(R.id.lv_live_customer);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(),
                5, GridLayoutManager.VERTICAL, false);
        lvLiveCustomer.setLayoutManager(gridLayoutManager);
        liveCustomerAdapter = new LiveCustomerAdapter(getActivity(), arrData);
        lvLiveCustomer.setAdapter(liveCustomerAdapter);
    }
}
