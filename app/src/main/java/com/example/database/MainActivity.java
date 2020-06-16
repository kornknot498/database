package com.example.database;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class MainActivity extends AppCompatActivity {

    EditText textmsg;
    TextView mainView;
    TextView newData;
    TextView errorData;
    static final int READ_BLOCK_SIZE = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mainView = (TextView) findViewById(R.id.mainView);
        newData = (TextView) findViewById(R.id.newData);
        errorData = (TextView) findViewById(R.id.errData);

        SQLiteOpenHelper dbHelper = new DatabaseHelper(getApplicationContext());

        try{
            SQLiteDatabase db = dbHelper.getReadableDatabase();
            Cursor cursor = db.query ("DRINK",
                    new String[] {"NAME", "DESCRIPTION", "IMAGE_RESOURCE_ID"},
                    "NAME = ? ",
                    new String[] {"Latte"},
                    null, null,null);
            if (cursor.moveToFirst()) {
                mainView.setText("Latte's description is: " + cursor.getString(1));
            }

            // for new record
            Cursor cursor2 = db.query ("DRINK",
                    new String[] {"NAME", "DESCRIPTION"},
                    null, null,null,null,null);
            if (cursor2.moveToLast()) {
                newData.setText("The New Record is: " + cursor2.getString(1));
            }
        } catch(SQLiteException e) {
            errorData.setText("SQL error happened:\n" + e.toString());
        }

    }
}