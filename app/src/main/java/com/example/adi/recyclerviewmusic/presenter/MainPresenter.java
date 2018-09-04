package com.example.adi.recyclerviewmusic.presenter;


import android.app.ProgressDialog;
import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.adi.recyclerviewmusic.model.DataVideo;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MainPresenter {

    private MainPresenter.View view;

    public MainPresenter(MainPresenter.View view) {
        this.view = view;
    }

    public void initData(Context context) {
        final List<DataVideo> datas = new ArrayList<DataVideo>();
        final ProgressDialog pDialog = new ProgressDialog(context);
        pDialog.setMessage("Loading...");
        pDialog.show();
        RequestQueue queue = Volley.newRequestQueue(context);
        String url = "https://itunes.apple.com/search?term=jack+johnson&entity=musicVideo";
        JsonObjectRequest jsObjRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                pDialog.hide();
                try {
                    JSONArray jsonArray = response.getJSONArray("results");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject object = jsonArray.getJSONObject(i);
                        if (object.has("previewUrl") && object.has("trackCensoredName")
                                && object.has("artistName") && object.has("artworkUrl30")) {
                            DataVideo dataVideo = new DataVideo(object.getString("previewUrl"),
                                    object.getString("trackCensoredName"),
                                    object.getString("artistName"),
                                    object.getString("artworkUrl30"));
                            datas.add(dataVideo);
                        }
                    }
                    view.addDatas(datas);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        queue.add(jsObjRequest);

    }


    public interface View {
        void addDatas(List<DataVideo> datas);
    }

}
