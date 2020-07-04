package com.example.myprofilepage;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class RegistrationActivity extends AppCompatActivity implements View.OnClickListener {

    TextView reg_title_txt, enter_roll_txt, enter_dept_txt, enter_first_txt, enter_last_txt, enter_gpa_txt, enter_password_txt,
            enter_date_txt, enter_email_txt, enter_number_txt, enter_sem_txt, reg_txt;
    EditText roll_edt, dept_edt, first_edt, last_edt, gpa_edt, password_edt, date_edt, email_edt, number_edt, sem_edt;

    Typeface regular, bold;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        regular = Typeface.createFromAsset(getAssets(), "OpenSans-Regular.ttf");
        bold = Typeface.createFromAsset(getAssets(), "OpenSans-Bold.ttf");

        reg_title_txt = findViewById(R.id.reg_title_txt);
        reg_title_txt.setTypeface(bold);
        enter_roll_txt = findViewById(R.id.enter_roll_txt);
        enter_roll_txt.setTypeface(regular);
        enter_password_txt = findViewById(R.id.enter_password_txt);
        enter_password_txt.setTypeface(regular);
        enter_dept_txt = findViewById(R.id.enter_dept_txt);
        enter_dept_txt.setTypeface(regular);
        enter_first_txt = findViewById(R.id.enter_first_txt);
        enter_first_txt.setTypeface(regular);
        reg_txt = findViewById(R.id.reg_txt);
        reg_txt.setTypeface(regular);
        reg_txt.setOnClickListener(this);
        enter_last_txt = findViewById(R.id.enter_last_txt);
        enter_last_txt.setTypeface(regular);
        enter_gpa_txt = findViewById(R.id.enter_gpa_txt);
        enter_gpa_txt.setTypeface(regular);
        enter_date_txt = findViewById(R.id.enter_date_txt);
        enter_date_txt.setTypeface(regular);
        enter_email_txt = findViewById(R.id.enter_email_txt);
        enter_email_txt.setTypeface(regular);
        enter_number_txt = findViewById(R.id.enter_number_txt);
        enter_number_txt.setTypeface(regular);
        enter_sem_txt = findViewById(R.id.enter_sem_txt);
        enter_sem_txt.setTypeface(regular);

        roll_edt = findViewById(R.id.roll_edt);
        roll_edt.setTypeface(regular);
        password_edt = findViewById(R.id.password_edt);
        password_edt.setTypeface(regular);
        dept_edt = findViewById(R.id.dept_edt);
        dept_edt.setTypeface(regular);
        first_edt = findViewById(R.id.first_edt);
        first_edt.setTypeface(regular);
        last_edt = findViewById(R.id.last_edt);
        last_edt.setTypeface(regular);
        gpa_edt = findViewById(R.id.gpa_edt);
        gpa_edt.setTypeface(regular);
        date_edt = findViewById(R.id.date_edt);
        date_edt.setTypeface(regular);
        email_edt = findViewById(R.id.email_edt);
        email_edt.setTypeface(regular);
        number_edt = findViewById(R.id.number_edt);
        number_edt.setTypeface(regular);
        sem_edt = findViewById(R.id.sem_edt);
        sem_edt.setTypeface(regular);
    }

    @Override
    public void onClick(View v) {

        if (v == reg_txt){

            String url = "https://placementcosc.herokuapp.com/placesignup";

            StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {

                            Intent intent = new Intent(RegistrationActivity.this,EligibleCompanies.class);
                            startActivity(intent);
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
                    params.put("dept_name", dept_edt.getText().toString());
                    params.put("first_name", first_edt.getText().toString());
                    params.put("last_name", last_edt.getText().toString());
                    params.put("gpa", gpa_edt.getText().toString());
                    params.put("password", password_edt.getText().toString());
                    params.put("dateregst", date_edt.getText().toString());
                    params.put("stemail", email_edt.getText().toString());
                    params.put("stphno", number_edt.getText().toString());
                    params.put("currentsem", sem_edt.getText().toString());
                    return params;
                }
            };
            stringRequest.setRetryPolicy(new DefaultRetryPolicy(500000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
            RequestQueue requestQueue = Volley.newRequestQueue(RegistrationActivity.this);
            requestQueue.add(stringRequest);

        }

    }
}
