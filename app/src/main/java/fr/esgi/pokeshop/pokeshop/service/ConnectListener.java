package fr.esgi.pokeshop.pokeshop.service;

import org.json.JSONObject;

public interface ConnectListener {
    void onSuccess(JSONObject obj);
    void onFailed(String msg);
}