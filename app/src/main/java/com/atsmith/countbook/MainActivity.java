package com.atsmith.countbook;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ArrayList<Counter> counters;
    private ArrayAdapter<Counter> adapter;
    private ListView counterList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button addButton = (Button) findViewById(R.id.addCounter);
        counterList = (ListView) findViewById(R.id.counterList);

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setResult(RESULT_OK);
                Counter c = null;
                try {
                    c = new Counter("Test", 0, "comment");
                } catch (NegativeValueException e) {
                    e.printStackTrace();
                }
                counters.add(c);

                adapter.notifyDataSetChanged();
            }
        });
    }

    /**
     * Called when the activity is started.
     *
     */
    @Override
    protected void onStart() {
        // TODO Auto-generated method stub
        super.onStart();
        counters = new ArrayList<Counter>();
        adapter = new ArrayAdapter<Counter>(this,
                R.layout.list_item, counters);
        counterList.setAdapter(adapter);

    }
}
