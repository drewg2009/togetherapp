package com.auth0.logindemo;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class Login extends AppCompatActivity {

    Button loginButton;
    Button signupButton;
    Button devExit;
    static String TAG = "LOGIN";
    SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        preferences = PreferenceManager.getDefaultSharedPreferences(this);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        ////////////////////////////////////////////////////////////
        devExit = (Button) findViewById(R.id.devExit);
        final Intent bypass = new Intent(this, MainActivity.class);
        devExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(bypass);
            }
        });
        /////TO BE REMOVED///////////////////////////////////////////


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
        //startActivity(main); //IGNORING THE LOGIN

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
