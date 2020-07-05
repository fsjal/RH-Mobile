package com.project.rhmobile.dao;

import android.content.Context;

import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.project.rhmobile.R;
import com.project.rhmobile.entities.User;

import java.util.HashMap;
import java.util.Map;

public class Users {

    /**
     * Create a new user in the database
     * @param context
     * @param url
     * @param user
     * @param listener
     * @param error
     */
    public static void add(Context context, String url, User user, Response.Listener<String> listener,
                           Response.ErrorListener error) {
        DataBase base = new DataBaseDAO();
        Map<String, String> params = new HashMap<>();

        params.put(context.getString(R.string.user_param_name), user.getName());
        params.put(context.getString(R.string.user_param_prename), user.getPrename());
        params.put(context.getString(R.string.user_param_mail), user.getEmail());
        params.put(context.getString(R.string.user_param_password), user.getPassword());
        params.put(context.getString(R.string.user_param_phone), user.getPhone());
        base.post(Volley.newRequestQueue(context), url, params, listener, error);
    }

    /**
     * Get a user from the database
     * @param context
     * @param url
     * @param user
     * @param listener
     * @param error
     */
    public static void get(Context context, String url, User user, Response.Listener<String> listener,
                           Response.ErrorListener error) {
        DataBase base = new DataBaseDAO();
        Map<String, String> params = new HashMap<>();

        params.put(context.getString(R.string.user_param_mail), user.getEmail());
        params.put(context.getString(R.string.user_param_password), user.getPassword());
        base.post(Volley.newRequestQueue(context), url, params, listener, error);
    }
}
