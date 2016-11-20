package com.auth0.logindemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
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

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d(TAG, "Login Response: " + response.toString());

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
                params.put("fullName", fullName);
                params.put("broadcaster", broadcaster);
                return params;
            }
        };


        return flag;
    }
}
