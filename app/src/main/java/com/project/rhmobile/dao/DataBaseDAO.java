package com.project.rhmobile.dao;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.Map;

public class DataBaseDAO implements DataBase {

    @Override
    public void get(RequestQueue queue, String url, Response.Listener<String> listener,
                    Response.ErrorListener error) {
       queue.add(new StringRequest(Request.Method.GET, url, listener, error));
    }

    @Override
    public void post(RequestQueue queue, String url, Map<String, String> params,
                             Response.Listener<String> listener, Response.ErrorListener error) {

        queue.add(new StringRequest(Request.Method.POST, url, listener, error) {

            @Override
            protected Map<String, String> getParams() {
                return params;
            }
        });
    }
}
