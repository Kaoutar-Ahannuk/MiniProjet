package com.example.mini_projet;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.auth.FirebaseAuth;

public class PageActivity extends AppCompatActivity {
    EditText somme1, reste1, depense1;
    Button voirPlus, ajout,btn_out;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_page);
        somme1=findViewById(R.id.somme1);
        reste1=findViewById(R.id.reste1);
        depense1=findViewById(R.id.somme1);
        voirPlus=findViewById(R.id.voirPlus);
        ajout=findViewById(R.id.ajout);
        btn_out=findViewById(R.id.out);

        somme1.setText(getIntent().getStringExtra("somme"));


        btn_out.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();//logout
                startActivity(new Intent(getApplicationContext(),MainActivity.class));
                finish();
            }
        });
    }
}