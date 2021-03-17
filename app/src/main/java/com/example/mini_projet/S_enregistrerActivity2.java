package com.example.mini_projet;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
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
    private EditText somme;
    private ImageButton next;
    private Button btn_out;
    private ListView list1,list2;
    private FirebaseAuth fAuth;
    private FirebaseFirestore fStore;
    private String userID;
    //variables select
    TextView select,select2;
    boolean[] selectedDepense,selectedRessource;
    ArrayList<String> depenseList = new ArrayList<>();
    ArrayList<String> ressourceList = new ArrayList<>();
    String[] depenseArray = {"Maison","food","Medicament","Telephone","Eau et electricite","sport","argent de poche","Autres"};
    String[] ressourceArray = {"Salaire","Bourse","Autres"};
    ArrayAdapter adapter,adapter2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_s_enregistrer2);

        somme = findViewById(R.id.somme);
        next = findViewById(R.id.next);
        btn_out = findViewById(R.id.out);
        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();
        select = findViewById(R.id.select);
        list1=findViewById(R.id.list1);
        select2 = findViewById(R.id.select2);
        list2=findViewById(R.id.list2);


        //initialize selected depense array and ressource array
        selectedDepense = new boolean[depenseArray.length];
        selectedRessource = new boolean[ressourceArray.length];

        select.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //initialize alert dialog
                AlertDialog.Builder builder = new AlertDialog.Builder(
                        S_enregistrerActivity2.this
                );

                builder.setTitle("Selectionner vos depenses");
                builder.setCancelable(false);
                builder.setMultiChoiceItems(depenseArray, selectedDepense, new DialogInterface.OnMultiChoiceClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i, boolean b) {
                        if(b){
                            depenseList.add(depenseArray[i]);
                            Collections.sort(depenseList);
                        }else {
                            depenseList.remove(depenseArray[i]);
                        }
                    }
                });

                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        /*StringBuilder stringBuilder = new StringBuilder();
                        for(int j=0; j<depenseList.size(); j++){
                            stringBuilder.append(depenseArray[depenseList.get(j)]);
                            if(j != depenseList.size()-1){
                                stringBuilder.append(", ");
                            }
                        }

                        select.setText(stringBuilder.toString());
                        */
                        adapter =new ArrayAdapter(S_enregistrerActivity2.this,R.layout.element,R.id.t1,depenseList);
                        list1.setAdapter(adapter);
                    }
                });

                builder.setNegativeButton("Annuler", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                });

                builder.setNeutralButton("Supprimer tous", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        for(int j=0; j<selectedDepense.length; j++) {
                            selectedDepense[j] = false;
                            list1.removeViewAt(j);
                            adapter.notifyDataSetChanged();
                        }
                    }
                });

                builder.show();
            }
        });
        select2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //initialize alert dialog
                AlertDialog.Builder builder = new AlertDialog.Builder(
                        S_enregistrerActivity2.this
                );

                builder.setTitle("Selectionner vos ressources");
                builder.setCancelable(false);
                builder.setMultiChoiceItems(ressourceArray, selectedRessource, new DialogInterface.OnMultiChoiceClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i, boolean b) {
                        if(b){
                            ressourceList.add(ressourceArray[i]);
                            Collections.sort(ressourceList);
                        }else {
                            ressourceList.remove(ressourceArray[i]);
                        }
                    }
                });

                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        /*StringBuilder stringBuilder = new StringBuilder();
                        for(int j=0; j<depenseList.size(); j++){
                            stringBuilder.append(depenseArray[depenseList.get(j)]);
                            if(j != depenseList.size()-1){
                                stringBuilder.append(", ");
                            }
                        }

                        select.setText(stringBuilder.toString());
                        */
                        adapter2 =new ArrayAdapter(S_enregistrerActivity2.this,R.layout.element,R.id.t1,ressourceList);
                        list2.setAdapter(adapter2);
                    }
                });

                builder.setNegativeButton("Annuler", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                });

                builder.setNeutralButton("Supprimer tous", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        for(int j=0; j<selectedRessource.length; j++) {
                            selectedRessource[j] = false;
                            list2.removeViewAt(j);
                            adapter2.notifyDataSetChanged();
                        }
                    }
                });

                builder.show();
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