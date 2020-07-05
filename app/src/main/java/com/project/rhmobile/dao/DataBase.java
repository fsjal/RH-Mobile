package com.project.rhmobile.dao;

import com.android.volley.RequestQueue;
import com.android.volley.Response;

import java.util.Map;

public interface DataBase {

    void get(RequestQueue queue, String url, Response.Listener<String> listener,
             Response.ErrorListener error);

    void post(RequestQueue queue, String url, Map<String, String> params,
                      Response.Listener<String> listener, Response.ErrorListener error);

}
