package com.grocery.fragment.fragmentManagerOffer;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.grocery.R;
import com.grocery.adapter.ManageOffersLiveAdapter;
import com.grocery.common.CRecyclerView;
import com.grocery.model.ItemManagerProductsUpdate;

import java.util.ArrayList;

/**
 * Created by ThanhBeo on 05/07/2017.
 */

public class FragmentManagerOffersLive extends Fragment implements View.OnClickListener {
    private ManageOffersLiveAdapter manageOffersLiveAdapter;
    private CRecyclerView lvManageOffersLive;
    private CRecyclerView lvMaganeLive;
    private LinearLayout btnSearch;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.manage_offers_live, container, false);
        initView(view);
        initListener();
        return view;
    }

    private void initListener() {
        btnSearch.setOnClickListener(this);
    }

    private void initView(View view) {
        btnSearch = (LinearLayout) view.findViewById(R.id.btn_search_live_offers);
        lvManageOffersLive = view.findViewById(R.id.lv_manage_offers_live);

        lvManageOffersLive = new CRecyclerView(getActivity());
        lvManageOffersLive.setNestedScrollingEnabled(false);
        manageOffersLiveAdapter = new ManageOffersLiveAdapter(getActivity(), new ArrayList<ItemManagerProductsUpdate>(),
                null);
        lvManageOffersLive.setAdapter(manageOffersLiveAdapter);
    }

    @Override
    public void onClick(View v) {

    }
}
