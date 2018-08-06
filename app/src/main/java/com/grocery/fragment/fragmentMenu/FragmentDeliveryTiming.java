package com.grocery.fragment.fragmentMenu;

import android.app.ProgressDialog;
import android.content.Context;
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
import com.grocery.common.DateTimeFormat;
import com.grocery.controller.Controller;
import com.grocery.model.DeliveryTime;
import com.grocery.request.UpdateDeliveryTimeRequest;
import com.grocery.response.BaseResponse;
import com.grocery.response.DeliveryTimeResponse;
import com.wdullaer.materialdatetimepicker.time.TimePickerDialog;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

/**
 * Created by ThanhBeo on 22/06/2017.
 */

public class FragmentDeliveryTiming extends Fragment implements View.OnClickListener {
    private ArrayList<CustomTextView> arrTv;
    private ArrayList<LinearLayout> arrCb;
    private ArrayList<LinearLayout> arrLayoutCb;
    private CustomTextView btnSave;
    private DeliveryTime deliveryTime;
    private CustomTextView tvTimeDelivery;
    private CustomTextView tvTimeMon;
    private CustomTextView tvTimeMon1;
    private CustomTextView tvTimeSun;
    private CustomTextView tvTimeSun1;
    private CustomTextView tvTimeTue;
    private CustomTextView tvTimeTue1;
    private CustomTextView tvTimeWed;
    private CustomTextView tvTimeWed1;
    private CustomTextView tvTimeThus;
    private CustomTextView tvTimeThus1;
    private CustomTextView tvTimeFri;
    private CustomTextView tvTimeFri1;
    private CustomTextView tvTimeSat;
    private CustomTextView tvTimeSat1;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.menu_delivery_timing, container, false);
        initView(view);
        initData();
        initListener(view);
        return view;
    }

    private void initData() {
        getDeliveryTime(getActivity(), Config.AdminId, Config.apiToken, Config.is_groceryAdmin);
    }

    private void initView(View view) {
        btnSave = (CustomTextView) view.findViewById(R.id.btnSaveUpdate);
        tvTimeDelivery = (CustomTextView) view.findViewById(R.id.tvTimeDelivery);
        arrTv = new ArrayList<>();
        arrTv.add((CustomTextView) view.findViewById(R.id.tv5Min));
        arrTv.add((CustomTextView) view.findViewById(R.id.tv10Min));
        arrTv.add((CustomTextView) view.findViewById(R.id.tv15Min));
        arrCb = new ArrayList<>();
        arrCb.add((LinearLayout) view.findViewById(R.id.rd5min));
        arrCb.add((LinearLayout) view.findViewById(R.id.rd10min));
        arrCb.add((LinearLayout) view.findViewById(R.id.rd15min));
        arrLayoutCb = new ArrayList<>();
        arrLayoutCb.add((LinearLayout) view.findViewById(R.id.layout5Min));
        arrLayoutCb.add((LinearLayout) view.findViewById(R.id.layout10Min));
        arrLayoutCb.add((LinearLayout) view.findViewById(R.id.layout15Min));
        tvTimeMon = (CustomTextView) view.findViewById(R.id.tvTimeMon);
        tvTimeMon1 = (CustomTextView) view.findViewById(R.id.tvTimeMon1);
        tvTimeSun = (CustomTextView) view.findViewById(R.id.tvTimeSun);
        tvTimeSun1 = (CustomTextView) view.findViewById(R.id.tvTimeSun1);
        tvTimeTue = (CustomTextView) view.findViewById(R.id.tvTimeTue);
        tvTimeTue1 = (CustomTextView) view.findViewById(R.id.tvTimeTue1);
        tvTimeWed = (CustomTextView) view.findViewById(R.id.tvTimeWed);
        tvTimeWed1 = (CustomTextView) view.findViewById(R.id.tvTimeWed1);
        tvTimeThus = (CustomTextView) view.findViewById(R.id.tvTimeThus);
        tvTimeThus1 = (CustomTextView) view.findViewById(R.id.tvTimeThus1);
        tvTimeFri = (CustomTextView) view.findViewById(R.id.tvTimeFri);
        tvTimeFri1 = (CustomTextView) view.findViewById(R.id.tvTimeFri1);
        tvTimeSat = (CustomTextView) view.findViewById(R.id.tvTimeSat);
        tvTimeSat1 = (CustomTextView) view.findViewById(R.id.tvTimeSat1);
    }

    private void initListener(View view) {

        btnSave.setOnClickListener(this);
        tvTimeDelivery.setOnClickListener(this);
        tvTimeMon.setOnClickListener(this);
        tvTimeMon1.setOnClickListener(this);
        tvTimeSun.setOnClickListener(this);
        tvTimeSun1.setOnClickListener(this);
        tvTimeTue.setOnClickListener(this);
        tvTimeTue1.setOnClickListener(this);
        tvTimeWed.setOnClickListener(this);
        tvTimeWed1.setOnClickListener(this);
        tvTimeThus.setOnClickListener(this);
        tvTimeThus1.setOnClickListener(this);
        tvTimeFri.setOnClickListener(this);
        tvTimeFri1.setOnClickListener(this);
        tvTimeSat.setOnClickListener(this);
        tvTimeSat1.setOnClickListener(this);
        for (int i = 0; i < arrLayoutCb.size(); i++) {
            arrLayoutCb.get(i).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    for (int i = 0; i < arrTv.size(); i++) {
                        arrTv.get(i).setTextColor(getResources().getColor(R.color.text_spiner));
                        arrCb.get(i).setSelected(false);
                    }
                    switch (v.getId()) {
                        case R.id.layout5Min:
                            arrTv.get(0).setTextColor(getResources().getColor(R.color.bg_search_main));
                            arrCb.get(0).setSelected(true);
                            break;
                        case R.id.layout10Min:
                            arrTv.get(1).setTextColor(getResources().getColor(R.color.bg_search_main));
                            arrCb.get(1).setSelected(true);
                            break;
                        case R.id.layout15Min:
                            arrTv.get(2).setTextColor(getResources().getColor(R.color.bg_search_main));
                            arrCb.get(2).setSelected(true);
                            break;
                    }
                }
            });
        }

    }

    public String converMituteToTime(int minute) {
        String timeOut = "";
        if (minute < 60) {
            timeOut = "00:" + minute;
        } else {
            int hour = minute / 60;
            int minute1 = minute % 60;
            timeOut = new DecimalFormat("00").format(hour) + ":" + new DecimalFormat("00").format(minute1);
        }
        return timeOut;
    }

    public String[] sliptTimeDay(String time) {
        String[] arr = time.split("-");
        return arr;
    }

    public int getDeliveryTiming(String time) {
        String[] arr = time.split(":");
        int minute = 0;
        minute = Integer.parseInt(arr[0].toString()) * 60 + Integer.parseInt(arr[1].toString());
        return minute;
    }

    public int getDespatchTiming() {
        int positions = 0;
        for (int i = 0; i < arrCb.size(); i++) {
            if (arrCb.get(i).isSelected()) {
                positions = i;
            }
        }
        if (positions == 0) {
            return 5;
        } else if (positions == 1) {
            return 10;
        } else {
            return 15;
        }
    }

    public String getTimeForPush(CustomTextView tv, CustomTextView tv1) {
        String s = "";
        s = DateTimeFormat.formatTime8(tv.getText().toString()) + "-" +
                DateTimeFormat.formatTime8(tv1.getText().toString());
        return s;
    }

    @Override
    public void onClick(View v) {
        final Calendar now1 = Calendar.getInstance();
        TimePickerDialog timePickerDialog;
        switch (v.getId()) {
            case R.id.btnSaveUpdate:
                if (!checkTimeDayForPut()) {
                    return;
                }
                UpdateDeliveryTimeRequest updateDeliveryTimeRequest = new UpdateDeliveryTimeRequest(Config.AdminId, Config.apiToken,
                        getDeliveryTiming(tvTimeDelivery.getText().toString()), getDespatchTiming(), getTimeForPush(tvTimeMon, tvTimeMon1),
                        getTimeForPush(tvTimeTue, tvTimeTue1), getTimeForPush(tvTimeWed, tvTimeWed1), getTimeForPush(tvTimeThus, tvTimeThus1),
                        getTimeForPush(tvTimeFri, tvTimeFri1), getTimeForPush(tvTimeSat, tvTimeSat1), getTimeForPush(tvTimeSun, tvTimeSun1), Config.is_groceryAdmin);
                updateDeliveryTime(getActivity(), updateDeliveryTimeRequest);
                break;
            case R.id.tvTimeDelivery:
                timePickerDialog = TimePickerDialog.newInstance(
                        new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePickerDialog view, int hourOfDay, int minute, int second) {
                                String time = hourOfDay + ":" + new DecimalFormat("00").format(minute);
                                tvTimeDelivery.setText(time);
                            }

//                            @Override
//                            public void onTimeSet(RadialPickerLayout view, int hourOfDay, int minute) {
//                                String time = hourOfDay + ":" + new DecimalFormat("00").format(minute);
//                                tvTimeDelivery.setText(time);
//                            }
                        },
                        now1.get(Calendar.HOUR_OF_DAY),
                        now1.get(Calendar.MINUTE),
                        true
                );
                timePickerDialog.show(getActivity().getFragmentManager(), "");
                break;
            case R.id.tvTimeMon:
                timePickerDialog = TimePickerDialog.newInstance(
                        new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePickerDialog view, int hourOfDay, int minute, int second) {
                                String time = hourOfDay + ":" + new DecimalFormat("00").format(minute);
                                tvTimeMon.setText(DateTimeFormat.formatTime7(time));
                            }

