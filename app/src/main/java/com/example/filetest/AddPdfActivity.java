package com.example.filetest;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.provider.OpenableColumns;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;
import com.karumi.dexter.listener.single.PermissionListener;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class AddPdfActivity extends AppCompatActivity implements OnPdfFileSelectListener{
    Context context;
    ImageView imageView;
    EditText et_Name,et_Des;
    RecyclerView rv_notes;
    Button btn_save,btn_delete;

    File path=new File(Environment.getExternalStorageDirectory().getAbsolutePath());
    File originalImagePath,compressImagePath;
    private static String filePath;

    public static final int RESULT_IMAGE=1;

    DatabaseHelperNotes databaseHelperImage;
    List<NotesData> imageDataList;
    String status,id,title,des,uri;

     AlertDialog alertDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_pdf);


        init();
        onClick();

    }

    void init(){

        et_Name=findViewById(R.id.et_name);
        et_Des=findViewById(R.id.et_Des);
        btn_save=findViewById(R.id.btn_save);
        btn_delete=findViewById(R.id.btn_delete);
        imageView=findViewById(R.id.imageView);

        status = getIntent().getStringExtra("status");

        if (status.equals("old")){
            id = getIntent().getStringExtra("iD");
            title = getIntent().getStringExtra("title");
            des = getIntent().getStringExtra("description");
            uri = getIntent().getStringExtra("uri");

            et_Name.setText(title);
            et_Des.setText(des);
            imageView.setImageResource(R.drawable.ic_pdf_file_icon);
        }


        context=AddPdfActivity.this;
        imageDataList=new ArrayList<>();
        databaseHelperImage=new DatabaseHelperNotes(context);
        databaseHelperImage.getWritableDatabase();



    }

    void onClick(){
        btn_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (status.equals("old")){
                    databaseHelperImage.deleteNotes(Integer.parseInt(id));
                    Intent intent=new Intent(context,MainActivity.class);
                    startActivity(intent);
                    finish();

                }else {
                    Toast.makeText(context, "data is empty", Toast.LENGTH_SHORT).show();
                }

            }
        });
        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addPdf();
            }
        });

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                showAllPdf();

//               Intent intent=new Intent(AddPdfActivity.this,AllPdfFileActivity.class);
//               startActivity(intent);
            }
        });



    }

    private void addPdf(){

        String title=et_Name.getText().toString();
        String des=et_Des.getText().toString();
        if (TextUtils.isEmpty(title)){
            et_Name.setError("Enter title");
            et_Name.requestFocus();
            return;
        }
        if (TextUtils.isEmpty(des)){
            et_Des.setError("Enter description");
            et_Des.requestFocus();
            return;
        }
        if (uri==null){
            Toast.makeText(context, "add image", Toast.LENGTH_SHORT).show();
            return;
        }


        if (status.equals("old")){

            //Log.e("uri",String.valueOf(originalImagePath));
            long sta=databaseHelperImage.updateNotes(new NotesData(Integer.parseInt(id),title,des,uri));
            if (sta==1){
                Intent intent=new Intent(context,MainActivity.class);
                startActivity(intent);
                finish();
                Toast.makeText(context, "update success", Toast.LENGTH_SHORT).show();

            }

        }else {
            long id=databaseHelperImage.insertNotes(new NotesData(title,des,uri));
            if (id>0){
                Intent intent=new Intent(context,MainActivity.class);
                startActivity(intent);
                finish();
                Toast.makeText(context, "add success", Toast.LENGTH_SHORT).show();
            }
        }



    }






    private void showAllPdf(){
        AlertDialog.Builder builder=new AlertDialog.Builder(context);
        LayoutInflater layoutInflater =LayoutInflater.from(context);
        View view=layoutInflater.inflate(R.layout.activity_all_pdf_file,null);
        builder.setView(view);
         alertDialog = builder.create();


      RecyclerView recyclerView=view.findViewById(R.id.recyclerViewId);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(this,2));

        List<File> pdfList=new ArrayList<>();
        pdfList.addAll(findPdf(Environment.getExternalStorageDirectory()));
       PdfAdapter pdfAdapter =new PdfAdapter(AddPdfActivity.this,pdfList,this);
        recyclerView.setAdapter(pdfAdapter);


        alertDialog.show();
    }

    public ArrayList<File> findPdf(File file){

        ArrayList<File> arrayList=new ArrayList<>();
        File[] files=file.listFiles();
        assert files != null;
        for (File singleFile: files ){
            if (singleFile.isDirectory() && !singleFile.isHidden()){
                arrayList.addAll(findPdf(singleFile));

            }else {
                if (singleFile.getName().endsWith(".pdf")){
                    arrayList.add(singleFile);
                }

            }
        }
        return arrayList;
    }

    @Override
    public void onPdfSelected(File file) {
        alertDialog.dismiss();
        uri=file.getAbsolutePath();
        imageView.setImageResource(R.drawable.ic_pdf_file_icon);

    }



}