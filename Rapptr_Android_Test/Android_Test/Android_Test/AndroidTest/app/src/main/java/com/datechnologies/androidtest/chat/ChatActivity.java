package com.datechnologies.androidtest.chat;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.datechnologies.androidtest.MainActivity;
import com.datechnologies.androidtest.R;
import com.datechnologies.androidtest.VolleyWrapper;
import com.datechnologies.androidtest.api.ChatLogMessageModel;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Screen that displays a list of chats from a chat log.
 */
public class ChatActivity extends AppCompatActivity {

    //==============================================================================================
    // Class Properties
    //==============================================================================================

    private final String ENDPOINT = "http://dev.rapptrlabs.com/Tests/scripts/chat_log.php";
    private final String DATA_TAG = "data";
    private final String NAME_TAG = "name";
    private final String AVATAR_TAG = "avatar_url";
    private final String MSG_TAG = "message";

    private RecyclerView recyclerView;
    private ChatAdapter chatAdapter;


    private Response.ErrorListener errorListener = new Response.ErrorListener() {
        @Override
        public void onErrorResponse(VolleyError error) {

        }
    };

    private Response.Listener<String> responseListener = new Response.Listener<String>() {
        @Override
        public void onResponse(String response) {
            try {

                JSONObject jsonObject = new JSONObject(response);
                JSONArray jsonArray = jsonObject.getJSONArray(DATA_TAG);

                if(jsonArray != null) {
                    List<ChatLogMessageModel> thread = new ArrayList<>();
                    for(int i = 0; i < jsonArray.length(); ++i) {
                        JSONObject entry = jsonArray.getJSONObject(i);
                        if(entry != null && entry.has(NAME_TAG) &&
                                entry.has(AVATAR_TAG) && entry.has(MSG_TAG)) {
                            ChatLogMessageModel model = new ChatLogMessageModel();
                            model.avatarUrl = entry.getString(AVATAR_TAG);
                            model.username = entry.getString(NAME_TAG);
                            model.message = entry.getString(MSG_TAG);

                            thread.add(model);
                        }
                    }
                    chatAdapter.setChatLogMessageModelList(thread);
                }
            }
            catch (Exception e) {

            }
        }
    };

    private void send() {
        VolleyWrapper wrapper = new VolleyWrapper(ChatActivity.this, Request.Method.GET,
                ENDPOINT, errorListener, responseListener);
        wrapper.start();
    }

    //==============================================================================================
    // Static Class Methods
    //==============================================================================================

    public static void start(Context context)
    {
        Intent starter = new Intent(context, ChatActivity.class);
        context.startActivity(starter);
    }

    //==============================================================================================
    // Lifecycle Methods
    //==============================================================================================

    @Override
    protected void onStart() {
        super.onStart();
        send();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);

        ActionBar actionBar = getSupportActionBar();

        assert actionBar != null;
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);
        setTitle(R.string.chat_header);

        chatAdapter = new ChatAdapter();

        recyclerView.setAdapter(chatAdapter);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext(),
                LinearLayoutManager.VERTICAL,
                false));

        // TODO: Make the UI look like it does in the mock-up. Allow for horizontal screen rotation.

        // TODO: Retrieve the chat data from http://dev.rapptrlabs.com/Tests/scripts/chat_log.php
        // TODO: Parse this chat data from JSON into ChatLogMessageModel and display it.
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
    }
}