//                            @Override
//                            public void onTimeSet(RadialPickerLayout view, int hourOfDay, int minute) {
//                                String time = hourOfDay + ":" + new DecimalFormat("00").format(minute);
//                                tvTimeMon.setText(DateTimeFormat.formatTime7(time));
//                            }
                        },
                        now1.get(Calendar.HOUR_OF_DAY),
                        now1.get(Calendar.MINUTE),
                        true
                );
                timePickerDialog.show(getActivity().getFragmentManager(), "");
                break;
            case R.id.tvTimeMon1:
                timePickerDialog = TimePickerDialog.newInstance(
                        new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePickerDialog view, int hourOfDay, int minute, int second) {
                                String time = hourOfDay + ":" + new DecimalFormat("00").format(minute);
                                if (checkDateTime(DateTimeFormat.formatTime7(time), tvTimeMon.getText().toString())) {
                                    tvTimeMon1.setText(DateTimeFormat.formatTime7(time));
                                } else {
                                    Toast.makeText(getActivity(), getContext().getResources().getString(R.string.time_fail), Toast.LENGTH_SHORT).show();
                                    tvTimeMon1.setText(tvTimeMon.getText().toString());
                                }
                            }

//                            @Override
//                            public void onTimeSet(RadialPickerLayout view, int hourOfDay, int minute) {
//                                String time = hourOfDay + ":" + new DecimalFormat("00").format(minute);
//                                if (checkDateTime(DateTimeFormat.formatTime7(time), tvTimeMon.getText().toString())) {
//                                    tvTimeMon1.setText(DateTimeFormat.formatTime7(time));
//                                } else {
//                                    Toast.makeText(getActivity(), getContext().getResources().getString(R.string.time_fail), Toast.LENGTH_SHORT).show();
//                                    tvTimeMon1.setText(tvTimeMon.getText().toString());
//                                }
//                            }
                        },
                        now1.get(Calendar.HOUR_OF_DAY),
                        now1.get(Calendar.MINUTE),
                        true
                );
                timePickerDialog.show(getActivity().getFragmentManager(), "");
                break;
            case R.id.tvTimeSun:
                timePickerDialog = TimePickerDialog.newInstance(
                        new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePickerDialog view, int hourOfDay, int minute, int second) {
                                String time = hourOfDay + ":" + new DecimalFormat("00").format(minute);
                                tvTimeSun.setText(DateTimeFormat.formatTime7(time));
                            }

