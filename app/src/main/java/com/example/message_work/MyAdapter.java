package com.example.message_work;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {

    private Context mContext;
    private onClickListener mClickListener;
    private List<Message> messageList;

    MyAdapter(Context context, List<Message> messageList){
        this.mContext = context;
        this.messageList = messageList;
    }

    @NonNull
    @Override
    public MyAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_title, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyAdapter.ViewHolder viewHolder,final int i) {
        final int pos = viewHolder.getLayoutPosition();
        viewHolder.mMessageTv.setText(messageList.get(i).getTitle());
        if (mClickListener  != null){
            viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mClickListener.onClick(pos);
                }
            });
            viewHolder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    mClickListener.onLongClick(pos);
                    return true;
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return messageList != null ?messageList.size():0;
    }

    static class ViewHolder extends RecyclerView.ViewHolder{
        TextView mMessageTv;

        ViewHolder(View itemView){
            super(itemView);
            mMessageTv = itemView.findViewById(R.id.item_titles);
        }
    }

    interface onClickListener{
        void onClick(int pos);
        void onLongClick(int pos);
    }

    void setClickListener(onClickListener clickListener){//?
        this.mClickListener = clickListener;
    }

}
