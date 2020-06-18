package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.FirebaseFirestoreSettings;
import com.google.firebase.firestore.MetadataChanges;
import com.google.firebase.firestore.QuerySnapshot;

public class AddProduct extends AppCompatActivity {
    FirebaseFirestore db;
    FirebaseFirestoreSettings firebaseFirestoreSettings;
    EditText ed1;
    EditText ed2;
    EditText ed3;
    EditText ed4;
    EditText ed5;
    Button btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_product);

        final Intent i = getIntent();



        ed1 = findViewById(R.id.id);
        ed2 = findViewById(R.id.name);
        ed3 = findViewById(R.id.code);
        ed4 = findViewById(R.id.price);
        ed5 = findViewById(R.id.desc);

        btn = findViewById(R.id.button2);
        db = FirebaseFirestore.getInstance();
        firebaseFirestoreSettings = new FirebaseFirestoreSettings.Builder().setPersistenceEnabled(true).setCacheSizeBytes(FirebaseFirestoreSettings.CACHE_SIZE_UNLIMITED).build();

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = ed1.getText().toString();
                String code = ed2.getText().toString();
                String price = ed3.getText().toString();
                String desc = ed4.getText().toString();
                String id = ed5.getText().toString();
                proudcts em = new proudcts();
                em.setCode(code);
                em.setName(name);
                em.setDesc(desc);
                em.setPrice(price);
                em.setId(id);
                db.collection("info").document(id).set(em).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        offline();
                    }
                });

                Intent i = new Intent(getApplicationContext(),MainActivity.class);
                startActivity(i);
            }
        });
    }


    private void offline() {
        db.collection("info").whereEqualTo("id", ed4.getText().toString()).addSnapshotListener(MetadataChanges.INCLUDE, new EventListener<QuerySnapshot>() {

            public void onEvent(QuerySnapshot queryDocumentSnapshots, FirebaseFirestoreException e) {
                if (e != null) {

                }
                for (DocumentChange documentChange : queryDocumentSnapshots.getDocumentChanges()) {
                    if (documentChange.getType() == DocumentChange.Type.ADDED) {
                    }
                }
                String src = queryDocumentSnapshots.getMetadata().isFromCache() ? "local" : "server";
            }
        });
    }

}