//                            @Override
//                            public void onTimeSet(RadialPickerLayout view, int hourOfDay, int minute) {
//                                String time = hourOfDay + ":" + new DecimalFormat("00").format(minute);
//                                tvTimeSun.setText(DateTimeFormat.formatTime7(time));
//                            }
                        },
                        now1.get(Calendar.HOUR_OF_DAY),
                        now1.get(Calendar.MINUTE),
                        true
                );
                timePickerDialog.show(getActivity().getFragmentManager(), "");
                break;
            case R.id.tvTimeSun1:
                timePickerDialog = TimePickerDialog.newInstance(
                        new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePickerDialog view, int hourOfDay, int minute, int second) {
                                String time = hourOfDay + ":" + new DecimalFormat("00").format(minute);
                                if (checkDateTime(DateTimeFormat.formatTime7(time), tvTimeSun.getText().toString())) {
                                    tvTimeSun1.setText(DateTimeFormat.formatTime7(time));
                                } else {
                                    Toast.makeText(getActivity(), getContext().getResources().getString(R.string.time_fail), Toast.LENGTH_SHORT).show();
                                    tvTimeSun1.setText(tvTimeSun1.getText().toString());
                                }
                            }

//                            @Override
//                            public void onTimeSet(RadialPickerLayout view, int hourOfDay, int minute) {
//                                String time = hourOfDay + ":" + new DecimalFormat("00").format(minute);
//                                if (checkDateTime(DateTimeFormat.formatTime7(time), tvTimeSun.getText().toString())) {
//                                    tvTimeSun1.setText(DateTimeFormat.formatTime7(time));
//                                } else {
//                                    Toast.makeText(getActivity(), getContext().getResources().getString(R.string.time_fail), Toast.LENGTH_SHORT).show();
//                                    tvTimeSun1.setText(tvTimeSun1.getText().toString());
//                                }
//                            }
                        },
                        now1.get(Calendar.HOUR_OF_DAY),
                        now1.get(Calendar.MINUTE),
                        true
                );
                timePickerDialog.show(getActivity().getFragmentManager(), "");
                break;
            case R.id.tvTimeTue:
                timePickerDialog = TimePickerDialog.newInstance(
                        new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePickerDialog view, int hourOfDay, int minute, int second) {
                                String time = hourOfDay + ":" + new DecimalFormat("00").format(minute);
                                tvTimeTue.setText(DateTimeFormat.formatTime7(time));
                            }

