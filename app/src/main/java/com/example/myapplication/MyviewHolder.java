package com.example.myapplication;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

public class MyviewHolder extends RecyclerView.ViewHolder {

    TextView nameTv;
    Button deleteBtn;
    Button editBtn;

    public MyviewHolder( View itemView) {
        super(itemView);
        nameTv=itemView.findViewById(R.id.nameTv);
        deleteBtn=itemView.findViewById(R.id.deleteBtn);

    }
}
