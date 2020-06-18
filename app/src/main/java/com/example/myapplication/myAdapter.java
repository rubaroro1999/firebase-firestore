package com.example.myapplication;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreSettings;

import java.util.ArrayList;

public class myAdapter extends RecyclerView.Adapter<MyviewHolder> {
    FirebaseFirestore db;
    FirebaseFirestoreSettings firebaseFirestoreSettings;

    ArrayList<proudcts> persons;
    int itemLayoutId;

     public myAdapter(ArrayList<proudcts> persons , int itemLayoutId ){
         this.persons=persons;
         this.itemLayoutId=itemLayoutId;
     }

    @Override
    public MyviewHolder onCreateViewHolder( ViewGroup viewGroup, int position) {
        View v=LayoutInflater.from(viewGroup.getContext()).inflate(itemLayoutId, viewGroup ,false);
         MyviewHolder vh=new MyviewHolder(v);
        db = FirebaseFirestore.getInstance();
        return vh;
    }

    @Override
    public void onBindViewHolder( MyviewHolder myviewHolder, final int position) {
        final proudcts p=persons.get(position);
         myviewHolder.nameTv.setText(p.getName());


        firebaseFirestoreSettings = new FirebaseFirestoreSettings.Builder().setPersistenceEnabled(true).setCacheSizeBytes(FirebaseFirestoreSettings.CACHE_SIZE_UNLIMITED).build();


    }

    @Override
    public int getItemCount()
    {
        return persons.size();
    }


    }