//                            @Override
//                            public void onTimeSet(RadialPickerLayout view, int hourOfDay, int minute) {
//                                String time = hourOfDay + ":" + new DecimalFormat("00").format(minute);
//                                tvTimeTue.setText(DateTimeFormat.formatTime7(time));
//                            }
                        },
                        now1.get(Calendar.HOUR_OF_DAY),
                        now1.get(Calendar.MINUTE),
                        true
                );
                timePickerDialog.show(getActivity().getFragmentManager(), "");
                break;
            case R.id.tvTimeTue1:
                timePickerDialog = TimePickerDialog.newInstance(
                        new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePickerDialog view, int hourOfDay, int minute, int second) {
                                String time = hourOfDay + ":" + new DecimalFormat("00").format(minute);
                                if (checkDateTime(DateTimeFormat.formatTime7(time), tvTimeTue.getText().toString())) {
                                    tvTimeTue1.setText(DateTimeFormat.formatTime7(time));
                                } else {
                                    Toast.makeText(getActivity(), getContext().getResources().getString(R.string.time_fail), Toast.LENGTH_SHORT).show();
                                    tvTimeTue1.setText(tvTimeTue.getText().toString());
                                }
                            }

//                            @Override
//                            public void onTimeSet(RadialPickerLayout view, int hourOfDay, int minute) {
//                                String time = hourOfDay + ":" + new DecimalFormat("00").format(minute);
//                                if (checkDateTime(DateTimeFormat.formatTime7(time), tvTimeTue.getText().toString())) {
//                                    tvTimeTue1.setText(DateTimeFormat.formatTime7(time));
//                                } else {
//                                    Toast.makeText(getActivity(), getContext().getResources().getString(R.string.time_fail), Toast.LENGTH_SHORT).show();
//                                    tvTimeTue1.setText(tvTimeTue.getText().toString());
//                                }
//                            }
                        },
                        now1.get(Calendar.HOUR_OF_DAY),
                        now1.get(Calendar.MINUTE),
                        true
                );
                timePickerDialog.show(getActivity().getFragmentManager(), "");
                break;
            case R.id.tvTimeWed:
                timePickerDialog = TimePickerDialog.newInstance(
                        new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePickerDialog view, int hourOfDay, int minute, int second) {
                                String time = hourOfDay + ":" + new DecimalFormat("00").format(minute);
                                tvTimeWed.setText(DateTimeFormat.formatTime7(time));
                            }

