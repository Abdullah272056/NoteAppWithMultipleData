package com.example.filetest;


import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.getbase.floatingactionbutton.FloatingActionButton;

import java.io.File;
import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity implements NotesImageSelectListener{
    FloatingActionButton f_btn_addPdf,f_btn_addVoice,f_btn_addImage;
    Context context;
    ImageView image;
    EditText et_Name,et_Des;
    RecyclerView rv_notes;

    File path=new File(Environment.getExternalStorageDirectory().getAbsolutePath());
    public static final int RESULT_IMAGE=1;
    File originalImagePath,compressImagePath;

    private static String filePath;



    DatabaseHelperNotes databaseHelperImage;
    CustomAdapterNotes customAdapterNotesImage;

    List<NotesData> notesDataList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);






        init();
        onClick();
        loadingCanEat();

        filePath=path.getAbsolutePath();
        if (!path.exists()){
            path.mkdirs();
        }

    }



    @Override
    public void onNotesItemSelected(NotesData notesData) {
        if (notesData.getUri().endsWith(".jpg")||notesData.getUri().endsWith(".png")){
            Intent intent=new Intent(MainActivity.this,AddImageActivity.class);
            intent.putExtra("status", "old");
            intent.putExtra("iD", String.valueOf(notesData.getId()));
            intent.putExtra("title", notesData.getTitle());
            intent.putExtra("description", notesData.getDes());
            intent.putExtra("uri", notesData.getUri());
            startActivity(intent);
        }

        if (notesData.getUri().endsWith(".pdf")){
            Intent intent=new Intent(MainActivity.this,AddPdfActivity.class);
            intent.putExtra("status", "old");
            intent.putExtra("iD", String.valueOf(notesData.getId()));
            intent.putExtra("title", notesData.getTitle());
            intent.putExtra("description", notesData.getDes());
            intent.putExtra("uri", notesData.getUri());
            startActivity(intent);
        }


    }

    @Override
    public void onNotesItemImageSelected(NotesData notesData) {
        Toast.makeText(context, "image", Toast.LENGTH_SHORT).show();
        if (notesData.getUri().endsWith(".pdf")){
            startActivity(new Intent(MainActivity.this,PdfViewerActivity.class)
                    .putExtra("path",notesData.getUri()));
        }
        if (notesData.getUri().endsWith(".jpg")||notesData.getUri().endsWith(".png")){
            Intent intent=new Intent(MainActivity.this,ImageViewActivity.class);
            intent.putExtra("uri", notesData.getUri());
            startActivity(intent);

        }

    }

    void init(){
        f_btn_addImage=findViewById(R.id.f_btn_addImage);
        f_btn_addPdf=findViewById(R.id.f_btn_addPdf);
        f_btn_addVoice=findViewById(R.id.f_btn_addVoice);
        rv_notes=findViewById(R.id.rv_notes);

        context=MainActivity.this;
        notesDataList=new ArrayList<>();
        databaseHelperImage=new DatabaseHelperNotes(MainActivity.this);
        databaseHelperImage.getWritableDatabase();
    }

    void onClick(){
        f_btn_addImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this,AddImageActivity.class);
                intent.putExtra("status", "new");
                startActivity(intent);
               // addImage();
            }
        });
        f_btn_addPdf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this,AddPdfActivity.class);
                intent.putExtra("status", "new");
                startActivity(intent);
            }
        });
        f_btn_addVoice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // addImage();
            }
        });

    }



    void loadingCanEat(){
        notesDataList= databaseHelperImage.getAllNotes();
        if (notesDataList.size()>0){
            customAdapterNotesImage = new CustomAdapterNotes(context,notesDataList,this);
            rv_notes.setLayoutManager(new LinearLayoutManager(context));
            rv_notes.setAdapter(customAdapterNotesImage);
            customAdapterNotesImage.notifyDataSetChanged();
            Log.e("duri",notesDataList.get(0).uri);
        }
    }





}