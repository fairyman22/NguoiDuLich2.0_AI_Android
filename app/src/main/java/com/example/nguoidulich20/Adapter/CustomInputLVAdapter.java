package com.example.nguoidulich20.Adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.nguoidulich20.R;

import java.util.ArrayList;
import java.util.List;

public class CustomInputLVAdapter extends ArrayAdapter<String> {
    private Context context;
    private int resource;
    private ArrayList<String> arrList;
    private String TAG = getClass().getSimpleName();
    public CustomInputLVAdapter(@NonNull Context context, int resource, @NonNull ArrayList<String> objects) {
        super(context, resource, objects);
        this.context =context;
        this.resource = resource;
        this.arrList = objects;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        convertView = LayoutInflater.from(context).inflate(R.layout.duongdi,parent,true);
        TextView tv = convertView.findViewById(R.id.tv);
        EditText edt = convertView.findViewById(R.id.distance);
        String str =  arrList.get(position);
        tv.setText(str);
        edt.setText("huhu");
        Log.d(TAG, "getView: "+position);
        return convertView;
    }
}
