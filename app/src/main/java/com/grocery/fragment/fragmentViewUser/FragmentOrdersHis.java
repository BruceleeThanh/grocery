package com.grocery.fragment.fragmentViewUser;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.grocery.R;
import com.grocery.activity.MainViewUser;
import com.grocery.adapter.OrderHistoryAdapter;
import com.grocery.common.CRecyclerView;
import com.grocery.common.Config;
import com.grocery.common.CustomTextView;
import com.grocery.common.DateTimeFormat;
import com.grocery.controller.Controller;
import com.grocery.model.ItemOrders;
import com.grocery.model.UserModel;
import com.grocery.response.UserOrderResponse;
import com.grocery.utils.ProgerssBarUntil;
import com.grocery.utils.Utils;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

/**
 * Created by ThanhBeo on 20/07/2017.
 */

public class FragmentOrdersHis extends Fragment implements View.OnClickListener {

    private OrderHistoryAdapter orderHistoryAdapter;
    private CRecyclerView lvOrder;
    private ProgerssBarUntil progerssBarUntil;
    private SwipeRefreshLayout swipeRefreshLayout;
    private boolean isCanNext = true;
    private boolean isProgessingLoadMore = true;
    private int page_number = 0;
    private MainViewUser mainViewUser;
    private UserModel userModel;
    private CustomTextView tvFromDate;
    private CustomTextView tvToDate;
    private ArrayList<String> arrDateStartChoose = new ArrayList<>();
    private String dateStart;
    private String dateEnd;
    private Date start;
    private Date end;
    private LinearLayout btnSearch;
    private ImageView btnDown1;
    private ImageView btnDown2;
    private ImageView btnDown3;
    private ImageView btnUp1;
    private ImageView btnUp2;
    private ImageView btnUp3;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.user_order_his, container, false);
        progerssBarUntil = new ProgerssBarUntil(view);
        mainViewUser = (MainViewUser) getActivity();
        initView(view);
        initData();
        initListener();
        return view;
    }

    private void initListener() {
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipeRefreshLayout.setRefreshing(false);
                cleanAdapter();
                loadMore();
            }
        });

        lvOrder.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (Utils.isReadyForPullEnd(recyclerView) && isCanNext && !isProgessingLoadMore) {
                    loadMore();
                }
            }
        });
        tvFromDate.setOnClickListener(this);
        tvToDate.setOnClickListener(this);
        btnSearch.setOnClickListener(this);
        btnDown1.setOnClickListener(this);
        btnDown2.setOnClickListener(this);
        btnDown3.setOnClickListener(this);
        btnUp1.setOnClickListener(this);
        btnUp2.setOnClickListener(this);
        btnUp3.setOnClickListener(this);
    }

    private void loadMore() {
        getOrderHistory(Config.AdminId, Config.apiToken, userModel.getUser_id(),
                DateTimeFormat.formatDateTime1(dateStart.toString()),
                DateTimeFormat.formatDateTime1(dateEnd.toString()),
                Config.PAGE_SIZE, ++page_number, Config.is_groceryAdmin);
    }

    private void cleanAdapter() {
        /*Date date = new Date();
        dateStart = new SimpleDateFormat("dd-MMM-yyyy", Locale.ENGLISH).format(date);
        tvFromDate.setText(dateStart);
        start = date;
        dateEnd = new SimpleDateFormat("dd-MMM-yyyy", Locale.ENGLISH).format(date);
        tvToDate.setText(dateEnd);*/
        page_number = 0;
        orderHistoryAdapter.clear();
    }

    private void initData() {
        userModel = mainViewUser.getUserModel();
        Date date1 = new Date();
        dateStart = new SimpleDateFormat("dd-MMM-yyyy", Locale.ENGLISH).format(date1);
        tvFromDate.setText(dateStart);
        arrDateStartChoose = converDate(tvFromDate.getText().toString());
        start = date1;

        Date date2 = new Date();
        dateEnd = new SimpleDateFormat("dd-MMM-yyyy", Locale.ENGLISH).format(date2);
        tvToDate.setText(dateEnd);
        end = date2;
        loadMore();
    }

    private void initView(View view) {
        lvOrder = (CRecyclerView) view.findViewById(R.id.lv_order_history);
        orderHistoryAdapter = new OrderHistoryAdapter(getActivity(), new ArrayList<ItemOrders>());
        lvOrder.setAdapter(orderHistoryAdapter);
        swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swiperefresh);
        tvFromDate = (CustomTextView) view.findViewById(R.id.tvFromDate);
        tvToDate = (CustomTextView) view.findViewById(R.id.tvToDate);
        btnSearch = (LinearLayout) view.findViewById(R.id.btnSearch);
        btnDown1 = view.findViewById(R.id.btnDown1);
        btnDown2 = view.findViewById(R.id.btnDown2);
        btnDown3 = view.findViewById(R.id.btnDown3);
        btnUp1 = view.findViewById(R.id.btnUp1);
        btnUp2 = view.findViewById(R.id.btnUp2);
        btnUp3 = view.findViewById(R.id.btnUp3);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tvFromDate:
                final Calendar now = Calendar.getInstance();
                DatePickerDialog datePickerDialog = DatePickerDialog.newInstance(
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
                                String datePick = year + "-" + new DecimalFormat("00").format(monthOfYear + 1) + "-" + dayOfMonth;
                                dateStart = DateTimeFormat.formatTime3(datePick);
                                tvFromDate.setText(dateStart);
                                arrDateStartChoose = converDate(tvFromDate.getText().toString());
                                start = new Date();
                                start.setMonth(monthOfYear);
                                start.setDate(dayOfMonth);
                                start.setYear(year);
                            }
                        },
                        now.get(Calendar.YEAR),
                        now.get(Calendar.MONTH),
                        now.get(Calendar.DAY_OF_MONTH)
                );
                datePickerDialog.show(mainViewUser.getFragmentManager(), "");
                break;
            case R.id.tvToDate:
                final Calendar now1 = Calendar.getInstance();
                DatePickerDialog datePickerDialog1 = DatePickerDialog.newInstance(
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
                                String datePick = year + "-" + new DecimalFormat("00").format(monthOfYear + 1) + "-" + dayOfMonth;
                                dateEnd = DateTimeFormat.formatTime3(datePick);
                                tvToDate.setText(dateEnd);
                                end = new Date();
                                end.setMonth(monthOfYear);
                                end.setDate(dayOfMonth);
                                end.setYear(year);
                            }
                        },
                        now1.get(Calendar.YEAR),
                        now1.get(Calendar.MONTH),
                        now1.get(Calendar.DAY_OF_MONTH)
                );
                now1.set(Calendar.DAY_OF_MONTH, Integer.parseInt(arrDateStartChoose.get(0)));
                now1.set(Calendar.MONTH, Integer.parseInt(DateTimeFormat.formatDate3(arrDateStartChoose.get(1))) - 1);
                now1.set(Calendar.YEAR, Integer.parseInt(arrDateStartChoose.get(2)));
                datePickerDialog1.setMinDate(now1);
                datePickerDialog1.show(mainViewUser.getFragmentManager(), "");
                break;
            case R.id.btnSearch:
                if (convertDateTimeToTimeStamp(tvFromDate.getText().toString()) > convertDateTimeToTimeStamp(tvToDate.getText().toString())) {
                    Toast.makeText(getContext(), getContext().getResources().getString(R.string.compareto_date), Toast.LENGTH_SHORT).show();
                    return;
                }
                loadMore();
                break;
            case R.id.btnDown1:
                lvOrder.smoothScrollBy(0, Config.scrollItem);
                break;
            case R.id.btnDown2:
                lvOrder.smoothScrollBy(0, 2 * Config.scrollItem);
                break;
            case R.id.btnDown3:
                lvOrder.smoothScrollBy(0, 3 * Config.scrollItem);
                break;
            case R.id.btnUp1:
                lvOrder.smoothScrollBy(0, -Config.scrollItem);
                break;
            case R.id.btnUp2:
                lvOrder.smoothScrollBy(0, -2 * Config.scrollItem);
                break;
            case R.id.btnUp3:
                lvOrder.smoothScrollBy(0, -3 * Config.scrollItem);
                break;
        }
    }

    public ArrayList<String> converDate(String inputDate) {
        ArrayList<String> outPut = new ArrayList<>();
        String[] arr;
        arr = inputDate.split("-");
        for (int i = 0; i < arr.length; i++) {
            outPut.add(arr[i]);
        }
        return outPut;
    }

    private void getOrderHistory(int userID, String apiToken, int user_id, String start_date,
                                 String end_date, final int page_size, int page_number, int is_groceryAdmin) {
        progerssBarUntil.setInvisibeLayoutMain();

        Controller controller = new Controller();
        Call<UserOrderResponse> call = controller.service.getOrderHistory(userID, apiToken, user_id,
                start_date, end_date, page_size, page_number, is_groceryAdmin);
        call.enqueue(new Callback<UserOrderResponse>() {
            @Override
            public void onResponse(Response<UserOrderResponse> response, Retrofit retrofit) {
                isProgessingLoadMore = false;
                progerssBarUntil.setVisibeLayoutMain();
                if (response != null) {
                    UserOrderResponse userOrderResponse = response.body();
                    if (userOrderResponse != null) {
                        if (userOrderResponse.code == 200) {
                            if (userOrderResponse.result.size() == page_size) {
                                isCanNext = true;
                            } else {
                                isCanNext = false;
                            }
                            orderHistoryAdapter.addAll(userOrderResponse.result);
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

    public long convertDateTimeToTimeStamp(String strDate) {
        long timestamp = 0;
        if (strDate != null) {
            try {
                SimpleDateFormat format = new SimpleDateFormat("dd-MMM-yyyy");
                Date date = format.parse(strDate);
                timestamp = date.getTime() / 1000;
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        return timestamp;
    }
}
