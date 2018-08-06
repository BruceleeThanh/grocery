package com.grocery.activity;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.gson.Gson;
import com.grocery.R;
import com.grocery.common.Config;
import com.grocery.common.CustomEditText;
import com.grocery.controller.Controller;
import com.grocery.model.AdminModel;
import com.grocery.request.LoginRequest;
import com.grocery.response.LoginResponse;
import com.grocery.service.DeleteTokenService;

import java.util.ArrayList;
import java.util.List;

import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

/**
 * Created by ThanhBeo on 16/06/2017.
 */

public class MainLogin extends AppCompatActivity implements View.OnClickListener {
    private static final int MY_PERMISSIONS_REQUEST_ACCOUNTS = 1;
    private LinearLayout btnLogin;
    private EditText edtName;
    private CustomEditText edtPass;
    private String token_firebase = "";

    private ProgressDialog dialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.login);

        initView();
        initListener();
    }

    private void initView() {

        edtName = (EditText) findViewById(R.id.edt_adminName);
        edtPass = (CustomEditText) findViewById(R.id.edt_passWord);
        btnLogin = (LinearLayout) findViewById(R.id.btn_login);
        SharedPreferences sharedPreferences = getSharedPreferences(Config.Pref, MODE_PRIVATE);
        String profile = sharedPreferences.getString(Config.KEY_ADMIN_PROFILE, "");
        Gson gson = new Gson();
        AdminModel userModel = gson.fromJson(profile, AdminModel.class);
        if (userModel != null) {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            finish();
        } else {
            Intent intent1 = new Intent(MainLogin.this, DeleteTokenService.class);
            MainLogin.this.startService(intent1);
        }

        dialog = new ProgressDialog(MainLogin.this);
        dialog.setMessage("Loading...");
        dialog.setCancelable(false);
    }

    private void initListener() {
        btnLogin.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (!checkInput()) {
            return;
        }
        LoginRequest loginRequest = new LoginRequest(edtName.getText().toString(), edtPass.getText().toString(), token_firebase);
        login(this, loginRequest);
    }

    public boolean checkInput() {
        String userName = edtName.getText().toString().trim();
        String passWord = edtPass.getText().toString().trim();
        if (userName.isEmpty()) {
            Toast.makeText(this, getString(R.string.please_enter_username), Toast.LENGTH_SHORT).show();
            return false;
        }
        if (passWord.isEmpty()) {
            Toast.makeText(this, getString(R.string.please_enter_pass), Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    private boolean checkAndRequestPermissions() {
        int permissionINTERNET = ContextCompat.checkSelfPermission(this, Manifest.permission.INTERNET);

        List<String> listPermissionsNeeded = new ArrayList<>();
        if (permissionINTERNET != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.INTERNET);
        }
        if (listPermissionsNeeded != null) {
            ActivityCompat.requestPermissions(this,
                    listPermissionsNeeded.toArray(new String[listPermissionsNeeded.size()]), MY_PERMISSIONS_REQUEST_ACCOUNTS);
            return false;
        }

        return true;
    }

    private void login(final Context context, LoginRequest loginRequest) {
        if (dialog.isShowing()) dialog.dismiss();
        dialog.show();

        try {
            Controller controller = new Controller();
            String session_id = FirebaseInstanceId.getInstance().getToken().toString();
            loginRequest.token_firebase = session_id;
            Call<LoginResponse> call = controller.service.adminLogin(loginRequest);
            call.enqueue(new Callback<LoginResponse>() {
                @Override
                public void onResponse(Response<LoginResponse> response, Retrofit retrofit) {
                    dialog.dismiss();
                    if (response != null) {
                        LoginResponse loginResponse = response.body();
                        if (loginResponse != null) {
                            if (loginResponse.code == 200) {
                                AdminModel adminModel = loginResponse.result;
                                Gson gson = new Gson();
                                String profile = gson.toJson(adminModel);
                                SharedPreferences sharedPreferences = getSharedPreferences(Config.Pref,
                                        MainLogin.this.MODE_PRIVATE);
                                SharedPreferences.Editor editor = sharedPreferences.edit();
                                editor.putString(Config.KEY_ADMIN_PROFILE, profile).commit();

                                Intent intent = new Intent(context, MainActivity.class);
                                startActivity(intent);
                                finish();
                                return;
                            }
                            Toast.makeText(context, loginResponse.message, Toast.LENGTH_SHORT).show();
                        }
                    }
                }

                @Override
                public void onFailure(Throwable t) {
                    t.printStackTrace();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_ACCOUNTS:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    //Permission Granted Successfully. Write working code here.
                } else {
                    //You did not accept the request can not use the functionality.
                }
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (dialog.isShowing()) dialog.dismiss();
    }
}
