package com.grocery.fragment.fragmentMenu;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.grocery.R;
import com.grocery.adapter.DeliveryBoyAdapter;
import com.grocery.common.CRecyclerView;
import com.grocery.common.Config;
import com.grocery.common.CustomEditText;
import com.grocery.common.CustomTextView;
import com.grocery.controller.Controller;
import com.grocery.model.ItemDeliveryBoy;
import com.grocery.request.AddDeliveryBoyRequest;
import com.grocery.response.BaseResponse;
import com.grocery.response.DeliveryBoyResponse;
import com.grocery.utils.HideSoftKeyBroad;
import com.grocery.utils.ProgerssBarUntil;
import com.grocery.utils.StringUtil;

import java.util.ArrayList;

import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

/**
 * Created by ThanhBeo on 22/06/2017.
 */

public class FragmentAddDeliveryBoy extends Fragment implements View.OnClickListener {

    private DeliveryBoyAdapter deliveryBoyAdapter;
    private CRecyclerView lvListDeliveryBoy;
    private CustomEditText edtEnterName, edtEnterEmail, edtEnterPassword;
    private CustomTextView btnAdd;
    private ProgerssBarUntil progerssBarUntil;
    private SwipeRefreshLayout swipeRefreshLayout;
    private boolean isLoading = false;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.menu_add_delivery_boy, container, false);
        progerssBarUntil = new ProgerssBarUntil(view);
        initView(view);
        initListener();
        return view;
    }

    private void initListener() {
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipeRefreshLayout.setRefreshing(false);
                deliveryBoyAdapter.clear();
                HideSoftKeyBroad.hideSoftKeyboard(getActivity(), getView());
                loadMore();
            }
        });

        btnAdd.setOnClickListener(this);
    }

    public boolean checkOk() {
        if (edtEnterName.getText().toString().isEmpty()) {
            Toast.makeText(getActivity(), getResources().getString(R.string.enter_deliveryboy), Toast.LENGTH_SHORT).show();
            return false;
        } else if (edtEnterEmail.getText().toString().isEmpty()) {
            Toast.makeText(getActivity(), getResources().getString(R.string.please_enter_email), Toast.LENGTH_SHORT).show();
            return false;
        } else if (!StringUtil.isValidEmail(edtEnterEmail.getText().toString())) {
            Toast.makeText(getActivity(), getResources().getString(R.string.email_invalid), Toast.LENGTH_SHORT).show();
            return false;
        } else if (edtEnterPassword.getText().toString().isEmpty()) {
            Toast.makeText(getActivity(), getResources().getString(R.string.please_enter_pass), Toast.LENGTH_SHORT).show();
            return false;
        } else {
            for (int i = 0; i < deliveryBoyAdapter.getList().size(); i++) {
                if (edtEnterName.getText().toString().equals(deliveryBoyAdapter.getList().get(i).getDelivery_boy_name())) {
                    Toast.makeText(getActivity(), getResources().getString(R.string.delivery_boy_exists), Toast.LENGTH_SHORT).show();
                    return false;
                }
            }
        }
        return true;
    }

    public void loadMore() {
        getDeliveryBoy(Config.AdminId, Config.apiToken, Config.is_groceryAdmin);
    }

    public void cleanAdapter() {
        deliveryBoyAdapter.clear();
    }

    private void initView(View view) {
        lvListDeliveryBoy = new CRecyclerView(getActivity());
        lvListDeliveryBoy = (CRecyclerView) view.findViewById(R.id.lv_delivery_boy);
        lvListDeliveryBoy.setNestedScrollingEnabled(false);
        deliveryBoyAdapter = new DeliveryBoyAdapter(getActivity(), new ArrayList<ItemDeliveryBoy>(), this);
        lvListDeliveryBoy.setAdapter(deliveryBoyAdapter);
        edtEnterName = (CustomEditText) view.findViewById(R.id.edt_enter_name_delivery_boy);
        edtEnterEmail = (CustomEditText) view.findViewById(R.id.edt_email);
        edtEnterPassword = (CustomEditText) view.findViewById(R.id.edt_password);
        btnAdd = (CustomTextView) view.findViewById(R.id.btn_add_delivery_boy);
        swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swiperefresh);
        loadMore();
    }

    @Override
    public void onClick(View v) {
        HideSoftKeyBroad.hideSoftKeyboard(getActivity(), getView());
        if (!isLoading || !checkOk()) {
            return;
        }
        AddDeliveryBoyRequest addDeliveryBoyRequest = new AddDeliveryBoyRequest(Config.AdminId,
                Config.apiToken, Config.is_groceryAdmin, edtEnterName.getText().toString(),
                edtEnterEmail.getText().toString(), edtEnterPassword.getText().toString());
        addDeliveryBoy(getActivity(), addDeliveryBoyRequest);
    }

    private void getDeliveryBoy(int userID, String apiToken, int is_groceryAdmin) {
        progerssBarUntil.setInvisibeLayoutMain();
        isLoading = false;

        Controller controller = new Controller();
        Call<DeliveryBoyResponse> call = controller.service.getDeliveryBoy(userID, apiToken, is_groceryAdmin);
        call.enqueue(new Callback<DeliveryBoyResponse>() {
            @Override
            public void onResponse(Response<DeliveryBoyResponse> response, Retrofit retrofit) {
                progerssBarUntil.setVisibeLayoutMain();
                if (response != null) {
                    DeliveryBoyResponse deliveryBoyResponse = response.body();
                    if (deliveryBoyResponse != null && deliveryBoyResponse.code == 200) {
                        deliveryBoyAdapter.addAll(deliveryBoyResponse.result);
                    }
                    isLoading = true;
                }
            }

            @Override
            public void onFailure(Throwable t) {
                t.printStackTrace();
            }
        });
    }

    private void addDeliveryBoy(final Context context, AddDeliveryBoyRequest addDeliveryBoyRequest) {
        progerssBarUntil.setInvisibeLayoutMain();

        Controller controller = new Controller();
        Call<BaseResponse> call = controller.service.addDeliveryBoy(addDeliveryBoyRequest);
        call.enqueue(new Callback<BaseResponse>() {
            @Override
            public void onResponse(Response<BaseResponse> response, Retrofit retrofit) {
                if (response != null) {
                    BaseResponse baseResponse = response.body();
                    if (baseResponse != null) {
                        if (baseResponse.code == 200) {
                            edtEnterName.setText("");
                            edtEnterEmail.setText("");
                            edtEnterPassword.setText("");
                            deliveryBoyAdapter.clear();
                            loadMore();
                            Toast.makeText(context, getString(R.string.delivery_boy_added), Toast.LENGTH_SHORT).show();
                        } else {
                            progerssBarUntil.setVisibeLayoutMain();
                            Toast.makeText(context, baseResponse.message.toString(), Toast.LENGTH_SHORT).show();
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
