package com.example.josh.assignment4;

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

public class CustomAdapter extends ArrayAdapter<TeamItem> {
    public CustomAdapter(@NonNull Context context, @NonNull List<TeamItem> teams) {
        super(context, 0, teams);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        TeamItem team = getItem(position);
        convertView = LayoutInflater.from(getContext()).inflate(R.layout.listrow, parent,false);
        TextView city = (TextView) convertView.findViewById(R.id.rowCity);
        TextView teamName = (TextView) convertView.findViewById(R.id.rowTeamName);

        city.setText(team.getTeamCity());
        teamName.setText(team.getTeamName());
        return convertView;
    }
}
