package com.example.remi.androidmarket;

import android.app.DialogFragment;
import android.app.Notification;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MyRAdapter extends RecyclerView.Adapter<MyRAdapter.ViewHolder> {
    public List<String> data;
    private static LongClickAction longClickAction;

    public interface LongClickAction{
        void OnLongClick(int position, View v);

    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public TextView mTextView;

        public ViewHolder(TextView v) {
            super(v);
            mTextView = v;
            mTextView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    longClickAction.OnLongClick(getAdapterPosition(), view);
                    return true;
                }
            });
        }

    }


    public MyRAdapter(List<String> adapterData, LongClickAction listener) {
        data = new ArrayList<String>();
        for (int i = 0; i < adapterData.size(); i++) {
            data.add(adapterData.get(i));
        }
        this.longClickAction = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        TextView v = (TextView) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.my_text_view, parent, false);
        //...
        ViewHolder vh = new ViewHolder(v);
        return vh;

    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.mTextView.setText(data.get(position));
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public void AddNewItem(String s) {
        data.add(s);
        this.notifyDataSetChanged();
    }

    public void DeleteItem(int position) {
        data.remove(position);
        this.notifyItemRemoved(position);
    }

    public void EditItem(int position, String value) {
        data.set(position, value);
        this.notifyItemChanged(position);
    }

}


