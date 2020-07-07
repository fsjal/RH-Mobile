package com.project.rhmobile.dao;

import android.content.Context;

import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

public class Services {

    public static void get(Context context, String url, Response.Listener<String> listener,
                           Response.ErrorListener error) {
        DataBase base = new DataBaseDAO();
        base.get(Volley.newRequestQueue(context), url, listener, error);
    }
}
