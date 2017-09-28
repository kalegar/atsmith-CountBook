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

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.lang.reflect.Type;
import java.util.ArrayList;

/**
 * The main class for the app.
 * Handles loading and saving the counters, as well as
 * creating/updating counters via intents. (See onActivityResult method)
 *
 * @author atsmith
 * @version 1.0
 */
public class MainActivity extends AppCompatActivity {
    public static final String EXTRA_HITSUBMIT = "com.atsmith.countbook.HITSUBMIT";
    public static final String EXTRA_COUNTERNAME = "com.atsmith.countbook.COUNTERNAME";
    public static final String EXTRA_COUNTERINITVALUE = "com.atsmith.countbook.COUNTERINITVALUE";
    public static final String EXTRA_COUNTERCOMMENT = "com.atsmith.countbook.COUNTERCOMMENT";
    public static final String EXTRA_COUNTERPOSITION = "com.atsmith.countbook.COUNTERPOSITION";
    public static final String FILENAME = "data.sav";

    private ArrayList<Counter> counters;
    private CounterArrayAdapter adapter;
    private ListView counterList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        counterList = (ListView) findViewById(R.id.counterList);
        loadData();
        adapter = new CounterArrayAdapter(this, counters);
        counterList.setAdapter(adapter);

    }

    /**
     * Called when the activity is started.
     *
     */
    @Override
    protected void onStart() {
        // TODO Auto-generated method stub
        TextView counterListTitle = (TextView) findViewById(R.id.counterListTitle);
        counterListTitle.setText("Counters (" + counters.size() + "): ");
        super.onStart();
    }

    @Override
    public void onBackPressed(){
        saveData();
        finish();
        return;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        if (resultCode == RESULT_OK) {
            if (requestCode == 1) {
                String name = null;
                String value = null;
                String comment = null;
                Log.d("onActivityResult", "received result");
                if (!data.hasExtra(EXTRA_HITSUBMIT)) {
                    return;
                }
                if (data.hasExtra(EXTRA_COUNTERNAME)) {
                    name = data.getExtras().getString(EXTRA_COUNTERNAME);
                }
                if (data.hasExtra(EXTRA_COUNTERINITVALUE)) {
                    value = data.getExtras().getString(EXTRA_COUNTERINITVALUE);
                }
                if (data.hasExtra(EXTRA_COUNTERCOMMENT)) {
                    comment = data.getExtras().getString(EXTRA_COUNTERCOMMENT);
                }
                if (name != null && value != null) {
                    Counter c = null;
                    if (data.hasExtra(EXTRA_COUNTERPOSITION)){
                        c = counters.get(Integer.parseInt(data.getExtras().getString(EXTRA_COUNTERPOSITION)));
                        if (c!=null){
                            c.setName(name);
                            c.setInitalValue(Integer.parseInt(value));
                            if (comment!= null){
                                c.setComment(comment);
                            }
                        }
                    }else {
                        try {
                            c = new Counter(name, Integer.parseInt(value), comment);
                        } catch (NegativeValueException e) {
                            e.printStackTrace();
                        }
                        if (c != null) {
                            counters.add(c);
                            adapter.notifyDataSetChanged();
                        }
                    }
                }
            }
        }
    }

    public void createCounter(View view){
        Intent intent = new Intent(this, CreateCounterActivity.class);
        startActivityForResult(intent, 1);
    }

    private void loadData(){
        try {
            FileInputStream fis = openFileInput(FILENAME);
            BufferedReader in = new BufferedReader(new InputStreamReader(fis));

            Gson gson = new Gson();

            //Taken from https://stackoverflow.com/questions/12384064/gson-convert-from-json-to-a-typed-arraylistt
            //2017-09-28
            Type listType = new TypeToken<ArrayList<Counter>>(){}.getType();
            counters = gson.fromJson(in,listType);
            in.close();
            fis.close();

        } catch (FileNotFoundException e) {
            counters = new ArrayList<Counter>();
        } catch (IOException e) {
            throw new RuntimeException();
        }

    }

    private void saveData(){
        try {
            FileOutputStream fos = openFileOutput(FILENAME, Context.MODE_PRIVATE);
            BufferedWriter out = new BufferedWriter(new OutputStreamWriter(fos));

            Gson gson = new Gson();
            gson.toJson(counters, out);
            out.flush();
            fos.close();
        } catch (FileNotFoundException e) {
            throw new RuntimeException();
        } catch (IOException e) {
            throw new RuntimeException();
        }
    }

}
