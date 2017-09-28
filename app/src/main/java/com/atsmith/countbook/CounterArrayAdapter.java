/*
 * Counter
 *
 * Version 1
 *
 * September 28, 2017
 *
 * Copyright 2017 Andrew Smith, CMPUT 301, University of Alberta.
 * All Rights Reserved. This code can be used, modified, and distributed in
 * accordance with the Code of Student Behaviour at University of Alberta.
 * A copy of the license can be found in this project.
 * Otherwise, contact atsmith@ualberta.ca
 */
package com.atsmith.countbook;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;

import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;

/**
 * A class extending the default ArrayAdapter.
 * Formats the display of the list of counters
 * via the getView method. When the edit button
 * is pressed, an intent is created for the
 * CreateCounterActivity which returns to the
 * main activity.
 *
 * @author atsmith
 * @version 1.0
 */
public class CounterArrayAdapter extends ArrayAdapter<Counter>{
    private Context context;

    public CounterArrayAdapter(Context context, ArrayList<Counter> counters){
        super(context, 0, counters);
        this.context = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        Counter c = getItem(position);
        if (convertView==null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.counter_item, parent, false);
        }
        TextView name = (TextView) convertView.findViewById(R.id.counterName);
        TextView date = (TextView) convertView.findViewById(R.id.counterDate);
        TextView val = (TextView) convertView.findViewById(R.id.counterValue);
        TextView comment = (TextView) convertView.findViewById(R.id.counterComment);
        ImageButton incButton = (ImageButton) convertView.findViewById(R.id.counterInc);
        ImageButton decButton = (ImageButton) convertView.findViewById(R.id.counterDec);
        ImageButton resetButton = (ImageButton) convertView.findViewById(R.id.counterReset);
        ImageButton editButton = (ImageButton) convertView.findViewById(R.id.counterEdit);
        ImageButton deleteButton = (ImageButton) convertView.findViewById(R.id.counterDelete);

        name.setText(c.getName());
        val.setText(Integer.toString(c.getValue()));
        date.setText(c.getDate().toString());
        if (c.getComment().isEmpty()){
            comment.setVisibility(View.GONE);
        }else{
            comment.setText(c.getComment());
        }

        incButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                View parent = (View) view.getParent().getParent();
                ListView listView = (ListView) parent.getParent();
                int position = listView.getPositionForView(parent);
                Counter counter = getItem(position);
                counter.incValue();
                notifyDataSetChanged();
            }
        });

        decButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                View parent = (View) view.getParent().getParent();
                ListView listView = (ListView) parent.getParent();
                int position = listView.getPositionForView(parent);
                Counter counter = getItem(position);
                counter.decValue();
                notifyDataSetChanged();
            }
        });

        resetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                View parent = (View) view.getParent().getParent();
                ListView listView = (ListView) parent.getParent();
                int position = listView.getPositionForView(parent);
                Counter counter = getItem(position);
                counter.resetValue();
                notifyDataSetChanged();
            }
        });

        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                View parent = (View) view.getParent().getParent();
                ListView listView = (ListView) parent.getParent();
                int position = listView.getPositionForView(parent);
                Counter counter = getItem(position);
                Intent intent = new Intent(context, CreateCounterActivity.class);
                intent.putExtra(MainActivity.EXTRA_COUNTERNAME, counter.getName());
                intent.putExtra(MainActivity.EXTRA_COUNTERINITVALUE, Integer.toString(counter.getInitalValue()));
                intent.putExtra(MainActivity.EXTRA_COUNTERCOMMENT, counter.getComment());
                intent.putExtra(MainActivity.EXTRA_COUNTERPOSITION, Integer.toString(position));
                ((Activity) context).startActivityForResult(intent,1);
            }
        });

        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                View parent = (View) view.getParent().getParent();
                ListView listView = (ListView) parent.getParent();
                int position = listView.getPositionForView(parent);
                Counter counter = getItem(position);
                remove(counter);
                notifyDataSetChanged();
            }
        });

        return convertView;
    }
}