//                            @Override
//                            public void onTimeSet(RadialPickerLayout view, int hourOfDay, int minute) {
//                                String time = hourOfDay + ":" + new DecimalFormat("00").format(minute);
//                                tvTimeWed.setText(DateTimeFormat.formatTime7(time));
//                            }
                        },
                        now1.get(Calendar.HOUR_OF_DAY),
                        now1.get(Calendar.MINUTE),
                        true
                );
                timePickerDialog.show(getActivity().getFragmentManager(), "");
                break;
            case R.id.tvTimeWed1:
                timePickerDialog = TimePickerDialog.newInstance(
                        new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePickerDialog view, int hourOfDay, int minute, int second) {
                                String time = hourOfDay + ":" + new DecimalFormat("00").format(minute);
                                if (checkDateTime(DateTimeFormat.formatTime7(time), tvTimeWed.getText().toString())) {
                                    tvTimeWed1.setText(DateTimeFormat.formatTime7(time));
                                } else {
                                    Toast.makeText(getActivity(), getContext().getResources().getString(R.string.time_fail), Toast.LENGTH_SHORT).show();
                                    tvTimeWed1.setText(tvTimeWed.getText().toString());
                                }
                            }

//                            @Override
//                            public void onTimeSet(RadialPickerLayout view, int hourOfDay, int minute) {
//                                String time = hourOfDay + ":" + new DecimalFormat("00").format(minute);
//                                if (checkDateTime(DateTimeFormat.formatTime7(time), tvTimeWed.getText().toString())) {
//                                    tvTimeWed1.setText(DateTimeFormat.formatTime7(time));
//                                } else {
//                                    Toast.makeText(getActivity(), getContext().getResources().getString(R.string.time_fail), Toast.LENGTH_SHORT).show();
//                                    tvTimeWed1.setText(tvTimeWed.getText().toString());
//                                }
//                            }
                        },
                        now1.get(Calendar.HOUR_OF_DAY),
                        now1.get(Calendar.MINUTE),
                        true
                );
                timePickerDialog.show(getActivity().getFragmentManager(), "");
                break;
            case R.id.tvTimeThus:
                timePickerDialog = TimePickerDialog.newInstance(
                        new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePickerDialog view, int hourOfDay, int minute, int second) {
                                String time = hourOfDay + ":" + new DecimalFormat("00").format(minute);
                                tvTimeThus.setText(DateTimeFormat.formatTime7(time));
                            }

//                            @Override
//                            public void onTimeSet(RadialPickerLayout view, int hourOfDay, int minute) {
//                                String time = hourOfDay + ":" + new DecimalFormat("00").format(minute);
//                                tvTimeThus.setText(DateTimeFormat.formatTime7(time));
//                            }
                        },
                        now1.get(Calendar.HOUR_OF_DAY),
                        now1.get(Calendar.MINUTE),
                        true
                );
                timePickerDialog.show(getActivity().getFragmentManager(), "");
                break;
            case R.id.tvTimeThus1:
                timePickerDialog = TimePickerDialog.newInstance(
                        new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePickerDialog view, int hourOfDay, int minute, int second) {
                                String time = hourOfDay + ":" + new DecimalFormat("00").format(minute);
                                if (checkDateTime(DateTimeFormat.formatTime7(time), tvTimeThus.getText().toString())) {
                                    tvTimeThus1.setText(DateTimeFormat.formatTime7(time));
                                } else {
                                    Toast.makeText(getActivity(), getContext().getResources().getString(R.string.time_fail), Toast.LENGTH_SHORT).show();
                                    tvTimeThus1.setText(tvTimeThus1.getText().toString());
                                }
                            }

