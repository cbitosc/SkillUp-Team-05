package com.example.myprofilepage;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Login extends AppCompatActivity implements View.OnClickListener {

    TextView login_title_txt,enter_roll_txt,enter_password_txt,dont_txt,reg_txt,login_txt;
    EditText roll_edt,password_edt;
    static int logged_in=0;


    Typeface regular,bold;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        if(logged_in==1){
            Intent intent = new Intent(Login.this,EligibleCompanies.class);
            startActivity(intent);
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        regular = Typeface.createFromAsset(getAssets(), "OpenSans-Regular.ttf");
        bold = Typeface.createFromAsset(getAssets(), "OpenSans-Bold.ttf");

        login_title_txt = findViewById(R.id.login_title_txt);
        login_title_txt.setTypeface(bold);
        enter_roll_txt = findViewById(R.id.enter_roll_txt);
        enter_roll_txt.setTypeface(regular);
        enter_password_txt = findViewById(R.id.enter_password_txt);
        enter_password_txt.setTypeface(regular);
        dont_txt = findViewById(R.id.dont_txt);
        dont_txt.setTypeface(regular);
        reg_txt = findViewById(R.id.reg_txt);
        reg_txt.setTypeface(bold);
        reg_txt.setOnClickListener(this);
        login_txt = findViewById(R.id.login_txt);
        login_txt.setTypeface(bold);
        login_txt.setOnClickListener(this);

        roll_edt = findViewById(R.id.roll_edt);
        roll_edt.setTypeface(regular);
        password_edt = findViewById(R.id.password_edt);
        password_edt.setTypeface(regular);

    }

    @Override
    public void onClick(View v) {

        if (v == reg_txt){

            Intent intent = new Intent(Login.this,RegistrationActivity.class);
            startActivity(intent);

        }

        if (v == login_txt){

            String url = "https://placementcosc.herokuapp.com/login";

            JsonObjectRequest ObjRequest = new JsonObjectRequest((Request.Method.POST), url, null,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {

                            try {

                                if(response.getJSONObject("message").toString()=="Invalid Credentials") {
                                    Toast.makeText(Login.this, "Invalid Credentials, please try again.", Toast.LENGTH_SHORT).show();
                                }
                                else if(response.getJSONObject("message").toString()=="There was an error cannot connect" || response.getJSONObject("message").toString()=="No company hires you"){
                                    Toast.makeText(Login.this, "There was an error.Please try again after some time.", Toast.LENGTH_SHORT).show();
                                }
                                else{

                                    final String rollno=roll_edt.toString().trim();
                                    final String password=password_edt.toString().trim();

                                    EligibleCompanies.Roll_no=rollno;
                                    MainActivity.Roll_no=rollno;
                                    company.rollno=rollno;
                                    EligibleCompanies.Password=password;
                                    logged_in=1;

                                    Intent intent = new Intent(Login.this, EligibleCompanies.class);
                                    startActivity(intent);
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {

                            error.getMessage();
                        }
                    }) {
                @Override
                protected Map<String, String> getParams() {
                    Map<String, String> params = new HashMap<String, String>();
                    params.put("roll_no", roll_edt.getText().toString());
                    params.put("password", password_edt.getText().toString());
                    return params;
                }
            };
            ObjRequest.setRetryPolicy(new DefaultRetryPolicy(500000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
            RequestQueue requestQueue = Volley.newRequestQueue(Login.this);
            requestQueue.add(ObjRequest);

        }

    }
}