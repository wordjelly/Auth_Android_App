package com.wordjelly.auth.oauth;

import android.util.Log;

import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.HttpUrl;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by root on 11/12/16.
 */

public class ServerConnection {

    /***
     *
     * @param param_name
     * @param param_value
     */
    public static void make_api_call_to_backend(String param_name, String param_value,String provider){

        OkHttpClient ok = new OkHttpClient();

        HttpUrl.Builder urlBuilder = HttpUrl.parse("http://10.0.2.2:3000/authenticate/omniauth/"+ provider +"/callback").newBuilder();
        String url = urlBuilder.build().toString();
        JSONObject object = new JSONObject();
        try {
            object.put("api_key", "test");
            object.put("current_app_id", "test_app_id");
            object.put("path", "omniauth/users/");
        } catch (Exception e) {
            Log.d("JSON EXCEPTION", "exception in json serialization of state object");
        }
        RequestBody requestBody = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart(param_name, param_value)
                .addFormDataPart("state", object.toString())
                .build();


        Request request = new Request.Builder()
                .url(url)
                .addHeader("CONTENT_TYPE", "application/json")
                .addHeader("ACCEPT", "application/json")
                .method("POST", RequestBody.create(null, new byte[0]))
                .post(requestBody)
                .build();

        try {
            //Response response = ok.newCall(request).execute();
            ok.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    Log.d("SERVER_RESPONSE_FAILED", e.toString());
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    Log.d("SERVER_RESPONSE", response.body().toString());
                }
            });

        } catch (Exception e) {
            Log.d("NETWORK CALL",e.toString());
        }

    }


}