//                            @Override
//                            public void onTimeSet(RadialPickerLayout view, int hourOfDay, int minute) {
//                                String time = hourOfDay + ":" + new DecimalFormat("00").format(minute);
//                                if (checkDateTime(DateTimeFormat.formatTime7(time), tvTimeThus.getText().toString())) {
//                                    tvTimeThus1.setText(DateTimeFormat.formatTime7(time));
//                                } else {
//                                    Toast.makeText(getActivity(), getContext().getResources().getString(R.string.time_fail), Toast.LENGTH_SHORT).show();
//                                    tvTimeThus1.setText(tvTimeThus1.getText().toString());
//                                }
//                            }
                        },
                        now1.get(Calendar.HOUR_OF_DAY),
                        now1.get(Calendar.MINUTE),
                        true
                );
                timePickerDialog.show(getActivity().getFragmentManager(), "");
                break;
            case R.id.tvTimeFri:
                timePickerDialog = TimePickerDialog.newInstance(
                        new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePickerDialog view, int hourOfDay, int minute, int second) {
                                String time = hourOfDay + ":" + new DecimalFormat("00").format(minute);
                                tvTimeFri.setText(DateTimeFormat.formatTime7(time));
                            }

//                            @Override
//                            public void onTimeSet(RadialPickerLayout view, int hourOfDay, int minute) {
//                                String time = hourOfDay + ":" + new DecimalFormat("00").format(minute);
//                                tvTimeFri.setText(DateTimeFormat.formatTime7(time));
//                            }
                        },
                        now1.get(Calendar.HOUR_OF_DAY),
                        now1.get(Calendar.MINUTE),
                        true
                );
                timePickerDialog.show(getActivity().getFragmentManager(), "");
                break;
            case R.id.tvTimeFri1:
                timePickerDialog = TimePickerDialog.newInstance(
                        new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePickerDialog view, int hourOfDay, int minute, int second) {
                                String time = hourOfDay + ":" + new DecimalFormat("00").format(minute);
                                if (checkDateTime(DateTimeFormat.formatTime7(time), tvTimeFri.getText().toString())) {
                                    tvTimeFri1.setText(DateTimeFormat.formatTime7(time));
                                } else {
                                    Toast.makeText(getActivity(), getContext().getResources().getString(R.string.time_fail), Toast.LENGTH_SHORT).show();
                                    tvTimeFri1.setText(tvTimeFri.getText().toString());
                                }
                            }

//                            @Override
//                            public void onTimeSet(RadialPickerLayout view, int hourOfDay, int minute) {
//                                String time = hourOfDay + ":" + new DecimalFormat("00").format(minute);
//                                if (checkDateTime(DateTimeFormat.formatTime7(time), tvTimeFri.getText().toString())) {
//                                    tvTimeFri1.setText(DateTimeFormat.formatTime7(time));
//                                } else {
//                                    Toast.makeText(getActivity(), getContext().getResources().getString(R.string.time_fail), Toast.LENGTH_SHORT).show();
//                                    tvTimeFri1.setText(tvTimeFri.getText().toString());
//                                }
//                            }
                        },
                        now1.get(Calendar.HOUR_OF_DAY),
                        now1.get(Calendar.MINUTE),
                        true
                );
                timePickerDialog.show(getActivity().getFragmentManager(), "");
                break;
            case R.id.tvTimeSat:
                timePickerDialog = TimePickerDialog.newInstance(
                        new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePickerDialog view, int hourOfDay, int minute, int second) {
                                String time = hourOfDay + ":" + new DecimalFormat("00").format(minute);
                                tvTimeSat.setText(DateTimeFormat.formatTime7(time));
                            }

