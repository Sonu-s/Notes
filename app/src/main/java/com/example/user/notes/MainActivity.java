package com.example.user.notes;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.HashSet;

public class MainActivity extends AppCompatActivity {

    ListView listView ;
     static ArrayList<String> arrayList =  new ArrayList<>();
     static ArrayAdapter<String> arrayAdapter;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.main_menu,menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
         super.onOptionsItemSelected(item);
       // arrayList.clear();
        if (item.getItemId()== R.id.addANote){

            Intent intent = new Intent(getApplicationContext(),SecondActivity.class);
            startActivity(intent);

        }
         return false;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = findViewById(R.id.listView);
        SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("com.example.user.notes", Context.MODE_PRIVATE);
        HashSet<String> set = (HashSet<String>) sharedPreferences.getStringSet("notes",null);

        if(set == null) {
            arrayList.add("Example note");
        }else{

            arrayList = new ArrayList<>(set);
        }



        arrayAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,arrayList);
        listView.setAdapter(arrayAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                Intent intent = new Intent(getApplicationContext(),SecondActivity.class);
                intent.putExtra("nodeId",i);
                startActivity(intent);

            }
        });


        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {

                final int iteamToDelete =i;

               new AlertDialog.Builder(MainActivity.this)
                       .setIcon(android.R.drawable.ic_dialog_alert)
                       .setTitle("Delete note")
                       .setMessage("Do you want to delete this note?")
                       .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                           @Override
                           public void onClick(DialogInterface dialogInterface, int i) {

                               arrayList.remove(iteamToDelete);
                               arrayAdapter.notifyDataSetChanged();

                               SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("com.example.user.notes", Context.MODE_PRIVATE);

                               HashSet<String> set = new HashSet<>(MainActivity.arrayList);
                               sharedPreferences.edit().putStringSet("notes",set).apply();

                           }
                       })
                       .setNegativeButton("No",null)
                       .show();

                return true;
            }
        });


    }
}
