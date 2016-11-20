package com.auth0.logindemo;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import static android.provider.ContactsContract.CommonDataKinds.Website.URL;

public class Login extends AppCompatActivity {

    Button loginButton;
    Button signupButton;
    static String TAG = "LOGIN";
    SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //TODO remove this later
        Intent i=new Intent(this, CreateEvent.class);
        startActivity(i);
        finish();

        preferences = PreferenceManager.getDefaultSharedPreferences(this);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        loginButton = (Button) findViewById(R.id.loginButton);
        signupButton = (Button) findViewById(R.id.signupButton);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login();
            }
        });

        //to signup page
        final Intent signup = new Intent(this, Signup.class);
        signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(signup);
            }
        });
    }

    private boolean login() {

        boolean flag = false;

        EditText emailText = (EditText) findViewById(R.id.email);
        EditText passwordText = (EditText) findViewById(R.id.password);

        final String email = emailText.getText().toString();
        final String password = passwordText.getText().toString();

        Log.i(TAG, email);
        Log.i(TAG, password);



        // Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(this);
        String url = "http://phplaravel-19273-43928-180256.cloudwaysapps.com/post/users/login";

        final Intent main=new Intent(this, MainActivity.class);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d(TAG, "Login Response: " + response.toString());

                if(!response.equals("false")){
                    //storing user info locally on device
                    SharedPreferences.Editor editor = preferences.edit();
                    editor.putString("user", response);
                    editor.apply();
                    startActivity(main);
                    finish();
                }
                else {
                    Log.d(TAG, "Login failed: " + response.toString());
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
                params.put("email", email);
                params.put("password", password);
                return params;
            }
        };

        queue.add(stringRequest);

        return flag;
    }

}