//                            @Override
//                            public void onTimeSet(RadialPickerLayout view, int hourOfDay, int minute) {
//                                String time = hourOfDay + ":" + new DecimalFormat("00").format(minute);
//                                tvTimeSat.setText(DateTimeFormat.formatTime7(time));
//                            }
                        },
                        now1.get(Calendar.HOUR_OF_DAY),
                        now1.get(Calendar.MINUTE),
                        true
                );
                timePickerDialog.show(getActivity().getFragmentManager(), "");
                break;
            case R.id.tvTimeSat1:
                timePickerDialog = TimePickerDialog.newInstance(
                        new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePickerDialog view, int hourOfDay, int minute, int second) {
                                String time = hourOfDay + ":" + new DecimalFormat("00").format(minute);
                                if (checkDateTime(DateTimeFormat.formatTime7(time), tvTimeSat.getText().toString())) {
                                    tvTimeSat1.setText(DateTimeFormat.formatTime7(time));
                                } else {
                                    Toast.makeText(getActivity(), getContext().getResources().getString(R.string.time_fail), Toast.LENGTH_SHORT).show();
                                    tvTimeSat1.setText(tvTimeSat.getText().toString());
                                }
                            }

//                            @Override
//                            public void onTimeSet(RadialPickerLayout view, int hourOfDay, int minute) {
//                                String time = hourOfDay + ":" + new DecimalFormat("00").format(minute);
//                                if (checkDateTime(DateTimeFormat.formatTime7(time), tvTimeSat.getText().toString())) {
//                                    tvTimeSat1.setText(DateTimeFormat.formatTime7(time));
//                                } else {
//                                    Toast.makeText(getActivity(), getContext().getResources().getString(R.string.time_fail), Toast.LENGTH_SHORT).show();
//                                    tvTimeSat1.setText(tvTimeSat.getText().toString());
//                                }
//                            }
                        },
                        now1.get(Calendar.HOUR_OF_DAY),
                        now1.get(Calendar.MINUTE),
                        true
                );
                timePickerDialog.show(getActivity().getFragmentManager(), "");
                break;
        }
    }

    public boolean checkTimeDayForPut() {
        if (!checkDateTime(tvTimeMon1.getText().toString(), tvTimeMon.getText().toString())) {
            Toast.makeText(getActivity(), getResources().getString(R.string.time_Mon_fail), Toast.LENGTH_SHORT).show();
            return false;
        }
        if (!checkDateTime(tvTimeTue1.getText().toString(), tvTimeTue.getText().toString())) {
            Toast.makeText(getActivity(), getResources().getString(R.string.time_Tue_fail), Toast.LENGTH_SHORT).show();
            return false;
        }
        if (!checkDateTime(tvTimeWed1.getText().toString(), tvTimeWed.getText().toString())) {
            Toast.makeText(getActivity(), getResources().getString(R.string.time_Wed_fail), Toast.LENGTH_SHORT).show();
            return false;
        }
        if (!checkDateTime(tvTimeThus1.getText().toString(), tvTimeThus.getText().toString())) {
            Toast.makeText(getActivity(), getResources().getString(R.string.time_Thus_fail), Toast.LENGTH_SHORT).show();
            return false;
        }
        if (!checkDateTime(tvTimeFri1.getText().toString(), tvTimeFri.getText().toString())) {
            Toast.makeText(getActivity(), getResources().getString(R.string.time_Fri_fail), Toast.LENGTH_SHORT).show();
            return false;
        }
        if (!checkDateTime(tvTimeSat1.getText().toString(), tvTimeSat.getText().toString())) {
            Toast.makeText(getActivity(), getResources().getString(R.string.time_Sat_fail), Toast.LENGTH_SHORT).show();
            return false;
        }
        if (!checkDateTime(tvTimeSun1.getText().toString(), tvTimeSun.getText().toString())) {
            Toast.makeText(getActivity(), getResources().getString(R.string.time_Sun_fail), Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    public boolean checkDateTime(String timePush, String tvTime) {
        SimpleDateFormat df1 = new SimpleDateFormat("HH:mm");
        Date d1 = null;
        try {
            d1 = df1.parse(DateTimeFormat.formatTime8(timePush.toString()));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        Date d2 = null;
        String timeSelect = DateTimeFormat.formatTime5(tvTime.toString());
        try {
            d2 = df1.parse(timeSelect.toString());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        if (d1 != null && d2 != null) {
            if (d1.compareTo(d2) < 0) {
                return false;
            }
        }
        return true;
    }

    private void getDeliveryTime(Context context, int userID, String apiToken, int is_groceryAdmin) {
        final ProgressDialog dialog = new ProgressDialog(context);
        dialog.setMessage("Loading....");
        dialog.setCancelable(false);
        dialog.show();

        Controller controller = new Controller();
        Call<DeliveryTimeResponse> call = controller.service.getDeliveryTime(userID, apiToken, is_groceryAdmin);
        call.enqueue(new Callback<DeliveryTimeResponse>() {
            @Override
            public void onResponse(Response<DeliveryTimeResponse> response, Retrofit retrofit) {
                dialog.dismiss();
                if (response != null) {
                    DeliveryTimeResponse deliveryTimeResponse = response.body();
                    if (deliveryTimeResponse != null) {
                        if (deliveryTimeResponse.code == 200) {
                            deliveryTime = deliveryTimeResponse.result;
                            tvTimeDelivery.setText(converMituteToTime(deliveryTime.getDelivery_timing()));
                            switch (deliveryTime.getDespatching_timing()) {
                                case 5:
                                    arrTv.get(0).setTextColor(getResources().getColor(R.color.bg_search_main));
                                    arrCb.get(0).setSelected(true);
                                    break;
                                case 10:
                                    arrTv.get(1).setTextColor(getResources().getColor(R.color.bg_search_main));
                                    arrCb.get(1).setSelected(true);
                                    break;
                                case 15:
                                    arrTv.get(2).setTextColor(getResources().getColor(R.color.bg_search_main));
                                    arrCb.get(2).setSelected(true);
                                    break;
                            }
                            tvTimeMon.setText(DateTimeFormat.formatTime7(sliptTimeDay(deliveryTime.getMonday())[0]));
                            tvTimeMon1.setText(DateTimeFormat.formatTime7(sliptTimeDay(deliveryTime.getMonday())[1]));
                            tvTimeSun.setText(DateTimeFormat.formatTime7(sliptTimeDay(deliveryTime.getSunday())[0]));
                            tvTimeSun1.setText(DateTimeFormat.formatTime7(sliptTimeDay(deliveryTime.getSunday())[1]));
                            tvTimeTue.setText(DateTimeFormat.formatTime7(sliptTimeDay(deliveryTime.getTuesday())[0]));
                            tvTimeTue1.setText(DateTimeFormat.formatTime7(sliptTimeDay(deliveryTime.getTuesday())[1]));
                            tvTimeWed.setText(DateTimeFormat.formatTime7(sliptTimeDay(deliveryTime.getWednesday())[0]));
                            tvTimeWed1.setText(DateTimeFormat.formatTime7(sliptTimeDay(deliveryTime.getWednesday())[1]));
                            tvTimeThus.setText(DateTimeFormat.formatTime7(sliptTimeDay(deliveryTime.getThursday())[0]));
                            tvTimeThus1.setText(DateTimeFormat.formatTime7(sliptTimeDay(deliveryTime.getThursday())[1]));
                            tvTimeFri.setText(DateTimeFormat.formatTime7(sliptTimeDay(deliveryTime.getFriday())[0]));
                            tvTimeFri1.setText(DateTimeFormat.formatTime7(sliptTimeDay(deliveryTime.getFriday())[1]));
                            tvTimeSat.setText(DateTimeFormat.formatTime7(sliptTimeDay(deliveryTime.getSaturday())[0]));
                            tvTimeSat1.setText(DateTimeFormat.formatTime7(sliptTimeDay(deliveryTime.getSaturday())[1]));
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

    private void updateDeliveryTime(final Context context, UpdateDeliveryTimeRequest updateDeliveryTimeRequest) {
        final ProgressDialog dialog = new ProgressDialog(context);
        dialog.setMessage("Waiting....");
        dialog.setCancelable(false);
        dialog.show();

        Controller controller = new Controller();
        Call<BaseResponse> call = controller.service.updateDelieveryTime(updateDeliveryTimeRequest);
        call.enqueue(new Callback<BaseResponse>() {
            @Override
            public void onResponse(Response<BaseResponse> response, Retrofit retrofit) {
                dialog.dismiss();
                if (response != null) {
                    BaseResponse baseResponse = response.body();
                    if (baseResponse != null && baseResponse.code == 200) {
                        Toast.makeText(context, "Update successful", Toast.LENGTH_SHORT).show();
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
