package com.auth0.logindemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

//Borrowing some code examples from Arjunu for Recycler

public class MainActivity extends AppCompatActivity {

    static String TAG = "MAIN_ACTIVITY";

    public static final int UNSEEN = 0;
    public static final int SEEN = 1;
    public static final int ACCEPTED = 2;

    private RecyclerView mRecyclerView;
    private CustomAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private String[] mDataset = {"29 degrees", "Seahawks 24 - 27 Bengals",
            "Flash missing, vanishes in crisis", "Half Life 3 announced"};
    private int mDataSetTypes[] = {UNSEEN, SEEN, ACCEPTED, ACCEPTED}; //view types



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        mLayoutManager = new LinearLayoutManager(MainActivity.this);
//        mRecyclerView.setLayoutManager(mLayoutManager);
//Adapter is created in the last step
        mAdapter = new CustomAdapter(mDataset, mDataSetTypes);
//        mRecyclerView.setAdapter(mAdapter);

        //final TextView logo = (TextView) findViewById(R.id.logo);
        //logo.setText("activity started!");
        Log.i(TAG, "activity started!");


        // Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(this);
        String url = "http://localhost:8000/get/users";

// Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Display the first 500 characters of the response string.
                        //mTextView.setText("Response is: "+ response.substring(0,500));
                        Log.e(TAG, response);
                        //logo.setText(response);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //mTextView.setText("That didn't work!");
                Log.i(TAG, "that didn't work!");
            }
        });
// Add the request to the RequestQueue.
        queue.add(stringRequest);
    }
}
