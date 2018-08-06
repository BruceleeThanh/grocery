package com.grocery.request;

import java.io.Serializable;

/**
 * Created by ThanhBeo on 16/09/2017.
 */

public class LoginRequest extends BaseRequest implements Serializable {
    public static final String PATH_URL = "/api/grocery/login";
    public String email;
    public String password;
    public String token_firebase;

    public LoginRequest( String email, String password, String token_firebase) {
        super(PATH_URL);
        this.email = email;
        this.password = password;
        this.token_firebase = token_firebase;
    }
}
