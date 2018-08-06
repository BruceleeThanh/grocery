package com.grocery.model;

/**
 * Created by ThanhBeo on 16/09/2017.
 */

public class AdminModel {
    private int id;
    private String email;
    private String description;
    private String created_at;
    private String updated_at;
    private String token_firebase;
    private String ApiToken;
    private int city_id;

    public int getCity_id() {
        return city_id;
    }

    public void setCity_id(int city_id) {
        this.city_id = city_id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public String getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(String updated_at) {
        this.updated_at = updated_at;
    }

    public String getToken_firebase() {
        return token_firebase;
    }

    public void setToken_firebase(String token_firebase) {
        this.token_firebase = token_firebase;
    }

    public String getApiToken() {
        return ApiToken;
    }

    public void setApiToken(String apiToken) {
        ApiToken = apiToken;
    }
}
