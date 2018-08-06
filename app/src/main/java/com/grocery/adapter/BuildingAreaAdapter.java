package com.grocery.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.grocery.R;
import com.grocery.common.Config;
import com.grocery.common.CustomTextView;
import com.grocery.controller.Controller;
import com.grocery.fragment.fragmentMenu.FragmentAddBuilding;
import com.grocery.model.ItemBuilding;
import com.grocery.request.DeleteBuildingRequest;
import com.grocery.response.BaseResponse;

import java.util.List;

import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

/**
 * Created by ThanhBeo on 19/06/2017.
 */

public class BuildingAreaAdapter extends BaseRecyclerAdapter<ItemBuilding, BuildingAreaAdapter.ViewHoler> {
    private FragmentAddBuilding fragmentAddBuilding;

    public BuildingAreaAdapter(Context context, List<ItemBuilding> list, FragmentAddBuilding fragmentAddBuilding) {
        super(context, list);
        this.fragmentAddBuilding = fragmentAddBuilding;
    }

    @Override
    public void onBindViewHolder(ViewHoler holder, int position) {
        holder.binData(list.get(position));
    }

    @Override
    public ViewHoler onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.item_building_area, parent, false);
        return new ViewHoler(view);
    }

    @Override
    public ItemBuilding getItembyPostion(int position) {
        return super.getItembyPostion(position);
    }

    public class ViewHoler extends RecyclerView.ViewHolder implements View.OnClickListener {
        private CustomTextView tvArea;
        private CustomTextView tvBuilding;
        private CustomTextView btnDelete;

        public ViewHoler(View itemView) {
            super(itemView);
            tvArea = (CustomTextView) itemView.findViewById(R.id.tv_area_item);
            tvBuilding = (CustomTextView) itemView.findViewById(R.id.tv_building_item);
            btnDelete = (CustomTextView) itemView.findViewById(R.id.btn_delete_item_building_area);
            btnDelete.setOnClickListener(this);
        }

        public void binData(ItemBuilding itemBuilding) {
            tvArea.setSelected(true);
            tvArea.setText(itemBuilding.getArea_name());
            tvBuilding.setSelected(true);
            tvBuilding.setText(itemBuilding.getBuilding_name());
        }

        @Override
        public void onClick(View v) {
            DeleteBuildingRequest deleteBuildingRequest = new DeleteBuildingRequest(Config.AdminId, Config.apiToken,
                    list.get(getAdapterPosition()).getId(), Config.is_groceryAdmin);
            deleteBuilding(mContext, deleteBuildingRequest);
        }
    }

    private void deleteBuilding(final Context context, DeleteBuildingRequest deleteBuildingRequest) {
        Controller controller = new Controller();
        Call<BaseResponse> call = controller.service.deleteBuildingArea(deleteBuildingRequest);
        call.enqueue(new Callback<BaseResponse>() {
            @Override
            public void onResponse(Response<BaseResponse> response, Retrofit retrofit) {
                if (response != null) {
                    BaseResponse baseResponse = response.body();
                    if (baseResponse != null) {
                        if (baseResponse.code == 200) {
                            fragmentAddBuilding.cleanAdapter();
                            fragmentAddBuilding.loadMore();
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
