package com.example.github;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONException;
import org.json.JSONObject;

public class profileActivity extends AppCompatActivity {

    ImageView profile;
    TextView follower,following,bio;
    String user;
    String url="https://api.github.com/users/";
    RecyclerView mRecyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        profile=(ImageView)findViewById(R.id.profileimage);
        follower=(TextView)findViewById(R.id.followers);
        following=(TextView)findViewById(R.id.following);
        bio=(TextView)findViewById(R.id.bio);
        mRecyclerView=(RecyclerView)findViewById(R.id.recyclerview);
        user=getIntent().getStringExtra("Username");
        url=url+user;
        loaddata(url);
        loadrepository(url);
    }
    public void loaddata(String url)
    {
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            String imageurl=response.getString("avatar_url");
                            String followersc=response.getString("followers");
                            String followingc=response.getString("following");
                            String bioc=response.getString("bio");
                            follower.setText("FOLLOWERS: "+followersc);
                            following.setText("FOLLOWING: "+followingc);
                            bio.setText("BIO:  "+bioc);
                            Glide.with(profileActivity.this).load(imageurl).into(profile);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(profileActivity.this, "Unable to Fetch to data", Toast.LENGTH_SHORT).show();
                    }
                });
        MySingleton.getInstance(profileActivity.this).addToRequestQueue(jsonObjectRequest);
    }
    public void loadrepository(String url)
    {
        url=url+"/repos?per_page=100&sort=created";
        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        GsonBuilder gsonBuilder=new GsonBuilder();
                        Gson gson=gsonBuilder.create();
                        Repository[] repoarray=gson.fromJson(response,Repository[].class);
                        RepositoryAdapter repositoryAdapter=new RepositoryAdapter(repoarray,profileActivity.this);
                        mRecyclerView.setLayoutManager(new LinearLayoutManager(profileActivity.this));
                        mRecyclerView.setAdapter(repositoryAdapter);
                        mRecyclerView.setHasFixedSize(true);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(profileActivity.this, "Unable to fetch the data", Toast.LENGTH_SHORT).show();
            }
        });
        MySingleton.getInstance(profileActivity.this).addToRequestQueue(stringRequest);
    }
}