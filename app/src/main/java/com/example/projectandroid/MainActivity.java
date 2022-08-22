package com.example.projectandroid;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.projectandroid.DataModel.Employee;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

/**
 * Created by chris on 3/7/2017.
 */

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private ListView mListView;
    private ListAdapter mListAdapter;

    private DBHelper mDBHelper;
    private ArrayList<Employee> employees;


    private FloatingActionButton register_btn, search_btn;
    private String searchFilter="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mDBHelper = new DBHelper(this);
        employees = mDBHelper.getAllEmployee();

        mListView = findViewById(R.id.list_view);
        register_btn =findViewById(R.id.register_button);
        search_btn = findViewById(R.id.search_button);

        register_btn.setOnClickListener(this);
        search_btn.setOnClickListener(this);

        mListAdapter = new ListAdapter(this,R.layout.list_row,employees);
        mListView.setAdapter(mListAdapter);




        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                Employee e=employees.get(i);
                Intent intent = new Intent(MainActivity.this, DetailsViewActivity.class);
                intent.putExtra("data",e.toDisplay());

                startActivity(intent);
            }


        });


    }

    private void setAdapterArray(){

        ArrayList<Employee> tmp = mDBHelper.getAllEmployee();

        ArrayList<Employee> tmp2=new ArrayList<>();

        for(Employee e:tmp){
            if(e.getName().toLowerCase().contains(searchFilter))
                tmp2.add(e);
        }
        mListAdapter.setList(tmp2);
        employees=tmp2;
    }

    @Override
    protected void onResume() {
        super.onResume();
        setAdapterArray();

    }

    @Override
    public void onClick(View view) {

        if(view.getId() == R.id.register_button){
            Intent i = new Intent(this,RegisterActivity.class);
            startActivity(i);
        }
        else {
            final EditText text = new EditText(this);
            text.setText(searchFilter);

            new AlertDialog.Builder(this)
                    .setTitle("Search By Name")
                    //.setMessage("Search : ")
                    .setView(text)
                    .setPositiveButton("Search", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int whichButton) {
                            searchFilter=text.getText().toString();
                            setAdapterArray();
                        }
                    })
                    .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int whichButton) {
                        }
                    })
                    .show();
        }
    }

}
