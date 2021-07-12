package com.example.filetest;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class AddImageActivity extends AppCompatActivity {
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_image);


        askPermission();
        init();
        onClick();


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode==RESULT_OK){

            final Uri imageUri=data.getData();

            try {
                final InputStream imageStream=getContentResolver().openInputStream(imageUri);
                final Bitmap selectedImage= BitmapFactory.decodeStream(imageStream);
                imageView.setImageBitmap(selectedImage);
                // get selected image path
                originalImagePath=new File(imageUri.getPath().replace("raw/",""));

                uri=String.valueOf(originalImagePath);

            } catch (FileNotFoundException e) {
                e.printStackTrace();
                Toast.makeText(this, "Something went wrong", Toast.LENGTH_SHORT).show();
            }

        }else {
            Toast.makeText(this, "No image selected", Toast.LENGTH_SHORT).show();

        }
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
            imageView.setImageURI(Uri.parse(uri));
        }


        context=AddImageActivity.this;
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
                addImage();
            }
        });

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openGallery();
            }
        });



    }

    private void addImage(){

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

    private void openGallery(){
        Intent gallery=new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
        startActivityForResult(gallery,RESULT_IMAGE);

    }
    private void askPermission(){
        Dexter.withContext(this)
                .withPermissions(
                        Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE
                )
                .withListener(new MultiplePermissionsListener() {
                    @Override
                    public void onPermissionsChecked(MultiplePermissionsReport multiplePermissionsReport) {

                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(List<PermissionRequest> list, PermissionToken permissionToken) {
                        permissionToken.continuePermissionRequest();
                    }


                })
                .check();

    }
}