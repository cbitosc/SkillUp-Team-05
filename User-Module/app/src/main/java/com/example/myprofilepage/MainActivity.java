package com.example.myprofilepage;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

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


public class MainActivity extends AppCompatActivity {

    public static String Roll_no;
    private TextView rollno,branch;
    private EditText FirstName, LastName, Email, phnno, semester , cgpa;

    //Enter the posting and retrieving url here
    private static String URL = "https://placementcosc.herokuapp.com/userupdatein";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        rollno = findViewById(R.id.rollno);
        FirstName=findViewById(R.id.FirstName);
        LastName=findViewById(R.id.LastName);
        Email=findViewById(R.id.email);
        phnno=findViewById(R.id.phn);
        semester=findViewById(R.id.semester);
        cgpa=findViewById(R.id.cgpa);
        branch=findViewById(R.id.branch);

        Button save = findViewById(R.id.SaveButton);

        rollno.setText(Roll_no);

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                savedata();
            }
        });


        RequestQueue queue = Volley.newRequestQueue(this);




        JsonObjectRequest ObjRequest = new JsonObjectRequest(Request.Method.GET,
                URL,
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            rollno.setText(response.getJSONObject("roll_no").toString());
                            FirstName.setText(response.getJSONObject("first_name").toString());
                            LastName.setText(response.getJSONObject("last_name").toString());
                            Email.setText(response.getJSONObject("stemail").toString());
                            phnno.setText(response.getJSONObject("stphno").toString());
                            semester.setText(response.getJSONObject("currentsem").toString());
                            cgpa.setText(response.getJSONObject("gpa").toString());
                            branch.setText(response.getJSONObject("dept_name").toString());
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(MainActivity.this, "Cannot find data", Toast.LENGTH_SHORT).show();
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(MainActivity.this, "Please try again.", Toast.LENGTH_SHORT).show();
                    }
                }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("rollno", Roll_no);
                return params;
            }
        };

        queue.add(ObjRequest);

    }

    private void savedata(){
        final String Mrollno= this.rollno.getText().toString().trim();
        final String Mfirstname= this.FirstName.getText().toString().trim();
        final String Mlastname= this.LastName.getText().toString().trim();
        final String Memail= this.Email.getText().toString().trim();
        final String Mphn= this.phnno.getText().toString().trim();
        final String Msem= this.semester.getText().toString().trim();
        final String Mcgpa= this.cgpa.getText().toString().trim();

        JsonObjectRequest ObjReq1= new JsonObjectRequest(Request.Method.POST,
                URL,
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            String success=response.getJSONObject("message").toString();

                            if (success.equals("success updated.")){
                                Toast.makeText(MainActivity.this, "Details updated successfully!", Toast.LENGTH_SHORT).show();
                                finish();
                            }
                            else if(success.equals("No details for updation.")){
                                Toast.makeText(MainActivity.this, "Please fill all the details", Toast.LENGTH_SHORT).show();
                            }
                            else{
                                Toast.makeText(MainActivity.this, "Please fill all the details", Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(MainActivity.this, "Error in saving data, please try again."+ e.toString(), Toast.LENGTH_SHORT).show();

                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        Toast.makeText(MainActivity.this, "Error in saving data, please try again."+ error.toString(), Toast.LENGTH_SHORT).show();
                    }
                })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();
                params.put("rollno",Mrollno);
                params.put("FirstName",Mfirstname);
                params.put("LastName",Mlastname);
                params.put("Email",Memail);
                params.put("phnno",Mphn);
                params.put("semester",Msem);
                params.put("cgpa",Mcgpa);
                return params;
            }
        };

        RequestQueue requestQueue= Volley.newRequestQueue(this);
        requestQueue.add(ObjReq1);
    }

 }