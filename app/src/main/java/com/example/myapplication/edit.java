package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.ProgressiveMediaSource;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.FirebaseFirestoreSettings;
import com.google.firebase.firestore.MetadataChanges;
import com.google.firebase.firestore.QuerySnapshot;

public class edit extends AppCompatActivity {
Button deleteBtn;
    EditText ed1;
    EditText ed2;
    EditText ed3;
    EditText ed4;
    EditText ed5;
    Button btn;
    FirebaseFirestore db;
    FirebaseFirestoreSettings firebaseFirestoreSettings;
    String id;

    private String videoURL;
    PlayerView playerView;
    private SimpleExoPlayer player;
    private boolean playWhenReady = true;
    private int currentWindow = 0;
    private long playbackPosition = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);
        deleteBtn = findViewById(R.id.deleteBtn);


        ed2 = findViewById(R.id.name);
        ed3 = findViewById(R.id.code);
        ed4 = findViewById(R.id.price);
        ed5 = findViewById(R.id.desc);

        btn = findViewById(R.id.button2);

        Intent i = getIntent();
       id   =  i.getStringExtra("id");
        String name = i.getStringExtra("name");
        String code = i.getStringExtra("code");
        String price = i.getStringExtra("price");
        String desc = i.getStringExtra("desc");

        ed2.setText(name);
        ed3.setText(code);
        ed4.setText(price);
        ed5.setText(desc);

        if (name.equalsIgnoreCase("laptop")){
            videoURL = "https://firebasestorage.googleapis.com/v0/b/my-first-lap.appspot.com/o/Aspire%205%20Laptop%20-%20Powerful%2C%20Everyday%20Computing%20at%20Your%20Side%20_%20Acer.mp4?alt=media&token=88589f81-79db-4378-9b8f-83a5e5c138a7";
        }else if(name.equalsIgnoreCase("ipad")){
            videoURL = "https://firebasestorage.googleapis.com/v0/b/my-first-lap.appspot.com/o/iPad%207th%20generation%202019%20hands-on%20first%20impressions.mp4?alt=media&token=b230577a-d287-4dba-96c9-304e0e46264c";
        }

        db = FirebaseFirestore.getInstance();
        firebaseFirestoreSettings = new FirebaseFirestoreSettings.Builder().setPersistenceEnabled(true).setCacheSizeBytes(FirebaseFirestoreSettings.CACHE_SIZE_UNLIMITED).build();


        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = ed2.getText().toString();
                String code = ed3.getText().toString();
                String price = ed4.getText().toString();
                String desc = ed5.getText().toString();
                db.collection("info")
                        .document(id)
                        .update("name",name, "code",code,"price",price , "desc",desc);

               offline();
                Intent iii = new Intent(getApplicationContext(),MainActivity.class);
                startActivity(iii);
                }
        });

        deleteBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                db.collection("info").document(id).delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Intent ii = new Intent(getApplicationContext(),MainActivity.class);
                        startActivity(ii);
                        offline();

                    }
                });


            }
        });



        ActivityCompat.requestPermissions(edit.this,
                new String[]{Manifest.permission.INTERNET, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE},
                100);

        playerView = findViewById(R.id.video_view);
    }

     void offline() {
         db.collection("info").whereEqualTo("id", id).addSnapshotListener(MetadataChanges.INCLUDE, new EventListener<QuerySnapshot>() {

             public void onEvent(QuerySnapshot queryDocumentSnapshots, FirebaseFirestoreException e) {
                 if (e != null) {

                 }
                 for (DocumentChange documentChange : queryDocumentSnapshots.getDocumentChanges()) {
                     if (documentChange.getType() == DocumentChange.Type.ADDED) {
                     }
                 }
             }
         });

     }

    private void initializePlayer() {
        player = ExoPlayerFactory.newSimpleInstance(this);
        playerView.setPlayer(player);

        Uri uri = Uri.parse(videoURL);
        MediaSource mediaSource = buildMediaSource(uri);

        player.setPlayWhenReady(playWhenReady);
        player.seekTo(currentWindow, playbackPosition);
        player.prepare(mediaSource, false, false);
    }

    private void releasePlayer() {
        if (player != null) {
            playWhenReady = player.getPlayWhenReady();
            playbackPosition = player.getCurrentPosition();
            currentWindow = player.getCurrentWindowIndex();
            player.release();
            player = null;
        }
    }

    private MediaSource buildMediaSource(Uri uri) {
        DataSource.Factory dataSourceFactory =
                new DefaultDataSourceFactory(this, "exoplayer-codelab");
        return new ProgressiveMediaSource.Factory(dataSourceFactory)
                .createMediaSource(uri);
    }

    @Override
    public void onStart() {
        super.onStart();
        if (Util.SDK_INT >= 24) {
            initializePlayer();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if ((Util.SDK_INT < 24 || player == null)) {
            initializePlayer();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (Util.SDK_INT < 24) {
            releasePlayer();
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        if (Util.SDK_INT >= 24) {
            releasePlayer();
        }
    }
}
