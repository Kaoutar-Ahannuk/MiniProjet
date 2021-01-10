package com.example.mini_projet;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

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
    }
}