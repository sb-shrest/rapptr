package com.datechnologies.androidtest.chat;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.datechnologies.androidtest.R;
import com.datechnologies.androidtest.VolleyWrapper;
import com.datechnologies.androidtest.api.ChatLogMessageModel;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * A recycler view adapter used to display chat log messages in {@link ChatActivity}.

 */
public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.ChatViewHolder>
{
    //==============================================================================================
    // Class Properties
    //==============================================================================================

    private List<ChatLogMessageModel> chatLogMessageModelList;

    //==============================================================================================
    // Constructor
    //==============================================================================================

    public ChatAdapter()
    {
        chatLogMessageModelList = new ArrayList<>();
    }

    //==============================================================================================
    // Class Instance Methods
    //==============================================================================================

    public void setChatLogMessageModelList(List<ChatLogMessageModel> chatLogMessageModelList)
    {
        this.chatLogMessageModelList = chatLogMessageModelList;
        notifyDataSetChanged();
    }

    //==============================================================================================
    // RecyclerView.Adapter Methods
    //==============================================================================================

    @Override
    public ChatViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View itemView = LayoutInflater.
                from(parent.getContext()).
                inflate(R.layout.item_chat, parent, false);

        return new ChatViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ChatViewHolder viewHolder, int position)
    {
        viewHolder.update(chatLogMessageModelList.get(position));
    }

    @Override
    public int getItemCount()
    {
        return chatLogMessageModelList.size();
    }

    //==============================================================================================
    // ChatViewHolder Class
    //==============================================================================================

    public static class ChatViewHolder extends RecyclerView.ViewHolder
    {
        ImageView avatarImageView;
        TextView messageTextView, usernameView;



        private void update(ChatLogMessageModel mod) {
            messageTextView.setText(mod.message);
            usernameView.setText(mod.username);
            Picasso.get().load(mod.avatarUrl).into(avatarImageView);

        }

        public ChatViewHolder(View view)
        {
            super(view);
            avatarImageView = (ImageView)view.findViewById(R.id.avatarImageView);
            messageTextView = (TextView)view.findViewById(R.id.messageTextView);
            usernameView = view.findViewById(R.id.usernameView);
        }
    }

}
