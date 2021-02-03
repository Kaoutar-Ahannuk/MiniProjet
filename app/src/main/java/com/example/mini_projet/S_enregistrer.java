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
    private EditText editTextNomComplet,editTextEmial,editTextPassword,editTextConfirmPassword;
    private Button buttonSenregistrer;
    private FirebaseAuth fAuth;
    private FirebaseFirestore fStore;
    private String userID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_s_enregistrer);

        editTextNomComplet = findViewById(R.id.editTextNomComplet);
        editTextEmial = findViewById(R.id.Email);
        editTextPassword = findViewById(R.id.editTextPassword);
        editTextConfirmPassword = findViewById(R.id.editTextConfirmPassword);
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
                final String email = editTextEmial.getText().toString().trim();
                String password = editTextPassword.getText().toString().trim();
                String confirmpassword = editTextConfirmPassword.getText().toString().trim();
                final String NomComplet = editTextNomComplet.getText().toString();

                if(TextUtils.isEmpty(email)){
                    editTextNomComplet.setError("Nom Complet est obligatoire.");
                    return;
                }
                if(TextUtils.isEmpty(email)){
                    editTextEmial.setError("L'adresse Email est obligatoire.");
                    return;
                }

                if(TextUtils.isEmpty(password)){
                    editTextPassword.setError("Le mot de passe est obligatoire.");
                    return;
                }

                if(TextUtils.isEmpty(password)){
                    editTextConfirmPassword.setError("Confirmer le mot de passe est obligatoire.");
                    return;
                }

                if(password.length() < 6){
                    editTextPassword.setError("Le mot de passe doit etre  >= 6 Characters");
                    return;
                }

                if(confirmpassword != password){
                    editTextConfirmPassword.setError("Le mot de passe est faux");
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
                            user.put("NomComplet",NomComplet);
                            user.put("email",email);
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
                            startActivity(new Intent(getApplicationContext(),S_enregistrerActivity2.class));

                        }else {
                            Toast.makeText(S_enregistrer.this, "Error ! " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
         });
    }
}

