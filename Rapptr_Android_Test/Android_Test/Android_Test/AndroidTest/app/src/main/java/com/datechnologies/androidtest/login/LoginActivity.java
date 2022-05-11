package com.datechnologies.androidtest.login;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Handler;
import android.os.Looper;
import android.os.SystemClock;
import android.support.v4.os.HandlerCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.datechnologies.androidtest.MainActivity;
import com.datechnologies.androidtest.R;
import com.datechnologies.androidtest.VolleyWrapper;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONStringer;

import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.Executor;
import java.util.concurrent.ThreadFactory;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;

/**
 * A screen that displays a login prompt, allowing the user to login to the D & A Technologies Web Server.
 *
 */
public class LoginActivity extends AppCompatActivity {


    private final String ENDPOINT = "http://dev.rapptrlabs.com/Tests/scripts/login.php";
    private final String EMAIL_TAG = "email";
    private final String PASS_TAG = "password";
    private final String OK = "OK";
    private final String DEFAULT = "";

    private EditText emailField, passField;
    private View loginBtn;
    private String email, pass;
    private long initTime;

    private View.OnClickListener clickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch(view.getId()) {
                case R.id.login_btn:
                    send();
                    break;
            }

        }
    };

    private DialogInterface.OnClickListener dialogListener = new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialogInterface, int i) {
            onBackPressed();
        }
    };

    private com.android.volley.Response.Listener<String> responseListener = new com.android.volley.Response.Listener<String>() {
        @Override
        public void onResponse(String response) {
            try {
                JSONObject jsonArray = new JSONObject(response);
                String resp = "";
                Iterator<String> iter = jsonArray.keys();
                while(iter.hasNext()) {

                    String key = iter.next();
                    String val = jsonArray.getString(key);
                    resp += (key + ": " + val + "\n");
                }
                resp += ("time: " + (SystemClock.elapsedRealtime() - initTime) + " ms");
                dialog(resp, true);
            }
            catch (Exception e) {

            }
        }
    };

    private com.android.volley.Response.ErrorListener errorListener = new com.android.volley.Response.ErrorListener() {
        @Override
        public void onErrorResponse(VolleyError error) {

            dialog(error.toString(), false);
        }
    };


    private void dialog(final String message, final boolean exit) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                AlertDialog alertDialog = new AlertDialog.Builder(LoginActivity.this).
                        setPositiveButton(OK, exit ? dialogListener : null).
                        setMessage(message).create();
                alertDialog.show();
            }
        });

    }

    private void send() {
        if(emailField != null && passField != null) {
            email = emailField.getText().toString();
            pass = passField.getText().toString();

            initTime = SystemClock.elapsedRealtime();

            VolleyWrapper wrapper = new VolleyWrapper(LoginActivity.this,
                    com.android.volley.Request.Method.POST, ENDPOINT, errorListener,
                    responseListener, EMAIL_TAG, email, PASS_TAG, pass);
            wrapper.start();


        }
    }

    //==============================================================================================
    // Static Class Methods
    //==============================================================================================

    public static void start(Context context)
    {
        Intent starter = new Intent(context, LoginActivity.class);
        context.startActivity(starter);
    }

    //==============================================================================================
    // Lifecycle Methods
    //==============================================================================================

    @Override
    protected void onStart() {
        super.onStart();
        if(passField != null) {
            passField.setText(pass);
        }
        if(emailField != null) {
            emailField.setText(email);
        }
        if(loginBtn != null) {
            loginBtn.setOnClickListener(clickListener);
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        if(loginBtn != null) {
            loginBtn.setOnClickListener(null);
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle bundle) {

        super.onSaveInstanceState(bundle);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        ActionBar actionBar = getSupportActionBar();

        assert actionBar != null;
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setTitle(R.string.login_header);

        email = (savedInstanceState != null) ?
                savedInstanceState.getString(EMAIL_TAG, DEFAULT) : DEFAULT;
        pass = (savedInstanceState != null) ?
                savedInstanceState.getString(PASS_TAG, DEFAULT) : DEFAULT;

        emailField = findViewById(R.id.email_field);
        passField = findViewById(R.id.pass_field);
        loginBtn = findViewById(R.id.login_btn);

        // TODO: Make the UI look like it does in the mock-up. Allow for horizontal screen rotation.
        // TODO: Add a ripple effect when the buttons are clicked
        // TODO: Save screen state on screen rotation, inputted username and password should not disappear on screen rotation

        // TODO: Send 'email' and 'password' to http://dev.rapptrlabs.com/Tests/scripts/login.php
        // TODO: as FormUrlEncoded parameters.

        // TODO: When you receive a response from the login endpoint, display an AlertDialog.
        // TODO: The AlertDialog should display the 'code' and 'message' that was returned by the endpoint.
        // TODO: The AlertDialog should also display how long the API call took in milliseconds.
        // TODO: When a login is successful, tapping 'OK' on the AlertDialog should bring us back to the MainActivity

        // TODO: The only valid login credentials are:
        // TODO: email: info@rapptrlabs.com
        // TODO: password: Test123
        // TODO: so please use those to test the login.
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        emailField = null;
        passField = null;
        loginBtn = null;
        email = null;
        pass = null;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed()
    {
        finish();
//        Intent intent = new Intent(this, MainActivity.class);
//        startActivity(intent);
    }
}
