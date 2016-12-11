package fr.esgi.pokeshop.pokeshop.service;

import org.json.JSONObject;

/**
 * Created by Marion on 11/12/2016.
 */

public interface ConnectListener {
    public void onSuccess(JSONObject obj);
    public void onFailed(String msg);
}