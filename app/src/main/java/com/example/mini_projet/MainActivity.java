package com.example.mini_projet;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    private Button Senregistrer;
    private Button SeConnecter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Senregistrer = findViewById(R.id.Senregistrer);
        SeConnecter = findViewById(R.id.SeConnecter);

        Senregistrer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),S_enregistrer.class));

            }
        });

        SeConnecter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),ConnectActivity.class));

            }
        });
    }
}
