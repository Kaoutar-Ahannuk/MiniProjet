package com.example.mini_projet;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.renderscript.ScriptGroup;
import android.text.InputType;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import com.example.mini_projet.S_enregistrer;

public class S_enregistrerActivity2 extends AppCompatActivity {

    public static final String TAG = "TAG";
    private TextView somme;
    private ImageButton next;
    private Button btn_out;
    private FirebaseAuth fAuth;
    private FirebaseFirestore fStore;
    private String userID;
    private CheckBox checkSalaire,checkBourse,checkAutres;
    private EditText salaire,bourse,autres;
    private TextView textViewSomme;
    private Button buttonSomme;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_s_enregistrer2);

        somme = findViewById(R.id.somme);
        next = findViewById(R.id.next);
        btn_out = findViewById(R.id.out);
        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();

        checkSalaire = findViewById(R.id.checkSalaire);
        checkBourse = findViewById(R.id.checkBourse);
        checkAutres = findViewById(R.id.checkAutres);
        salaire = findViewById(R.id.salaire);
        bourse = findViewById(R.id.bourse);
        autres = findViewById(R.id.autres);
        textViewSomme = findViewById(R.id.textViewSomme);
        buttonSomme = findViewById(R.id.buttonSomme);

        buttonSomme.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int sum=0;
                if(checkSalaire.isChecked()){
                    int sal = Integer.parseInt(salaire.getText().toString());
                    sum += sal;
                    textViewSomme.setText("somme");
                    somme.setText(String.valueOf(sum));
                }
                if(checkBourse.isChecked()){
                    int bours = Integer.parseInt(bourse.getText().toString());
                    sum += bours;
                    textViewSomme.setText("somme");
                    somme.setText(String.valueOf(sum));
                }
                if(checkAutres.isChecked()){
                    int autre = Integer.parseInt(salaire.getText().toString());
                    sum += autre;
                    textViewSomme.setText("somme");
                    somme.setText(String.valueOf(sum));
                }

            }
        });



        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent x=getIntent();
                String sum = somme.getText().toString();

                userID = fAuth.getCurrentUser().getUid();
                DocumentReference documentReference = fStore.collection("users").document(userID);
                Map<String,Object> user = new HashMap<>();

                user.put("NomComplet",x.getStringExtra("Nom_Complet"));
                user.put("email",x.getStringExtra("email"));
                user.put("somme",sum);

                documentReference.set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d(TAG, "onSuccess: user Profile is created for "+ userID);
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d(TAG, "onFailure: " + e.toString());
                    }
                });
               /* if(TextUtils.isEmpty(somme.getText().toString())) {
                    somme.setError("La somme est obligatoire.");
                    return;
                }*/
                Intent i=new Intent(S_enregistrerActivity2.this,PageActivity.class);
                i.putExtra("somme",somme.getText().toString());
                startActivity(i);

            }
        });

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