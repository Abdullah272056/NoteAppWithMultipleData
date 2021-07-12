package com.example.filetest;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;


public class DatabaseHelperNotes extends SQLiteOpenHelper {
    Context context;

    public DatabaseHelperNotes(@Nullable Context context) {
        super(context, ConstantNotes.TABLE_NAME_NOTES, null, ConstantNotes.DATABASE_VERSION);
        this.context=context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(ConstantNotes.CREATE_TABLE_IMAGE);
        Toast.makeText(context, "On created is called", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(" DROP TABLE IF EXISTS "+ ConstantNotes.TABLE_NAME_NOTES);
        onCreate(db);
    }


    public int insertNotes(NotesData notesData){
        SQLiteDatabase sqLiteDatabase=getWritableDatabase();
        ContentValues contentValues=new ContentValues();
        contentValues.put(ConstantNotes.COLUMN_TITLE,notesData.getTitle());
        contentValues.put(ConstantNotes.COLUMN_DESCRIPTION,notesData.getDes());
        contentValues.put(ConstantNotes.COLUMN_URI,notesData.getUri());

        int id= (int) sqLiteDatabase.insert(ConstantNotes.TABLE_NAME_NOTES,null,contentValues);
        return id;
    }

    public List<NotesData> getAllNotes(){
        SQLiteDatabase sqLiteDatabase = getReadableDatabase();
        List<NotesData> getAllNoteList = new ArrayList<>();
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM "+ ConstantNotes.TABLE_NAME_NOTES,null);
        if (cursor.moveToFirst()){
            do {
                NotesData notesData = new NotesData(cursor.getInt(
                        cursor.getColumnIndex(ConstantNotes.COLUMN_ID_IMAGE)),
                        cursor.getString(cursor.getColumnIndex(ConstantNotes.COLUMN_TITLE)),
                        cursor.getString(cursor.getColumnIndex(ConstantNotes.COLUMN_DESCRIPTION)),
                        cursor.getString(cursor.getColumnIndex(ConstantNotes.COLUMN_URI))
                           );

                getAllNoteList.add(notesData);
            }while (cursor.moveToNext());
        }
        return getAllNoteList;
    }

    //delete data
    public int deleteNotes(int id){
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        int status = sqLiteDatabase.delete(ConstantNotes.TABLE_NAME_NOTES,"id=?",new String[]{String.valueOf(id)});
        return status;

    }
//
    // update data
    public int updateNotes(NotesData notesData){
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(ConstantNotes.COLUMN_TITLE,notesData.getTitle());
        contentValues.put(ConstantNotes.COLUMN_DESCRIPTION,notesData.getDes());
        contentValues.put(ConstantNotes.COLUMN_URI,notesData.getUri());

        int status = sqLiteDatabase.update(ConstantNotes.TABLE_NAME_NOTES,contentValues," id=? ",new String[]{String.valueOf(notesData.getId())});
        return status;
    }

}
