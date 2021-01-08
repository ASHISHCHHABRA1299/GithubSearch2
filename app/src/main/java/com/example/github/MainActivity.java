package com.example.github;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {


    EditText usernm;
    Button searchbtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        usernm=(EditText)findViewById(R.id.username);
        searchbtn=(Button)findViewById(R.id.search);
        searchbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username=usernm.getText().toString();
                Intent i=new Intent(MainActivity.this,profileActivity.class);
                i.putExtra("Username",username); //user=get from the edittext
                startActivity(i);
            }
        });

    }
}