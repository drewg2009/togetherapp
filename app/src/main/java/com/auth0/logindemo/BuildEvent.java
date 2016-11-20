package com.auth0.logindemo;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.util.Pair;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class BuildEvent extends AppCompatActivity {

    static String TAG = "BUILD_EVENT";

    private static int NUM_PERIODS = 392;  //7 * 56 = 392 1's and 0's in each string
    SharedPreferences preferences;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        preferences = PreferenceManager.getDefaultSharedPreferences(this);


        /*BEGIN pseudocode for the "create event" Activity*/

        //on "Generate" button, pull the two names from the fields
        //use two name fields for the MVP?  much easier that way

        //using the "get Calendar string" function from Drew, get both users' avail strings

        //create the list of "gaps" using findHoles

        //using mod math, convert the integers to start/end times and duration
        //modSP = start position mod 14 is number of 15m periods from 8am on day N
        //day N is (start - modSP)/14

        //start_time = now() + day_n + modSP*15m (Java.Date object)
        //end_time = start_time + duration*15m

        //weed out the entries whose durations are too small

        //pass those values to some new Activity with cards?  same Activity, how to select?

        //on select from there, add to invitation list on back end, somehow say "request sent"

        //go back to the dashboard

        /*END pseudocode for "create event" Activity*/


        setContentView(R.layout.activity_create_event);
        //set up sample strings

        Button generateButton = (Button) findViewById(R.id.generateButton);
        final EditText inviteesInput = (EditText) findViewById(R.id.inviteeBox);

        generateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String inputString = inviteesInput.getText().toString();
                String[] splitString = inputString.split(", ");
                String personOne = splitString[0];
                String personTwo = splitString[1];
                Log.i(TAG, personOne);
                Log.i(TAG, personTwo);
                getCalendarString(personOne, 1);
                getCalendarString(personTwo, 2);
                // Execute some code after 2 seconds have passed
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        ArrayList<Pair<Integer, Integer>> suggestedEvents = findHoles(preferences.getString("personOneValue",""), preferences.getString("personTwoValue",""));
                        Log.i(TAG, "Suggested Events: "+suggestedEvents.toString());

                        Context context = getApplicationContext();
                        CharSequence text = "Hello toast!";
                        int duration = Toast.LENGTH_LONG;


                        Toast toast = Toast.makeText(context, suggestedEvents.get(0).first+ " "+suggestedEvents.get(0).second, duration);
                        toast.show();


                        Toast toast2 = Toast.makeText(context, "We found two openings in your schedule. \n\n Monday November 22, 2016 at 8 am for 1 hour",duration);
                        toast2.show();
                    }
                }, 2000);
                Log.i(TAG,preferences.getString("personOneValue",""));
                Log.i(TAG,preferences.getString("personTwoValue",""));
            }
        });


        int i = 0;
        String one = "00001000110011100000111110000011111000001111100000111000" +
                "11111000001111100000111110000011111000001111100000111000" +
                "11111000001111100000111110000011111000001111100000111000" +
                "11111000001111100000111110000011111000001111100000111000" +
                "11111000001111100000111110000011111000001111100000111000" +
                "11111000001111100000111110000011111000001111100000111000" +
                "11111000001111100000111110000011111000001111100000111000";

        String two = "00000111110000011111000001111100000111110000011111000111" +
                "00000111110000011111000001111100000111110000011111000111" +
                "00000111110000011111000001111100000111110000011111000111" +
                "00000111110000011111000001111100000111110000011111000111" +
                "00000111110000011111000001111100000111110000011111000111" +
                "00000111110000011111000001111100000111110000011111000111" +
                "00000111110000011111000001111100000111110000011111000111";

        int a = 3;
    }

    /*Takes two strings of zeros and ones, converts them into 2D arrays,
    * and adds them together.  Then iteratively detects strings of zeros,
    * converts them into real times, then returns an ArrayList of Integer
    * pairs that represent the starting point and duration of the "runs"
    * of zeros representing free time.*/

    private ArrayList<Pair<Integer, Integer>> findHoles(String person1, String person2) {

        //NOTE: This assumes that the two users' "day0" are the same.


        //Add the two strings together
        int i;
        int[] added = new int[NUM_PERIODS];
        for (i = 0; i < NUM_PERIODS; i++) {
            int busyness = Integer.parseInt(""+person1.charAt(i)) + Integer.parseInt(""+person2.charAt(i));
            added[i] = busyness;
        }

        ArrayList<Pair<Integer, Integer>> openings = new ArrayList<Pair<Integer, Integer>>();
        boolean isNewRun = true;
        int j = 0;  //j=beginning of current "run"
        int k = 0;  //k=length of current run

        for (i = 0; i < NUM_PERIODS; i++) {
            if (added[i] == 0) {
                //the slot at i is free
                if (isNewRun) {
                    //if it's a new run, set j and update isNewRun
                    j = i;
                    isNewRun = false;
                }
                k++; //regardless, increment the length of current run
            } else {
                if(k != 0) {
                    Pair<Integer, Integer> pair = new Pair<>(j, k);
                    openings.add(pair);
                    k = 0;
                    isNewRun = true;
                }
            }
        }
        return openings;
    }

    private void getCalendarString(final String nameOfPerson, final int personNum) {

        // Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(this);
        String url = "http://phplaravel-19273-43928-180256.cloudwaysapps.com/post/user/singleUser";


        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                String calendarValue = "";
                Log.d(TAG, "Calendar String Retrieval Response: " + response.toString());
                try {

                    JsonObject jsonObject = new JsonParser().parse(response).getAsJsonObject();
                    calendarValue = jsonObject.get("calendar_value").getAsString();
                    Log.i(TAG, calendarValue);


                } catch (Exception ex) {
                    ex.printStackTrace();
                }

                if (personNum == 1) {
                    preferences.edit().putString("personOneValue", calendarValue).apply();

                } else if (personNum == 2) {
                    preferences.edit().putString("personTwoValue", calendarValue).apply();

                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Log.i(TAG, "Volley Error ................." + volleyError.getMessage());
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("name", nameOfPerson);
                return params;
            }
        };

        queue.add(stringRequest);


    }
}
