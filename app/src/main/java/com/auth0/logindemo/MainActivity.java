package com.auth0.logindemo;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import org.json.JSONArray;

import java.util.HashMap;
import java.util.Map;

//Borrowing some code examples from Arjunu for Recycler

public class MainActivity extends AppCompatActivity {

    static String TAG = "MAIN_ACTIVITY";

    public static final int INBOUND = 0;
    public static final int DONE = 1;
    public static final int BROADCAST = 2;

    private RecyclerView mRecyclerView;
    private CustomAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private String[] mDataset = {"29 degrees", "Seahawks 24 - 27 Bengals",
            "Flash missing, vanishes in crisis", "Half Life 3 announced"};

    SharedPreferences preferences;
    String currentEventString = "";
    private int mDataSetTypes[] = {INBOUND, INBOUND, BROADCAST, DONE}; //view types



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        mLayoutManager = new LinearLayoutManager(MainActivity.this);
        mRecyclerView.setLayoutManager(mLayoutManager);
//Adapter is created in the last step
        mAdapter = new CustomAdapter(mDataset, mDataSetTypes);
        mRecyclerView.setAdapter(mAdapter);

        //final TextView logo = (TextView) findViewById(R.id.logo);
        //logo.setText("activity started!");
        Log.i(TAG, "activity started!");
        
        setTitle("Together | Dashboard");

        preferences = PreferenceManager.getDefaultSharedPreferences(this);
        String user = preferences.getString("user", "");
        JsonObject jsonObject = new JsonParser().parse(user).getAsJsonObject();
        int id = jsonObject.get("id").getAsInt();
        Log.i(TAG, "fetched user id: " + id);

        getInvitations(id);

    }


    private boolean getInvitations(final int user_id) {
        boolean flag = false;
        // Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(this);
        String url = "http://phplaravel-19273-43928-180256.cloudwaysapps.com/post/invitations/all";

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d(TAG, "Invitation Response: " + response.toString());

                if (!response.equals("false")) {
                    //parse invitations and create card objects
                    try {
                        JSONArray arr = new JSONArray(response);
                        for (int i = 0; i < arr.length(); i++) {
                            String event_id = arr.getJSONObject(i).getString("event_id");
                            //stored in global currentEventString variable
                            getEvent(Integer.parseInt(event_id));

                            Log.i(TAG, currentEventString);

                        }
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }


                } else {
                    Log.d(TAG, "Invitation fetch failed failed: " + response.toString());
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                System.out.println("volley Error ................." + volleyError.getMessage());
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("user_id", "" + user_id);
                return params;
            }
        };

        queue.add(stringRequest);

        return flag;

    }

    private boolean getEvent(final int event_id) {
        // Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(this);
        String url = "http://phplaravel-19273-43928-180256.cloudwaysapps.com/post/events/singleEvent";

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d(TAG, "Event Response: " + response.toString());

                if (!response.equals("false")) {
                    currentEventString = response;
                } else {
                    Log.d(TAG, "Event fetch failed : " + response.toString());
                    currentEventString = "false";
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                System.out.println("volley Error ................." + volleyError.getMessage());
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("event_id", "" + event_id);
                return params;
            }
        };

        queue.add(stringRequest);

        return false;

    }
}



