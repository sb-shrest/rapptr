package com.datechnologies.androidtest;

import android.content.Context;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class VolleyWrapper extends Thread {

    private final String CONTENT = "application/x-www-form-urlencoded; charset=UTF-8";

    private int method;
    private String endpoint;
    private Context context;
    private ArrayList<String> args = new ArrayList<>();
    private Response.Listener<String> responseListener;
    private Response.ErrorListener errorListener;

    public VolleyWrapper(Context con, int met, String end,
                  Response.ErrorListener error,
                  Response.Listener<String> response, String... arg) {

        method = met;
        endpoint = end;

        context = con;

        errorListener = error;
        responseListener = response;

        for(int i = 0; i < arg.length; ++i) {
            args.add(arg[i]);
        }
    }

    @Override
    public void run() {
        try {


            StringRequest request = new StringRequest(method, endpoint, responseListener, errorListener) {

                @Override
                public String getBodyContentType() {
                    return CONTENT;
                }

                @Override
                protected Map<String, String> getParams() {
                    Map<String, String> params = new HashMap<>();
                    for(int i = 0; (i + 1) < args.size(); i = i + 2) {
                        params.put(args.get(i), args.get(i + 1));
                    }
                    return params;
                }

            };

            RequestQueue queue = Volley.newRequestQueue(context);
            queue.add(request);
            queue.start();

        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}
