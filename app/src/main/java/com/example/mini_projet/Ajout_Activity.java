package com.example.mini_projet;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.nfc.Tag;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Ajout_Activity extends AppCompatActivity {
    private static final String TAG = "test";
    private Button buutonVoirD;
    private ListView listView;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    private String userID=user.getUid();
    List<String> Val =new ArrayList();
    List<String> Check =new ArrayList();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ajout);

        listView=findViewById(R.id.listView);
        buutonVoirD=findViewById(R.id.buttonVoirD);

        DocumentReference docRef= db.collection("users").document(userID);
        docRef.get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        if (documentSnapshot.exists()) {
                            Map<String,String> dep= (Map<String, String>) documentSnapshot.get("depences");
                            Log.d(TAG, "DocumentSnapshot data depenses: " + dep);
                            Check.addAll(dep.keySet());
                            Val.addAll(dep.values());
                            Log.d(TAG, "List Checked: " + Check + "and list Values: " + Val);
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

        buutonVoirD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CustomAdapter customAdapter = new CustomAdapter(getApplicationContext(), Check, Val);
                listView.setAdapter(customAdapter);
            }
        });
    }

    private class CustomAdapter extends BaseAdapter {
        Context context;
        List<String> Check;
        List<String> Val;

        public CustomAdapter(Context applicationContext, List<String> Check, List<String> Val) {
            this.context = applicationContext;
            this.Check = Check;
            this.Val = Val;
        }
        @Override
        public int getCount() {
            return Check.size();
        }

        @Override
        public Object getItem(int i) {
            return null;
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }

        @Override
        public View getView(int i, View convertView, ViewGroup viewGroup) {
            final View result;

            if (convertView == null) {
                result = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.listitem, viewGroup, false);
            } else {
                result = convertView;
            }
            TextView check2 = (TextView)result.findViewById(R.id.chcked);
            TextView value = (TextView) result.findViewById(R.id.value);
            check2.setText(Check.get(i));
            value.setText(String.valueOf(Val.get(i)));
            return result;
        }

    }
}