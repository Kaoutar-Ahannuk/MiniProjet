package com.example.mini_projet;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

public class PageActivity extends AppCompatActivity {
    EditText somme1, reste1, depense1;
    Button voirPlus, ajout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_page);
        somme1=findViewById(R.id.somme1);
        reste1=findViewById(R.id.reste1);
        depense1=findViewById(R.id.somme1);
        voirPlus=findViewById(R.id.voirPlus);
        ajout=findViewById(R.id.ajout);
        somme1.setText(getIntent().getStringExtra("somme"));
    }
}