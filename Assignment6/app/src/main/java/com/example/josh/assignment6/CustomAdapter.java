package com.example.josh.assignment6;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class CustomAdapter extends ArrayAdapter<ListItem> {
    public CustomAdapter(@NonNull Context context, @NonNull List<ListItem> objects) {
        super(context, 0, objects);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        ListItem tempEQObj = getItem(position);
        convertView = LayoutInflater.from(getContext()).inflate(R.layout.listrow, parent,false);
        TextView eqDescription = (TextView) convertView.findViewById(R.id.tViewEQDesc);
        TextView eqDate = (TextView) convertView.findViewById(R.id.tViewEQDate);

        if(position%2 ==0 )
        {
            convertView.setBackgroundColor(Color.parseColor("#00ff99"));
        }
        else
        {
            convertView.setBackgroundColor(Color.parseColor("#ffff66"));
        }
        eqDescription.setText(tempEQObj.getEarthquakeTitle());
        eqDate.setText(tempEQObj.getEarthquakeDate());
        return convertView;
    }
}
