package com.example.filetest;

import androidx.appcompat.app.AppCompatActivity;

import android.net.Uri;
import android.os.Bundle;

import com.github.barteksc.pdfviewer.PDFView;

import java.io.File;

public class PdfViewerActivity extends AppCompatActivity {
    PDFView pdfView;
    String filePath="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pdf_viewer);
        pdfView=findViewById(R.id.pdfView);
        filePath=getIntent().getStringExtra("path");
        File file=new File(filePath);
        Uri uri=Uri.fromFile(file);

        pdfView.fromUri(uri).load();
    }
}