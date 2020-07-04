package com.example.myprofilepage;

import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class company extends AppCompatActivity {

    public static String compname,rollno,jobname;
    public String URL;
    private Button apply;
    public JSONObject data;



    private TextView companyname;
    private TextView description;


    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_company);

        companyname=findViewById(R.id.companyname);
        description=findViewById(R.id.desc);
        apply=findViewById(R.id.button2);

        URL="https://placementcosc.herokuapp.com/regstud";


        JsonObjectRequest objectRequest=new JsonObjectRequest(Request.Method.GET, URL, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try{
                            companyname.setText(response.getJSONObject("company_name").toString());
                            description.setText(response.getJSONObject("job_description").toString());

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }) {

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("company_name" , compname);
                params.put("job_name" , jobname);
                return params;

            }
        };

        data=new JSONObject();
        try
            {
            data.put("roll_no",rollno);
            data.put("company_name",compname);
            data.put("dateregco",java.time.LocalTime.now());

            }
        catch (JSONException e)
            {
            e.printStackTrace();
            }

        apply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                JsonObjectRequest obj=new JsonObjectRequest(Request.Method.POST,
                        URL,
                        data,
                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {

                                try
                                {
                                String success=response.getJSONObject("message").toString();

                                if (success.equals("successfully registered.")){
                                    Toast.makeText(company.this, "Application sent!", Toast.LENGTH_SHORT).show();
                                    finish();
                                }
                                else if(success.equals("Student with that info already registered")){
                                    Toast.makeText(company.this, "Already applied", Toast.LENGTH_SHORT).show();
                                }
                                else{
                                    Toast.makeText(company.this, "Error occured. Please try again.", Toast.LENGTH_SHORT).show();
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                                Toast.makeText(company.this, "Error in saving data, please try again."+ e.toString(), Toast.LENGTH_SHORT).show();

                            }

                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {

                                Toast.makeText(company.this, "Error in saving data, please try again."+ error.toString(), Toast.LENGTH_SHORT).show();

                            }
                        });

            }
        });

        RequestQueue requestQueue= Volley.newRequestQueue(this);
        requestQueue.add(objectRequest);
    }

}