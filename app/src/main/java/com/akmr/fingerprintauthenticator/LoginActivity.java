package com.akmr.fingerprintauthenticator;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.nio.charset.StandardCharsets;

public class LoginActivity extends AppCompatActivity {

    private static final String TAG = "LoginActivity";

    private EditText userNameEditText;
    private EditText passwordEditText;
    private Button loginBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        userNameEditText = findViewById(R.id.uname_et);
        passwordEditText = findViewById(R.id.pwd_et);
        loginBtn = findViewById(R.id.loginbtn);

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String userName = userNameEditText.getText().toString();
                String password = passwordEditText.getText().toString();

                /*if(userName.equals("anmolkmr") && password.equals("1234")){
                    Toast.makeText(LoginActivity.this, "Login Success", Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(LoginActivity.this, "Login Failed", Toast.LENGTH_SHORT).show();
                }*/
                login(userName, password);
            }
        });
    }

    private void login(String userName, String password) {
        Log.d(TAG, "login: in login" + userName + " : " + password);
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        String url = "http://192.168.29.50:80/umgmt/login_action.php";
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("user_name", userName);
            jsonObject.put("password", password);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        String reqBody = jsonObject.toString();

        Response.Listener<String> loginResponse = new Response.Listener<String>(){

            @Override
            public void onResponse(String response) {
                Toast.makeText(LoginActivity.this, response, Toast.LENGTH_SHORT).show();
                Log.d(TAG, "onResponse: " + response);
            }
        };


        Response.ErrorListener errorResponse = new Response.ErrorListener(){

            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(LoginActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                Log.d(TAG, "onErrorResponse: " + error.getMessage());
            }
        };

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, loginResponse, errorResponse);
        requestQueue.add(stringRequest);
    }
}