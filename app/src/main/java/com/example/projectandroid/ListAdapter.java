package com.example.projectandroid;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.projectandroid.DataModel.Employee;

import java.util.ArrayList;
import java.util.List;


public class ListAdapter extends ArrayAdapter {

    private ArrayList<Employee> mList;



    public ListAdapter(Context context, int resource, ArrayList<Employee> employees){
        super(context, resource);
        this.mList = employees;
        Log.d("ADAPTER",""+mList.size());
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Nullable
    @Override
    public Object getItem(int position) {
        return mList.get(position);
    }



    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        View row = convertView;
        LayoutHandler layoutHandler;

        if(row == null){
            LayoutInflater layoutInflater = (LayoutInflater)this.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = layoutInflater.inflate(R.layout.list_row,parent,false);
            layoutHandler = new LayoutHandler();
            layoutHandler.userName = row.findViewById(R.id.name);
            layoutHandler.score = row.findViewById(R.id.id);
            row.setTag(layoutHandler);
        }else {
            layoutHandler = (LayoutHandler)row.getTag();
        }

        Employee employee = mList.get(position);
        layoutHandler.userName.setText(employee.getName());
        layoutHandler.score.setText(String.valueOf(employee.getID()));


        return row;

    }

    public void setList(ArrayList<Employee> employees) {
        this.mList =employees;
        notifyDataSetInvalidated();
    }


    static class LayoutHandler{
        TextView userName,score; // declaring the textviews to use within the list
    }
}
