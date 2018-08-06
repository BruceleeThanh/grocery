package com.grocery.fragment.fragmentMainScreen;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.grocery.R;
import com.grocery.adapter.UserAdapter;
import com.grocery.common.CRecyclerView;
import com.grocery.common.Config;
import com.grocery.common.CustomEditText;
import com.grocery.controller.Controller;
import com.grocery.model.UserModel;
import com.grocery.response.GetUserResponse;
import com.grocery.utils.HideSoftKeyBroad;
import com.grocery.utils.ProgerssBarUntil;
import com.grocery.utils.Utils;

import java.util.ArrayList;

import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

/**
 * Created by ThanhBeo on 22/06/2017.
 */

public class FragmentAllUser extends Fragment implements View.OnClickListener {
    private CRecyclerView lvUser;
    private UserAdapter userAdapter;
    private ImageView imSearch;
    private ProgerssBarUntil progerssBarUntil;
    private boolean isCanNext = true;
    private boolean isProgessingLoadMore = true;
    private int page_number = 0;
    private SwipeRefreshLayout swipeRefreshLayout;
    private String customName = "";
    private String flatNumber = "";
    private String buildingName = "";
    private CustomEditText edtCustomName;
    private CustomEditText edtFlat;
    private CustomEditText edtBuildingName;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.all_user, container, false);
        progerssBarUntil = new ProgerssBarUntil(view);
        initView(view);
        initData();
        initListener();
        return view;
    }

    private void initData() {
        loadMore();
    }

    private void initListener() {
        imSearch.setOnClickListener(this);
        lvUser.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (Utils.isReadyForPullEnd(recyclerView) && isCanNext && !isProgessingLoadMore) {
                    loadMore();
                }
            }
        });
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipeRefreshLayout.setRefreshing(false);
                cleanAdapter();
                cleanSearch();
                loadMore();
            }
        });
    }

    public void cleanSearch() {
        edtBuildingName.setText("");
        edtFlat.setText("");
        edtCustomName.setText("");
        buildingName = "";
        flatNumber = "";
        customName = "";
    }

    private void cleanAdapter() {
        page_number = 0;
        userAdapter.clear();
    }

    private void loadMore() {
        getAllUser(Config.AdminId, Config.apiToken, customName, flatNumber, buildingName,
                Config.PAGE_SIZE, ++page_number, Config.is_groceryAdmin);
    }

    private void initView(View view) {
        imSearch = (ImageView) view.findViewById(R.id.im_search_all_users);
        lvUser = (CRecyclerView) view.findViewById(R.id.lv_all_user);
        userAdapter = new UserAdapter(getActivity(), new ArrayList<UserModel>());
        lvUser.setAdapter(userAdapter);
        swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swiperefresh);
        edtCustomName = (CustomEditText) view.findViewById(R.id.edtCustomName);
        edtFlat = (CustomEditText) view.findViewById(R.id.edtFlat);
        edtBuildingName = (CustomEditText) view.findViewById(R.id.edtBuildingName);
    }

    @Override
    public void onClick(View v) {
        HideSoftKeyBroad.hideSoftKeyboard(getActivity(), getView());
        switch (v.getId()) {
            case R.id.im_search_all_users:
                if (edtCustomName.getText().toString().isEmpty() && edtFlat.getText().toString().isEmpty() &&
                        edtBuildingName.getText().toString().isEmpty()) {
                    Toast.makeText(getActivity(), getResources().getString(R.string.enter_search), Toast.LENGTH_LONG).show();
                    return;
                }
                if (edtCustomName.getText().toString().isEmpty()) {
                    customName = "";
                } else {
                    customName = edtCustomName.getText().toString();
                }
                if (edtFlat.getText().toString().isEmpty()) {
                    flatNumber = "";
                } else {
                    flatNumber = edtFlat.getText().toString();
                }
                if (edtBuildingName.getText().toString().isEmpty()) {
                    buildingName = "";
                } else {
                    buildingName = edtBuildingName.getText().toString();
                }
                cleanAdapter();
                loadMore();
                break;
        }
    }

    private void getAllUser(int userID, String apiToken, String customer_name,
                            String flat_number, String building_name, final int page_size,
                            int page_number, int is_groceryAdmin) {
        progerssBarUntil.setInvisibeLayoutMain();

        Controller controller = new Controller();
        Call<GetUserResponse> call = controller.service.getAllUser(userID, apiToken, customer_name,
                flat_number, building_name, page_size, page_number, is_groceryAdmin);
        call.enqueue(new Callback<GetUserResponse>() {
            @Override
            public void onResponse(Response<GetUserResponse> response, Retrofit retrofit) {
                progerssBarUntil.setVisibeLayoutMain();
                isProgessingLoadMore = false;
                if (response != null) {
                    GetUserResponse getUserResponse = response.body();
                    if (getUserResponse != null) {
                        if (getUserResponse.code == 200) {
                            if (getUserResponse.result.size() == page_size) {
                                isCanNext = true;
                            } else {
                                isCanNext = false;
                            }
                            userAdapter.addAll(getUserResponse.result);
                        }
                    }
                }
            }

            @Override
            public void onFailure(Throwable t) {
                t.printStackTrace();
            }
        });
    }
}
