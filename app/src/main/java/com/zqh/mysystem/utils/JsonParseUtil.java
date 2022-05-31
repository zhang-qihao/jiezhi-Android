package com.zqh.mysystem.utils;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import okhttp3.Response;

public class JsonParseUtil {
    public static void parseJsonWithJsonObject(Response response) {
        assert response.body() != null;
        String responseData = response.body().toString();
        try{
            JSONArray jsonArray = new JSONArray(responseData);
            JSONObject jsonObject = jsonArray.getJSONObject(1);
            String total = jsonObject.getString("Total");
            Log.i("HomePage", "total" + total);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
