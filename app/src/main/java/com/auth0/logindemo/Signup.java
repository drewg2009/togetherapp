package com.auth0.logindemo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
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

public class Signup extends AppCompatActivity {

    static String TAG = "SIGNUP";
    Button signupButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        signupButton = (Button)findViewById(R.id.signupButton);
        signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signup();
            }
        });

    }

    private boolean signup() {
        boolean flag = false;

        EditText fullNameText = (EditText) findViewById(R.id.signup_name);
        EditText emailText = (EditText) findViewById(R.id.signup_email);
        EditText passwordText = (EditText) findViewById(R.id.signup_password);
        CheckBox signupBroadcasterBox = (CheckBox) findViewById(R.id.signup_broadcaster);

        final String fullName = fullNameText.getText().toString();
        final String email = emailText.getText().toString();
        final String password = passwordText.toString();
        final String broadcaster = "" + signupBroadcasterBox.isChecked();

        // Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(this);
        String url = "http://phplaravel-19273-43928-180256.cloudwaysapps.com/post/users/signup";

        final Intent i = new Intent(this, MainActivity.class);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d(TAG, "Signup Response: " + response.toString());
                startActivity(i);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Log.i(TAG,"volley Error ................." + volleyError.getMessage());
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("email", email);
                params.put("password", password);
                params.put("name", fullName);
                params.put("broadcaster", broadcaster);
                params.put("calendar_value", "");
                params.put("calendar_last_updated", "");
                return params;
            }
        };

        queue.add(stringRequest);

        return flag;
    }
}
