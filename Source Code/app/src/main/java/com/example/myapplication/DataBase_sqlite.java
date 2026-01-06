package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DataBase_sqlite extends AppCompatActivity {

    private EditText editTextData1, editTextData2, editTextData3;
    private RadioButton radioButtonMale;
    private TextView textViewResult;
    private DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_base_sqlite);

        editTextData1 = findViewById(R.id.editTextData1);
        editTextData2 = findViewById(R.id.editTextData2);
        editTextData3 = findViewById(R.id.editTextData3);

        radioButtonMale = findViewById(R.id.radioButtonMale);
        textViewResult = findViewById(R.id.textViewResult);

        Button buttonSave = findViewById(R.id.buttonSave);
        Button buttonView = findViewById(R.id.buttonView);
        Button buttonSearch = findViewById(R.id.buttonSearch);

        dbHelper = new DatabaseHelper(this);

        buttonSave.setOnClickListener(v -> saveData());
        buttonView.setOnClickListener(v -> viewData());
        buttonSearch.setOnClickListener(v -> searchData());
    }

    private void saveData() {
        String D1 = editTextData1.getText().toString().toLowerCase();
        String D2 = editTextData2.getText().toString().toLowerCase();
        String D3 = editTextData3.getText().toString().toLowerCase();
        String gender = radioButtonMale.isChecked() ? "Male" : "Female";

        if(D1.isEmpty() || D2.isEmpty() || D3.isEmpty()){
            Toast.makeText(this, "The fields are empty.", Toast.LENGTH_LONG).show();
            return;
        }

        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.COLUMN_DATA1, D1);
        values.put(DatabaseHelper.COLUMN_DATA2, D2);
        values.put(DatabaseHelper.COLUMN_DATA3, D3);
        values.put(DatabaseHelper.COLUMN_GENDER, gender);

        long newRowId = db.insert(DatabaseHelper.TABLE_NAME, null, values);
        db.close();

        if (newRowId != -1) {
            Toast.makeText(this, "Data saved", Toast.LENGTH_SHORT).show();
            clearFields();
        } else {
            Toast.makeText(this, "Error saving data", Toast.LENGTH_SHORT).show();
        }
    }

    private void viewData() {
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        String[] projection = {
                DatabaseHelper.COLUMN_ID,
                DatabaseHelper.COLUMN_DATA1,
                DatabaseHelper.COLUMN_DATA2,
                DatabaseHelper.COLUMN_DATA3,
                DatabaseHelper.COLUMN_GENDER
        };

        Cursor cursor = db.query(
                DatabaseHelper.TABLE_NAME,
                projection,
                null,
                null,
                null,
                null,
                null
        );

        StringBuilder result = new StringBuilder();
        while (cursor.moveToNext()) {
            long itemId = cursor.getLong(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_ID));
            String data1 = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_DATA1));
            String data2 = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_DATA2));
            String data3 = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_DATA3));
            String gender = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_GENDER));
            result.append(itemId).append(": ").append(data1).append(", ").append(data2).append(", ").append(data3).append(", ").append(gender).append("\n");
        }
        cursor.close();
        db.close();
        textViewResult.setText(result.toString());
    }

    private void searchData() {
        String data1 = editTextData1.getText().toString().toLowerCase();

        SQLiteDatabase db = dbHelper.getReadableDatabase();

        String[] projection = {
                DatabaseHelper.COLUMN_ID,
                DatabaseHelper.COLUMN_DATA1,
                DatabaseHelper.COLUMN_DATA2,
                DatabaseHelper.COLUMN_DATA3,
                DatabaseHelper.COLUMN_GENDER
        };

        String selection = DatabaseHelper.COLUMN_DATA1 + " = ?";
        String[] selectionArgs = { data1 };

        Cursor cursor = db.query(
                DatabaseHelper.TABLE_NAME,
                projection,
                selection,
                selectionArgs,
                null,
                null,
                null
        );

        StringBuilder result = new StringBuilder();
        if (cursor.moveToFirst()) {
            long itemId = cursor.getLong(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_ID));
            String data2 = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_DATA2));
            String data3 = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_DATA3));
            String gender = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_GENDER));
            result.append(itemId).append(": ").append(data1).append(", ").append(data2).append(", ").append(data3).append(", ").append(gender).append("\n");
        } else {
            result.append("No matching record found.");
        }
        cursor.close();
        db.close();
        textViewResult.setText(result.toString());
    }

    private void clearFields() {
        editTextData1.setText("");
        editTextData2.setText("");
        editTextData3.setText("");
        radioButtonMale.setChecked(true);
    }

    // Database Helper Class
    private static class DatabaseHelper extends SQLiteOpenHelper {
        private static final String DATABASE_NAME = "DATABASE.db";
        private static final int DATABASE_VERSION = 1;

        static final String TABLE_NAME = "my_table";
        static final String COLUMN_ID = "_id";
        static final String COLUMN_DATA1 = "data1";
        static final String COLUMN_DATA2 = "data2";
        static final String COLUMN_DATA3 = "data3";
        static final String COLUMN_GENDER = "gender";

        private static final String SQL_CREATE_ENTRIES =
                "CREATE TABLE " + TABLE_NAME + " (" +
                        COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                        COLUMN_DATA1 + " TEXT," +
                        COLUMN_DATA2 + " TEXT," +
                        COLUMN_DATA3 + " TEXT," +
                        COLUMN_GENDER + " TEXT)";

        private static final String SQL_DELETE_ENTRIES =
                "DROP TABLE IF EXISTS " + TABLE_NAME;

        DatabaseHelper(android.content.Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(SQL_CREATE_ENTRIES);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL(SQL_DELETE_ENTRIES);
            onCreate(db);
        }


    }
}