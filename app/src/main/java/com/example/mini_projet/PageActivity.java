package com.example.mini_projet;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

//import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
//import com.firebase.ui.firestore.FirestoreRecyclerOptions;

public class PageActivity extends AppCompatActivity {

    private static final String TAG = "test";

    private TextView somme1, reste1, depense1;
    private Button voirPlus, ajout, btn_out, buttonVoir;
    private RecyclerView mStoreList;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    private String userID=user.getUid();
    //  FirestoreRecyclerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_page);
        somme1 = findViewById(R.id.somme1);
        reste1 = findViewById(R.id.reste1);
        depense1 = findViewById(R.id.depense1);
        voirPlus = findViewById(R.id.voirPlus);
        ajout = findViewById(R.id.ajout);
        btn_out = findViewById(R.id.out);
        buttonVoir = findViewById(R.id.buttonVoir);
        mStoreList = findViewById(R.id.fireStoreList);

        DocumentReference docRef= db.collection("users").document(userID);
        docRef.get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        if (documentSnapshot.exists()) {
                            String somme = documentSnapshot.getString("SommeRessources");
                            Log.d(TAG, "DocumentSnapshot data: " + documentSnapshot.getData());
                            somme1.setText(somme);
                        } else {
                            Log.d(TAG, "No such document");
                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d(TAG, "get failed with ");

                    }
                });

/*
        buttonVoir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Query
                Query query=fStore.collection("users");
                //Recycler Options
                FirestoreRecyclerOptions<Model> options=new FirestoreRecyclerOptions.Builder<Model>()
                        .setQuery(query,Model.class)
                        .build();
                adapter= new FirestoreRecyclerAdapter<Model, MViewHolder>(options) {
                    @NonNull
                    @Override
                    public MViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.listitem,parent,false);
                        return new MViewHolder(view);
                    }

                    @Override
                    protected void onBindViewHolder(@NonNull MViewHolder holder, int position, @NonNull Model model) {
                        //for(int i=0; i<7; i++) {
                            holder.t1.setText(model.getListDepenses().get(0));
                            holder.e1.setText(model.getListDepensesValues().get(0));
                        //}
                    }
                };
                mStoreList.setHasFixedSize(true);
                mStoreList.setLayoutManager(new LinearLayoutManager(PageActivity.this));
                mStoreList.setAdapter(adapter);
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

    private class MViewHolder extends RecyclerView.ViewHolder {
        private TextView t1;
        private EditText e1;
        public MViewHolder(@NonNull View itemView) {
            super(itemView);

            t1=itemView.findViewById(R.id.t1);
            e1=itemView.findViewById(R.id.e1);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        adapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        adapter.stopListening();
    }

 */
    }
}