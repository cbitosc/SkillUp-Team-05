package com.example.myprofilepage;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class EligibleCompanies extends AppCompatActivity {

    private static String Company_URL = "https://placementcosc.herokuapp.com/login";
    public static String Roll_no,Password;
    private RecyclerView recyclerView;
    private ArrayList job_name,com_name,job_desc,job_req;
    private FloatingActionButton edit;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_eligible_companies);

        recyclerView=findViewById(R.id.recycler);
        edit=findViewById(R.id.edit);

        JsonArrayRequest ArrayReq= new JsonArrayRequest(Request.Method.POST,
                Company_URL,
                null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        try{

                            if(response.getJSONObject(0).getString("message")=="there was an error cannot connect" || response.getJSONObject(0).getString("message")=="No company hires you" ){
                                Toast.makeText(EligibleCompanies.this, "No company available right now for you.", Toast.LENGTH_SHORT).show();
                            }
                            else{
                                com_name= new ArrayList();
                                job_name=new ArrayList();
                                job_desc=new ArrayList();
                                job_req=new ArrayList();
                                for (int i = 0; i < response.length(); i++){
                                    JSONObject result = response.getJSONObject(i);
                                    com_name.set(i, result.getString("company_name"));
                                    job_name.set(i, result.getString("job_name"));
                                    job_desc.set(i, result.getString("job_description"));
                                    job_req.set(i, result.getString("job_requirements"));
                                }

                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        Toast.makeText(EligibleCompanies.this, "Error in saving data, please try again."+ error.toString(), Toast.LENGTH_SHORT).show();
                    }
                })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("roll_no",Roll_no);
                params.put("password",Password);
                return params;
            }
        };

        Adapter Adapter=new Adapter(EligibleCompanies.this,job_name,com_name);

        recyclerView.setAdapter(Adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        Adapter.SetOnItemClickListener(new Adapter.OnItemClickListener() {
            @Override
            public void OnItemClick(int position) {
                company.compname= com_name.get(position).toString().trim();
                company.jobname=job_name.get(position).toString().trim();
                Intent intent = new Intent(EligibleCompanies.this,company.class);
                startActivity(intent);
            }
        });

        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent toEdit=new Intent(EligibleCompanies.this,MainActivity.class);
                startActivity(toEdit);
            }
        });

    }

}