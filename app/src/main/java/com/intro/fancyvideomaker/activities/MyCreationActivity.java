package com.intro.fancyvideomaker.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.intro.fancyvideomaker.R;
import com.intro.fancyvideomaker.adapters.MyCreationAdapter;

import java.io.File;
import java.io.FilenameFilter;

public class MyCreationActivity extends AppCompatActivity {

    private RecyclerView myCreationRecycler;
    MyCreationAdapter myCreationAdapter;
    Context context;
    File[] allFiles;
    private TextView notaxt;

    boolean fromVideoPlayer = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_creation);
        context = this;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            getWindow().setNavigationBarColor(getColor(R.color.bg4));
        }

        myCreationRecycler = findViewById(R.id.myCreationRecycler);
        ImageView ivoption = findViewById(R.id.ivoption);
        ImageView ivback = findViewById(R.id.ivback);

        notaxt = findViewById(R.id.notaxt);
        ((TextView) findViewById(R.id.my_header_text)).setText("My Creation");
        ivoption.setVisibility(View.GONE);

        getVideos();

        fromVideoPlayer = getIntent().getBooleanExtra("fromVideoMaker", false);

        if (allFiles.length == 0) {
            notaxt.setVisibility(View.VISIBLE);
        }

        myCreationAdapter = new MyCreationAdapter(context, allFiles);
        myCreationRecycler.setLayoutManager(new LinearLayoutManager(context));
        myCreationRecycler.setAdapter(myCreationAdapter);

        ivback.setOnClickListener(view -> onBackPressed());
    }

    private void getVideos() {
        //        stringBuilder.append(Environment.getExternalStorageDirectory().toString());
        String stringBuilder = getExternalMediaDirs()[0] +
                "/Introspect - Intro,Outro Video Maker/";
        File file = new File(stringBuilder);
        file.mkdirs();

        Log.i("DATA", "getImages: " + file);

        allFiles = file.listFiles((dir, name) -> {
            Log.i("DATA", "accept: " + name);
            Log.i("DATA", "accept: " + dir);
            return name.endsWith(".mp4");
        });
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onBackPressed() {
        if (fromVideoPlayer) {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            finish();
        } else {
            super.onBackPressed();
        }
    }
}
