package com.anshu.chatapp.Adepter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.anshu.chatapp.R;

import java.util.List;

public class MyStatusAdapter extends BaseAdapter {
    private List<String> statusList;
    private Context context;

    public MyStatusAdapter(Context context, List<String> statusList) {
        this.context = context;
        this.statusList = statusList;
    }

    @Override
    public int getCount() {
        return statusList.size();
    }

    @Override
    public Object getItem(int position) {
        return statusList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_status, parent, false);
        }

        TextView textView = convertView.findViewById(R.id.text_status);
        textView.setText(statusList.get(position));

        return convertView;
    }
}