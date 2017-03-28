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

public class AdapterChat  extends RecyclerView.Adapter<AdapterChat.ChatHolder>  {
    private List<Message> datas;
    private Context mContext;
    private LayoutInflater  mLayoutInflater;
    String TAG = "AdapterChat";
    public AdapterChat(Context context, List<Message> datas) {
        mContext = context;
        this.datas = datas;
        mLayoutInflater = LayoutInflater.from(context);
    }
    @Override
    public int getItemViewType(int position) {
        //Implement your logic here
        Message mess = datas.get(position);
        return mess.getFromId();
    }
    @Override
    public ChatHolder onCreateViewHolder(ViewGroup parent, int viewType) {
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
        Message mess= datas.get(position);
        Log.e( "onBindViewHolder: ",mess.getContent() );
        holder.txtUser.setText(mess.getContent());
        holder.txtMess.setText(mess.getFromId());
    }

    @Override
    public int getItemCount() {
        return 0;
    }

    class ChatHolder extends RecyclerView.ViewHolder  {
        private TextView txtUser;
        private TextView txtMess;

        public ChatHolder(View itemView) {
            super(itemView);
            txtMess = (TextView) itemView.findViewById(R.id.txtUser);
            txtUser = (TextView) itemView.findViewById(R.id.txtMess);
        }


    }
}
