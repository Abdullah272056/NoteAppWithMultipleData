package com.example.filetest;

import androidx.appcompat.app.AppCompatActivity;

import android.net.Uri;
import android.os.Bundle;

import com.github.chrisbanes.photoview.PhotoView;


public class ImageViewActivity extends AppCompatActivity {
    PhotoView imageView;
    String filePath;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_view);
        imageView=findViewById(R.id.imageView);
        filePath=getIntent().getStringExtra("uri");
        imageView.setImageURI(Uri.parse(filePath));

    }
}