package com.healthcare.a2040.healthcarepartner.ui_activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
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
import com.healthcare.a2040.healthcarepartner.R;
import com.healthcare.a2040.healthcarepartner.app_utils.AppConstants;

import java.util.HashMap;
import java.util.Map;

public class SignUpActivity extends SuperCompatActivity
        implements View.OnClickListener {

    private Context mContext;
    private EditText userName, userEmail, userPassword;
    String name, email, password;
    private Button btnClick;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        userName = findViewById(R.id.idNameR1);
        userEmail = findViewById(R.id.idemailR);
//        userPassword = findViewById(R.id.idpwdletR);
        btnClick = findViewById(R.id.signup);
        btnClick.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.signup:

                showProgressDialog(null, "Signing Up...", false);

                StringRequest stringRequest = new StringRequest(Request.Method.POST, AppConstants.URL_SignUp,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                Toast.makeText(SignUpActivity.this, response, Toast.LENGTH_LONG).show();
                                removeProgressDialog();
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Toast.makeText(SignUpActivity.this, error.toString(), Toast.LENGTH_LONG).show();
                                Log.e("error", error.toString());
                                removeProgressDialog();
                            }
                        }) {

                    @Override
                    public String getBodyContentType() {
                        return "application/x-www-form-urlencoded; charset=UTF-8";
                    }

                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String, String> params = new HashMap<String, String>();
                        params.put("email", userEmail.getText().toString());
                        params.put("password", userPassword.getText().toString());
                        params.put("name", userName.getText().toString());
                        Log.e("params", params.toString());
                        return params;
                    }

                };
                RequestQueue requestQueue = Volley.newRequestQueue(mContext);
                requestQueue.add(stringRequest);

                break;
            case R.id.tv_Login_here:
                startActivity(new Intent(mctx, LoginActivity.class));
                break;
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        finish();
        return super.onOptionsItemSelected(item);
    }
}