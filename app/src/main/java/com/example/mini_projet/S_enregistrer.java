package com.example.mini_projet;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class S_enregistrer extends AppCompatActivity {
    public static final String TAG = "TAG";
    EditText NomComplet,Emial,Password,ConfirmPassword;
    private Button buttonSenregistrer;
    private FirebaseAuth fAuth;
    private FirebaseFirestore fStore;
    private String userID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_s_enregistrer);

        NomComplet = findViewById(R.id.NomComplet);
        Emial = findViewById(R.id.Email);
        Password = findViewById(R.id.Password);
        ConfirmPassword = findViewById(R.id.ConfirmPassword);
        buttonSenregistrer = findViewById(R.id.buttonSenregistrer);

        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();

        if(fAuth.getCurrentUser() != null){
            startActivity(new Intent(getApplicationContext(),S_enregistrerActivity2.class));
            finish();
        }
        buttonSenregistrer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String email = Emial.getText().toString().trim();
                String password = Password.getText().toString().trim();
                String confirmpassword = ConfirmPassword.getText().toString().trim();
                final String CompletNom = NomComplet.getText().toString();

                if(TextUtils.isEmpty(CompletNom)){
                    NomComplet.setError("Nom Complet est obligatoire.");
                    return;
                }
                if(TextUtils.isEmpty(email)){
                    Emial.setError("L'adresse Email est obligatoire.");
                    return;
                }

                if(TextUtils.isEmpty(password)){
                    Password.setError("Le mot de passe est obligatoire.");
                    return;
                }

                if(TextUtils.isEmpty(confirmpassword)){
                    ConfirmPassword.setError("Confirmer le mot de passe est obligatoire.");
                    return;
                }

                if(password.length() < 6){
                    Password.setError("Le mot de passe doit etre  >= 6 Characters");
                    return;
                }

                if(confirmpassword != password){
                    ConfirmPassword.setError("Le mot de passe est faux");
                }


                // register the user in firebase

                fAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){

                            // send verification link

                            FirebaseUser fuser = fAuth.getCurrentUser();
                            fuser.sendEmailVerification().addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Toast.makeText(S_enregistrer.this, "L'email de vérification est envoyé à votre compte.", Toast.LENGTH_SHORT).show();
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Log.d(TAG, "onFailure: Email not sent " + e.getMessage());
                                }
                            });

                            Toast.makeText(S_enregistrer.this, "User Created.", Toast.LENGTH_SHORT).show();
                            userID = fAuth.getCurrentUser().getUid();
                            DocumentReference documentReference = fStore.collection("users").document(userID);
                            Map<String,Object> user = new HashMap<>();
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
                            Intent i=new Intent(getApplicationContext(),S_enregistrerActivity2.class);
                           // i.putExtra("Nom_Complet",CompletNom);
                            i.putExtra("email",email);
                            startActivity(i);

                        }else {
                            Toast.makeText(S_enregistrer.this, "Error ! " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
         });
    }

}

