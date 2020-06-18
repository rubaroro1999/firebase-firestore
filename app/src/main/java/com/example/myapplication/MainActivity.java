package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.FirebaseFirestoreSettings;
import com.google.firebase.firestore.MetadataChanges;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
      FirebaseFirestore db;
    FirebaseFirestoreSettings firebaseFirestoreSettings;
    Button btn;
    ArrayList<proudcts> bookname;
    FirebaseAnalytics mFirebaseAnalytics;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);
        trackScreen();
        bookname = new ArrayList<>();
         RecyclerView iv=findViewById(R.id.recycler);
        final myAdapter adapter=new myAdapter(bookname,R.layout.viewer);


        LinearLayoutManager llm=new LinearLayoutManager(getApplicationContext());
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        db = FirebaseFirestore.getInstance();
        btn = findViewById(R.id.button);

        iv.setAdapter(adapter);
        iv.setLayoutManager(llm);
        DividerItemDecoration dd=new DividerItemDecoration(getApplicationContext(),DividerItemDecoration.VERTICAL);
        iv.addItemDecoration(dd);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(),AddProduct.class);
                startActivity(i);
            }
        });
        db.collection("info").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
                public void onEvent(QuerySnapshot queryDocumentSnapshots, FirebaseFirestoreException e) {
                    for (DocumentChange doc : queryDocumentSnapshots.getDocumentChanges()) {
                    if (doc.getType() == DocumentChange.Type.ADDED) {
                        proudcts b = doc.getDocument().toObject(proudcts.class);
                        bookname.add(b);
                        adapter.notifyDataSetChanged();
                        offline();
                    }
                }
            }
        });

       firebaseFirestoreSettings = new FirebaseFirestoreSettings.Builder().setPersistenceEnabled(true).setCacheSizeBytes(FirebaseFirestoreSettings.CACHE_SIZE_UNLIMITED).build();

        iv.addOnItemTouchListener(new RecyclerTouchListener(getApplicationContext(), iv, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                proudcts p = bookname.get(position);
                final String id  = p.getId();

                final String name  = p.getName();
                final String code  = p.getCode();
                final String price  = p.getPrice();
                final String desc  = p.getDesc();
                Intent i = new Intent(getApplicationContext(),edit.class);
                i.putExtra("id",id);

                i.putExtra("name",name);
               i.putExtra("code",code);
               i.putExtra("price",price);
              i.putExtra("desc", desc);
                startActivity(i);
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));
    }
    private void trackScreen () {
        mFirebaseAnalytics.setCurrentScreen(this, "MainActivity", null);
    }
     void offline() {
        db.collection("info").whereEqualTo("id", "1").addSnapshotListener(MetadataChanges.INCLUDE, new EventListener<QuerySnapshot>() {

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



