package com.grocery.utils;

import android.view.View;

import com.grocery.R;

/**
 * Created by ThanhBeo on 14/09/2017.
 */

public class ProgerssBarUntil {
    private View view;

    public ProgerssBarUntil(View view) {
        this.view = view;
    }

    public void setVisibeLayoutMain() {
        view.findViewById(R.id.progressBarMain).setVisibility(View.INVISIBLE);
        view.findViewById(R.id.layout_main).setVisibility(View.VISIBLE);
    }
    public void setInvisibeLayoutMain() {
        view.findViewById(R.id.progressBarMain).setVisibility(View.VISIBLE);
        view.findViewById(R.id.layout_main).setVisibility(View.INVISIBLE);
    }
}
