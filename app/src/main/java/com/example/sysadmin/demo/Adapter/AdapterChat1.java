package com.example.sysadmin.demo.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.sysadmin.demo.Model.Message;
import com.example.sysadmin.demo.R;

import java.util.List;

/**
 * Created by QWAN on 29/03/2017.
 */

public class AdapterChat1 extends RecyclerView.Adapter<AdapterChat1.ChatHolder> {
    private static final String TAG = "SongAdapter";
    private List<Message> mSongs;
    private Context mContext;
    private LayoutInflater mLayoutInflater;

    public AdapterChat1(Context context, List<Message> datas) {
        mContext = context;
        mSongs = datas;
        mLayoutInflater = LayoutInflater.from(context);
    }

    @Override
    public int getItemViewType(int position) {
        //Implement your logic here
        Message mess = mSongs.get(position);
        return mess.getFromId();
    }
    @Override
    public ChatHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //inflate view from row_item_song.xml
        View itemView =null;
        Log.e(TAG, "onCreateViewHolder: "+viewType );
        Log.e(TAG, "onCreateViewHolder: " );
        switch(viewType){
            case 0:
                itemView =  mLayoutInflater.inflate(R.layout.row_chat_left, parent, false);
                break;
            case 1:
                itemView = mLayoutInflater.inflate(R.layout.row_chat_right, parent, false);
                break;
        }
        return new ChatHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ChatHolder holder, int position) {
        //get song in mSong via position
        Message song = mSongs.get(position);

        //bind data to viewholder
        holder.tvCode.setText(song.getContent());
        holder.tvTitle.setText(song.getTime()+"");
    }

    @Override
    public int getItemCount() {
        return mSongs.size();
    }

    class ChatHolder extends RecyclerView.ViewHolder  {
        private TextView tvCode;
        private TextView tvTitle;

        public ChatHolder(View itemView) {
            super(itemView);
            tvCode = (TextView) itemView.findViewById(R.id.txtUser);
            tvTitle = (TextView) itemView.findViewById(R.id.txtMess);

        }


    }
}