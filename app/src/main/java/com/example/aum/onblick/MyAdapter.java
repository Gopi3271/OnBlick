package com.example.aum.onblick;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by AUM on 1/18/2016.
 */
public class MyAdapter extends ArrayAdapter<MyApp>
{
    Context cont;
   ArrayList<MyApp> list;
    public MyAdapter(Context context, int resource, ArrayList<MyApp> objects) {
        super(context, resource, objects);
        cont=context;
        list=objects;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View view;
        LayoutInflater inflater=(LayoutInflater)cont.getSystemService(cont.LAYOUT_INFLATER_SERVICE);
        view=inflater.inflate(R.layout.list_items_adapter,parent,false);

        TextView color=(TextView)view.findViewById(R.id.textViewColor);
        TextView value=(TextView)view.findViewById(R.id.textViewValue);
        MyApp b=list.get(position);

        color.setText(b.getC());
        value.setText(b.getV());

        return view;
    }
}
