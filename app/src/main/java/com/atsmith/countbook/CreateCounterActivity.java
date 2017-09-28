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

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

/**
 * Activity class for creating and editing counters.
 * This class is sent information via intents from the main
 * activity which it uses to allow the user to edit the name,
 * initial value, and comment of an existing counter. When
 * creating a new counter, now information (Extras) are sent
 * in the intent request. When finish() is called, either by
 * hitting Submit, Cancel, or the back button, this activity
 * returns data about the new/edited counter.
 * The main activity only uses the returned information if
 * the submit button was pressed (ie: if a certain extra was
 * passed), otherwise the info is ignored.
 *
 * @author atsmith
 * @version 1.0
 * @see MainActivity
 */
public class CreateCounterActivity extends AppCompatActivity {

    private EditText etCounterName;
    private EditText etCounterComment;
    private EditText etCounterInitValue;
    private boolean hitSubmit = false;
    private String counterPosition = null;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_counter);

        Button submitButton = (Button) findViewById(R.id.submitButton);
        Button cancelButton = (Button) findViewById(R.id.cancelButton);
        etCounterName = (EditText) findViewById(R.id.editCounterTitle);
        etCounterComment = (EditText) findViewById(R.id.editCounterComment);
        etCounterInitValue = (EditText) findViewById(R.id.editCounterInitValue);

        Bundle extras = getIntent().getExtras();
        if (extras!=null){
            if (extras.containsKey(MainActivity.EXTRA_COUNTERPOSITION)) {
                String name = extras.getString(MainActivity.EXTRA_COUNTERNAME);
                String val = extras.getString(MainActivity.EXTRA_COUNTERINITVALUE);
                String comment = extras.getString(MainActivity.EXTRA_COUNTERCOMMENT);
                counterPosition = extras.getString(MainActivity.EXTRA_COUNTERPOSITION);
                //Change the submit button text to Save as we are editing.
                submitButton.setText("Save");
                if (name != null) {
                    etCounterName.setText(name);
                }
                if (val != null) {
                    etCounterInitValue.setText(val);
                }
                if (val != null) {
                    etCounterComment.setText(comment);
                }
            }
        }

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                hitSubmit = true;
                finish();
            }
        });

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    @Override
    public void finish(){
        Intent data = new Intent();
        if (hitSubmit) {
            data.putExtra(MainActivity.EXTRA_HITSUBMIT, "true");
        }
        if (counterPosition!=null){
            data.putExtra(MainActivity.EXTRA_COUNTERPOSITION,counterPosition);
        }
        data.putExtra(MainActivity.EXTRA_COUNTERNAME, etCounterName.getText().toString());
        data.putExtra(MainActivity.EXTRA_COUNTERINITVALUE,etCounterInitValue.getText().toString());
        data.putExtra(MainActivity.EXTRA_COUNTERCOMMENT,etCounterComment.getText().toString());
        setResult(RESULT_OK, data);
        super.finish();
    }
}
