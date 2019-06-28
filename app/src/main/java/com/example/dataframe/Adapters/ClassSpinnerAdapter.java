package com.example.dataframe.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.dataframe.R;

public class ClassSpinnerAdapter extends BaseAdapter {
    private Context context;
    private String[] list;

    public ClassSpinnerAdapter(Context context, String[] list){
        this.context = context;
        this.list = list;
    }

    @Override
    public int getCount() {
        if(list.length != 0){
            return list.length;
        }
        return 0;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
         convertView = LayoutInflater.from(context).inflate(R.layout.custom_spinner_classes, parent, false);
        TextView textView = convertView.findViewById(R.id.spinner_item);
        textView.setText(list[position]);

         return convertView;
    }
}
