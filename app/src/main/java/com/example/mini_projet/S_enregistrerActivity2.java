package com.example.mini_projet;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;

public class S_enregistrerActivity2 extends AppCompatActivity {

    private EditText somme;
    private ImageButton next;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_s_enregistrer2);
        somme=findViewById(R.id.somme);
        next=findViewById(R.id.next);
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(TextUtils.isEmpty(somme.getText().toString())) {
                    somme.setError("La somme est obligatoire.");
                    return;
                }
                Intent i=new Intent(S_enregistrerActivity2.this,PageActivity.class);
                i.putExtra("somme",somme.getText());
                startActivity(i);
            }
        });
    }
}