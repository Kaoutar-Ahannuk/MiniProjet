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
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.example.mini_projet.S_enregistrer;

public class S_enregistrerActivity2 extends AppCompatActivity {

    public static final String TAG = "TAG";
    private TextView somme,somme2;
    private ImageButton next;
    private Button btn_out;
    private FirebaseAuth fAuth;
    private FirebaseFirestore fStore;
    private String userID;
    private CheckBox checkSalaire,checkBourse,checkAutres,checkFood,checkTelephone,checkMedicament,checkAutres2,checkArgentDePoche,checkEauElectr,checkSport;
    private EditText salaire,bourse,autres,Telephone,autres2,food,eauElect,sport,argentPoche,Medicament;
    private Button buttonSomme,buttonSomme2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_s_enregistrer2);

        somme = findViewById(R.id.somme);
        somme2 = findViewById(R.id.somme2);
        next = findViewById(R.id.next);
        btn_out = findViewById(R.id.out);
        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();

        checkSalaire = findViewById(R.id.checkSalaire);checkFood = findViewById(R.id.checkFood);
        checkBourse = findViewById(R.id.checkBourse);checkSport = findViewById(R.id.checkSport);
        checkAutres = findViewById(R.id.checkAutres);checkEauElectr = findViewById(R.id.checkEauElectr);
        checkMedicament = findViewById(R.id.checkMedicament);checkArgentDePoche = findViewById(R.id.ArgentDePoche);
        checkAutres2 = findViewById(R.id.checkAutres2);checkTelephone = findViewById(R.id.checkTelephone);

        salaire = findViewById(R.id.salaire);Telephone = findViewById(R.id.Telephone);
        bourse = findViewById(R.id.bourse);food = findViewById(R.id.food);
        autres = findViewById(R.id.autres);autres2 = findViewById(R.id.autres2);
        argentPoche= findViewById(R.id.argentPoche2);Medicament = findViewById(R.id.argentPoche);
        sport = findViewById(R.id.Sport);eauElect = findViewById(R.id.eauElect);

        buttonSomme = findViewById(R.id.buttonSomme);
        buttonSomme2 = findViewById(R.id.buttonSomme2);

        userID = fAuth.getCurrentUser().getUid();
        final DocumentReference documentReference = fStore.collection("users").document(userID);
        final Map<String,Object> user = new HashMap<>();
        final Map<String, String> cheked = new HashMap<>();

        final String[] listChecked = new String[3];
        final String[] listCheckedValues = new String[3];

        buttonSomme.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int sum=0;
                if(checkSalaire.isChecked()){
                    int sal = Integer.parseInt(salaire.getText().toString());
                    sum += sal;
                    listChecked[0]= String.valueOf(checkSalaire.getText());
                    listCheckedValues[0]= String.valueOf(salaire.getText());

                }
                if(checkBourse.isChecked()){
                    int bours = Integer.parseInt(bourse.getText().toString());
                    sum += bours;
                    listChecked[1]= String.valueOf(checkBourse.getText());
                    listCheckedValues[1]= String.valueOf(bourse.getText());
                }
                if(checkAutres.isChecked()){
                    int autre = Integer.parseInt(autres.getText().toString());
                    sum += autre;
                    listChecked[2]= String.valueOf(checkAutres.getText());
                    listCheckedValues[2]= String.valueOf(autres.getText());
                }
                somme.setText(String.valueOf(sum));
                /*for (int i=0; i<listChecked.length;i++){
                    cheked.put(listChecked[i],listCheckedValues[i]);
                }*/
                }
        });
        buttonSomme2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int sum=0;
                if(checkTelephone.isChecked()){
                    int tel = Integer.parseInt(Telephone.getText().toString());
                    sum += tel;
                }
                if(checkFood.isChecked()){
                    int fod = Integer.parseInt(food.getText().toString());
                    sum += fod;
                }
                if(checkAutres2.isChecked()){
                    int autre = Integer.parseInt(autres2.getText().toString());
                    sum += autre;
                }
                if(checkMedicament.isChecked()){
                    int medic = Integer.parseInt(Medicament.getText().toString());
                    sum += medic;
                }
                if(checkSport.isChecked()){
                    int sp = Integer.parseInt(sport.getText().toString());
                    sum += sp;
                }
                if(checkEauElectr.isChecked()){
                    int eauE = Integer.parseInt(eauElect.getText().toString());
                    sum += eauE;
                }
                if(checkArgentDePoche.isChecked()){
                    int argent = Integer.parseInt(argentPoche.getText().toString());
                    sum += argent;
                }
                somme2.setText(String.valueOf(sum));
            }
        });



        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent x=getIntent();
                String sumRess = somme.getText().toString();
                String sumDepenses= somme2.getText().toString();
                List<String> tags = Arrays.asList(listChecked);
                List<String> tags2 = Arrays.asList(listCheckedValues);


                //user.put("Nom Complet",x.getStringExtra("Nom_Complet"));
                user.put("email",x.getStringExtra("email"));
                user.put("Somme Ressources",sumRess);
                user.put("Somme Depenses",sumDepenses);
                user.put("listChecked",tags);
                user.put("listValues",tags2);

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
                Intent i=new Intent(S_enregistrerActivity2.this,PageActivity.class);
                //i.putExtra("sommeRessources",sumRess);
                //i.putExtra("sommeDepenses",sumDepenses);
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