package com.example.filetest;

public class ConstantNotes {
    public  static final String DATABASE_NAME="N0tes.db";
    public  static final int DATABASE_VERSION=2;
    public  static final String TABLE_NAME_NOTES="NOTES";


    public  static final String COLUMN_ID_IMAGE="id";
    public  static final String COLUMN_TITLE="TITLE";
    public  static final String COLUMN_DESCRIPTION="DESCRIPTION";
    public  static final String COLUMN_URI="URI";

    public static final String CREATE_TABLE_IMAGE  = " CREATE TABLE "+TABLE_NAME_NOTES+"("
            +COLUMN_ID_IMAGE+" INTEGER PRIMARY KEY AUTOINCREMENT, "
            +COLUMN_TITLE+" TEXT, "
            +COLUMN_DESCRIPTION+" TEXT, "
            +COLUMN_URI+" TEXT "
            +")";


}
