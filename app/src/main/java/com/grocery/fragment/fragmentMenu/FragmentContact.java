package com.grocery.fragment.fragmentMenu;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListPopupWindow;
import android.widget.Spinner;

import com.grocery.R;
import com.grocery.common.CustomTextView;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by ThanhBeo on 30/06/2017.
 */

public class FragmentContact extends Fragment implements View.OnClickListener {
    private CustomTextView btnSave;
    private LinearLayout rdChoose;
    private LinearLayout rdSelect;
    private CustomTextView tvChoose;
    private CustomTextView tvSelect;
    private LinearLayout layoutChoose;
    private LinearLayout layoutSelect;
    private Spinner spnSelectCountry;
    private Spinner spnSelectCity;
    private Spinner spnSelectArea;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.menu_contact, container, false);
        initView(view);
        initListener();
        return view;
    }

    private void initListener() {
        layoutChoose.setOnClickListener(this);
        layoutSelect.setOnClickListener(this);
    }

    private void initView(View view) {
        btnSave = (CustomTextView) view.findViewById(R.id.btn_save_contact);
        rdChoose = (LinearLayout) view.findViewById(R.id.rd_contacts_choose);
        rdSelect = (LinearLayout) view.findViewById(R.id.rd_contacts_selects);
        tvChoose = (CustomTextView) view.findViewById(R.id.tv_choose_current);
        tvSelect = (CustomTextView) view.findViewById(R.id.tv_select_on_map);
        rdChoose.setSelected(true);
        tvChoose.setTextColor(getResources().getColor(R.color.color_Sat));
        layoutChoose = (LinearLayout) view.findViewById(R.id.layout_rd_choose);
        layoutSelect = (LinearLayout) view.findViewById(R.id.layout_rd_select);
        spnSelectCountry = (Spinner) view.findViewById(R.id.spn_select_country);
        spnSelectCity = (Spinner) view.findViewById(R.id.spn_select_city);
        spnSelectArea = (Spinner) view.findViewById(R.id.spn_select_area);

        try {
            Field popup = Spinner.class.getDeclaredField("mPopup");
            popup.setAccessible(true);
            // set popup Select Country
            android.widget.ListPopupWindow popupWindow = (android.widget.ListPopupWindow) popup.get(spnSelectCountry);
            popupWindow.setBackgroundDrawable(getResources().getDrawable(R.drawable.radius_edt));
            popupWindow.setHeight(ListPopupWindow.WRAP_CONTENT);

            // set popup Select City
            android.widget.ListPopupWindow popupWindow1 = (android.widget.ListPopupWindow) popup.get(spnSelectCity);
            popupWindow1.setBackgroundDrawable(getResources().getDrawable(R.drawable.radius_edt));
            popupWindow1.setHeight(ListPopupWindow.WRAP_CONTENT);

            // set popup Select Area
            android.widget.ListPopupWindow popupWindow2 = (android.widget.ListPopupWindow) popup.get(spnSelectArea);
            popupWindow2.setBackgroundDrawable(getResources().getDrawable(R.drawable.radius_edt));
            popupWindow2.setHeight(ListPopupWindow.WRAP_CONTENT);
        } catch (NoClassDefFoundError | ClassCastException | NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }

        ArrayAdapter adapterCountry = new ArrayAdapter(getActivity(),
                R.layout.item_spinner, getListStringSelect());
        spnSelectCountry.setAdapter(adapterCountry);

        ArrayAdapter adapterCity = new ArrayAdapter(getActivity(),
                R.layout.item_spinner, getListStringSelect());
        spnSelectCity.setAdapter(adapterCity);

        ArrayAdapter adapterArea = new ArrayAdapter(getActivity(),
                R.layout.item_spinner, getListStringSelect());
        spnSelectArea.setAdapter(adapterArea);

        /*spnSelectCountry.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
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
        switch (v.getId()) {
            case R.id.layout_rd_choose:
                if (rdChoose.isSelected()) {
                    rdChoose.setSelected(false);
                    tvChoose.setTextColor(getResources().getColor(R.color.text_spiner));
                    break;
                }
                rdChoose.setSelected(true);
                rdSelect.setSelected(false);
                tvChoose.setTextColor(getResources().getColor(R.color.color_Sat));
                tvSelect.setTextColor(getResources().getColor(R.color.text_spiner));
                break;
            case R.id.layout_rd_select:
                if (rdSelect.isSelected()) {
                    rdSelect.setSelected(false);
                    tvSelect.setTextColor(getResources().getColor(R.color.text_spiner));
                    break;
                }
                rdChoose.setSelected(false);
                rdSelect.setSelected(true);
                tvChoose.setTextColor(getResources().getColor(R.color.text_spiner));
                tvSelect.setTextColor(getResources().getColor(R.color.color_Sat));
                break;
        }
    }

    private List<String> getListStringSelect() {
        List<String> list = new ArrayList<>();
        list.add("Thai Nguyen");
        list.add("Ha Noi");
        list.add("Da Nang");
        list.add("Hai Phong");
        list.add("Ho Chi Minh");

        return list;
    }
}
