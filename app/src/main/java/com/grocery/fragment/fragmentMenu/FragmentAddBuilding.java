package com.grocery.fragment.fragmentMenu;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListPopupWindow;
import android.widget.Spinner;
import android.widget.Toast;

import com.grocery.R;
import com.grocery.adapter.BuildingAreaAdapter;
import com.grocery.common.CRecyclerView;
import com.grocery.common.Config;
import com.grocery.common.CustomTextView;
import com.grocery.controller.Controller;
import com.grocery.model.ItemArea;
import com.grocery.model.ItemBuilding;
import com.grocery.model.ItemBuilding2;
import com.grocery.request.AddBuildingAreaRequest;
import com.grocery.response.BaseResponse;
import com.grocery.response.GetAreaResponse;
import com.grocery.response.GetBuildingResponse;
import com.grocery.utils.ProgerssBarUntil;

import java.lang.reflect.Field;
import java.util.ArrayList;

import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

/**
 * Created by ThanhBeo on 22/06/2017.
 */

public class FragmentAddBuilding extends Fragment {
    private CRecyclerView lvListBuildingArea;
    private BuildingAreaAdapter buildingAreaAdapter;
    private ProgerssBarUntil progerssBarUntil;
    private SwipeRefreshLayout swipeRefreshLayout;
    private ArrayList<ItemArea> arrArea;
    private ArrayList<String> arrNameArea;
    private ArrayList<String> arrNameBuilding;
    private Spinner spnArea;
    private Spinner spnBuilding;
    private CustomTextView btnAddToList;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.menu_building_area, container, false);
        progerssBarUntil = new ProgerssBarUntil(view);
        initView(view);
        initData();
        initLisetener();
        return view;
    }

    private void initData() {
        arrArea = new ArrayList<>();
        arrNameArea = new ArrayList<>();
        arrNameBuilding = new ArrayList<>();
        getArea(Config.city_id);
    }

    private void initLisetener() {
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipeRefreshLayout.setRefreshing(false);
                cleanAdapter();
                loadMore();
                spnArea.setSelection(0);
            }
        });

        spnArea.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                arrNameBuilding = new ArrayList<String>();
                arrNameBuilding.addAll(getListNameBuilding(position));
                ArrayAdapter adapter1 = new ArrayAdapter(getActivity(),
                        R.layout.item_spinner, arrNameBuilding);
                spnBuilding.setAdapter(adapter1);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        btnAddToList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkOk()) {
                    AddBuildingAreaRequest addBuildingAreaRequest = new AddBuildingAreaRequest(Config.AdminId, Config.apiToken,
                            getBuildingId(), Config.is_groceryAdmin);
                    addBuilding(getActivity(), addBuildingAreaRequest);
                }
            }
        });
    }

    public int getBuildingId() {
        String area = spnArea.getSelectedItem().toString();
        String building = spnBuilding.getSelectedItem().toString();
        for (int i = 0; i < arrArea.size(); i++) {
            if (arrArea.get(i).getName().equals(area)) {
                for (int j = 0; j < arrArea.get(i).getBuildings().size(); j++) {
                    if (arrArea.get(i).getBuildings().get(j).getName().equals(building)) {
                        return arrArea.get(i).getBuildings().get(j).getId();
                    }
                }
            }
        }
        return -1;
    }

    public boolean checkOk() {
        String area = spnArea.getSelectedItem().toString();
        String building = spnBuilding.getSelectedItem().toString();
        for (int i = 0; i < buildingAreaAdapter.getList().size(); i++) {
            if (buildingAreaAdapter.getList().get(i).getArea_name().equals(area) &&
                    buildingAreaAdapter.getList().get(i).getBuilding_name().equals(building)) {
                Toast.makeText(getActivity(), getResources().getString(R.string.buildingAdded), Toast.LENGTH_SHORT).show();
                return false;
            }
        }
        return true;
    }

    public void cleanAdapter() {
        buildingAreaAdapter.clear();
    }

    public void loadMore() {
        getBuilding(Config.AdminId, Config.apiToken, Config.is_groceryAdmin);
    }


    private void initView(View view) {
        btnAddToList = (CustomTextView) view.findViewById(R.id.btnAddToList);
        swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swiperefresh);
        spnBuilding = (Spinner) view.findViewById(R.id.spn_building);
        spnArea = (Spinner) view.findViewById(R.id.spn_Area);
        try {
            Field popup = Spinner.class.getDeclaredField("mPopup");
            popup.setAccessible(true);

            // Get private mPopup member variable and try cast to ListPopupWindow
            android.widget.ListPopupWindow popupWindow = (android.widget.ListPopupWindow) popup.get(spnBuilding);
            android.widget.ListPopupWindow popupWindow1 = (android.widget.ListPopupWindow) popup.get(spnArea);

            popupWindow.setBackgroundDrawable(getResources().getDrawable(R.drawable.radius_edt));
            popupWindow1.setBackgroundDrawable(getResources().getDrawable(R.drawable.radius_edt));
            // Set popupWindow height to 500px
            popupWindow.setHeight(ListPopupWindow.WRAP_CONTENT);
            popupWindow1.setHeight(ListPopupWindow.WRAP_CONTENT);
        } catch (NoClassDefFoundError | ClassCastException | NoSuchFieldException | IllegalAccessException e) {
            // silently fail...
        }

        // tao listView
        lvListBuildingArea = new CRecyclerView(getActivity());
        lvListBuildingArea = (CRecyclerView) view.findViewById(R.id.lv_building_area);
        lvListBuildingArea.setNestedScrollingEnabled(false);
        buildingAreaAdapter = new BuildingAreaAdapter(getActivity(), new ArrayList<ItemBuilding>(), this);
        lvListBuildingArea.setAdapter(buildingAreaAdapter);
        loadMore();
    }

    public ArrayList<String> getListNameArea() {
        ArrayList<String> arr = new ArrayList<>();
        for (int i = 0; i < arrArea.size(); i++) {
            arr.add(arrArea.get(i).getName().toString());
        }
        return arr;
    }

    public ArrayList<String> getListNameBuilding(int positon) {
        ArrayList<String> arr = new ArrayList<>();
        ArrayList<ItemBuilding2> arrBuilding = new ArrayList<>();
        arrBuilding.addAll(arrArea.get(positon).getBuildings());
        for (int i = 0; i < arrBuilding.size(); i++) {
            arr.add(arrBuilding.get(i).getName().toString());
        }
        return arr;
    }

    private void getBuilding(int userID, String apiToken, int is_groceryAdmin) {
        progerssBarUntil.setInvisibeLayoutMain();

        Controller controller = new Controller();
        Call<GetBuildingResponse> call = controller.service.getBuildingArea(userID, apiToken, is_groceryAdmin);
        call.enqueue(new Callback<GetBuildingResponse>() {
            @Override
            public void onResponse(Response<GetBuildingResponse> response, Retrofit retrofit) {
                progerssBarUntil.setVisibeLayoutMain();
                if (response != null) {
                    GetBuildingResponse getBuildingResponse = response.body();
                    if (getBuildingResponse != null && getBuildingResponse.code == 200) {
                        buildingAreaAdapter.addAll(getBuildingResponse.result);
                    }
                }
            }

            @Override
            public void onFailure(Throwable t) {
                t.printStackTrace();
            }
        });
    }

    private void getArea(int city_id) {
        Controller controller = new Controller();
        Call<GetAreaResponse> call = controller.service.getArea(city_id);
        call.enqueue(new Callback<GetAreaResponse>() {
            @Override
            public void onResponse(Response<GetAreaResponse> response, Retrofit retrofit) {
                if (response != null) {
                    GetAreaResponse getAreaResponse = response.body();
                    if (getAreaResponse != null && getAreaResponse.code == 200) {
                        arrArea.addAll(getAreaResponse.result);
                        arrNameArea.addAll(getListNameArea());
                        ArrayAdapter adapter1 = new ArrayAdapter(getActivity(),
                                R.layout.item_spinner, arrNameArea);
                        spnArea.setAdapter(adapter1);
                    }
                }
            }

            @Override
            public void onFailure(Throwable t) {
                t.printStackTrace();
            }
        });
    }

    private void addBuilding(final Context context, AddBuildingAreaRequest addBuildingAreaRequest) {
        Controller controller = new Controller();
        Call<BaseResponse> call = controller.service.addbuildingArea(addBuildingAreaRequest);
        call.enqueue(new Callback<BaseResponse>() {
            @Override
            public void onResponse(Response<BaseResponse> response, Retrofit retrofit) {
                if (response != null) {
                    BaseResponse baseResponse = response.body();
                    if (baseResponse != null) {
                        if (baseResponse.code == 200) {
                            cleanAdapter();
                            loadMore();
                        }
                        Toast.makeText(context, baseResponse.message.toString(), Toast.LENGTH_SHORT).show();
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
