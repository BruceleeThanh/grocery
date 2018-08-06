package com.grocery.fragment.fragmentMenu;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.grocery.R;
import com.grocery.common.Config;
import com.grocery.common.CustomTextView;
import com.grocery.controller.Controller;
import com.grocery.request.BulkOrderSettingRequest;
import com.grocery.response.BaseResponse;
import com.grocery.response.BulkOrderSettingResponse;

import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

public class FragmentBulkOrderSetting extends Fragment implements View.OnClickListener {
    private LinearLayout rdAlso;
    private LinearLayout rdOnly;
    private CustomTextView btnSave;
    private LinearLayout lnRd1Bulk;
    private LinearLayout lnRd2Bulk;
    private CustomTextView tvAlso;
    private CustomTextView tvOnly;
    private static final int TYPE_ALSO_RECEIVE = 1;
    private static final int TYPE_ONLY_RECEIVE = 2;
    private int mTypeReceive;
    private boolean mCkickSave;
    private int mTypeReceiveSend;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.menu_builk_settings, container, false);
        initView(view);
        getBulkOrderSetting();
        initListner();
        return view;
    }


    private void initView(View view) {
        rdAlso = (LinearLayout) view.findViewById(R.id.rd_also_recive);
        rdOnly = (LinearLayout) view.findViewById(R.id.rd_only_recive);
        btnSave = view.findViewById(R.id.btn_save_update_setting);
        lnRd1Bulk = (LinearLayout) view.findViewById(R.id.layout_rd1_bulk);
        lnRd2Bulk = (LinearLayout) view.findViewById(R.id.layout_rd2_bulk);
        tvAlso = (CustomTextView) view.findViewById(R.id.tv_also_receive);
        tvOnly = (CustomTextView) view.findViewById(R.id.tv_only_receive);
    }

    private void initListner() {
        lnRd1Bulk.setOnClickListener(this);
        lnRd2Bulk.setOnClickListener(this);
        btnSave.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.layout_rd1_bulk:
                mTypeReceiveSend = TYPE_ALSO_RECEIVE;

                rdAlso.setSelected(true);
                rdOnly.setSelected(false);
                tvAlso.setTextColor(getResources().getColor(R.color.color_Sat));
                tvOnly.setTextColor(getResources().getColor(R.color.text_spiner));
                lnRd1Bulk.setBackgroundColor(getResources().getColor(R.color.bg_rd_bulk));
                lnRd2Bulk.setBackgroundColor(Color.WHITE);

                if (TYPE_ALSO_RECEIVE == mTypeReceive) {
                    mCkickSave = false;
                    btnSave.setTextColor(getResources().getColor(R.color.text_save));
                    btnSave.setBackgroundResource(R.drawable.btn_save);
                } else {
                    mCkickSave = true;
                    btnSave.setTextColor(getResources().getColor(R.color.colorWhite));
                    btnSave.setBackgroundResource(R.drawable.btn_open_again_shop);
                }
                break;

            case R.id.layout_rd2_bulk:
                mTypeReceiveSend = TYPE_ONLY_RECEIVE;

                rdAlso.setSelected(false);
                rdOnly.setSelected(true);
                tvAlso.setTextColor(getResources().getColor(R.color.text_spiner));
                tvOnly.setTextColor(getResources().getColor(R.color.color_Sat));
                lnRd2Bulk.setBackgroundColor(getResources().getColor(R.color.bg_rd_bulk));
                lnRd1Bulk.setBackgroundColor(Color.WHITE);

                if (TYPE_ONLY_RECEIVE == mTypeReceive) {
                    mCkickSave = false;
                    btnSave.setTextColor(getResources().getColor(R.color.text_save));
                    btnSave.setBackgroundResource(R.drawable.btn_save);
                } else {
                    mCkickSave = true;
                    btnSave.setTextColor(getResources().getColor(R.color.colorWhite));
                    btnSave.setBackgroundResource(R.drawable.btn_open_again_shop);
                }
                break;

            case R.id.btn_save_update_setting:
                if (mCkickSave) {
                    postBulkOrderSetting();
                }
                break;
        }
    }

    private void getBulkOrderSetting() {
        Controller controller = new Controller();
        Call<BulkOrderSettingResponse> call = controller.service.getBulkOrderSetting(Config.AdminId,
                Config.apiToken, Config.is_groceryAdmin);
        call.enqueue(new Callback<BulkOrderSettingResponse>() {
            @Override
            public void onResponse(Response<BulkOrderSettingResponse> response, Retrofit retrofit) {
                if (response != null) {
                    BulkOrderSettingResponse bulkOrderSettingResponse = response.body();
                    if (bulkOrderSettingResponse != null) {
                        if (bulkOrderSettingResponse.code == 200) {
                            mTypeReceive = bulkOrderSettingResponse.result.getSettingType();

                            if (bulkOrderSettingResponse.result.getSettingType() == TYPE_ALSO_RECEIVE) {
                                rdAlso.setSelected(true);
                                tvAlso.setTextColor(getResources().getColor(R.color.color_Sat));
                                lnRd1Bulk.setBackgroundColor(getResources().getColor(R.color.bg_rd_bulk));

                                rdOnly.setSelected(false);
                                tvOnly.setTextColor(getResources().getColor(R.color.text_spiner));
                                lnRd2Bulk.setBackgroundColor(getResources().getColor(R.color.colorWhite));
                            } else {
                                rdOnly.setSelected(true);
                                tvOnly.setTextColor(getResources().getColor(R.color.color_Sat));
                                lnRd2Bulk.setBackgroundColor(getResources().getColor(R.color.bg_rd_bulk));

                                rdAlso.setSelected(false);
                                tvAlso.setTextColor(getResources().getColor(R.color.text_spiner));
                                lnRd1Bulk.setBackgroundColor(getResources().getColor(R.color.colorWhite));
                            }
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

    private void postBulkOrderSetting() {
        BulkOrderSettingRequest bulkOrderSettingRequest = new BulkOrderSettingRequest(Config.AdminId,
                Config.apiToken, mTypeReceiveSend, Config.is_groceryAdmin);
        Controller controller = new Controller();
        Call<BaseResponse> call = controller.service.updateBulkOrderSetting(bulkOrderSettingRequest);
        call.enqueue(new Callback<BaseResponse>() {
            @Override
            public void onResponse(Response<BaseResponse> response, Retrofit retrofit) {
                if (response != null) {
                    BaseResponse baseResponse = response.body();
                    if (baseResponse.code == 200) {
                        Toast.makeText(getActivity(), baseResponse.message, Toast.LENGTH_SHORT).show();
                        mCkickSave = false;
                        btnSave.setTextColor(getResources().getColor(R.color.text_save));
                        btnSave.setBackgroundResource(R.drawable.btn_save);
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
