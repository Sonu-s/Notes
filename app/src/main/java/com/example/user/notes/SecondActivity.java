package com.example.user.notes;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.Toast;

import java.util.HashSet;

public class SecondActivity extends AppCompatActivity {

    EditText editText;
    int nodeId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        editText = findViewById(R.id.editText);

       // Toast.makeText(this,editText.getText().toString(),Toast.LENGTH_LONG).show();
       // MainActivity.arrayList.set(0,editText.getText().toString());
        Intent intent=getIntent();
        nodeId = intent.getIntExtra("nodeId",-1);

        if (nodeId != -1){

            editText.setText(MainActivity.arrayList.get(nodeId));
        }else{

            MainActivity.arrayList.add("");
            nodeId = MainActivity.arrayList.size()-1;
            MainActivity.arrayAdapter.notifyDataSetChanged();
        }

        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                MainActivity.arrayList.set(nodeId,String.valueOf(charSequence));
                MainActivity.arrayAdapter.notifyDataSetChanged();

                SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("com.example.user.notes", Context.MODE_PRIVATE);

                HashSet<String> set = new HashSet<>(MainActivity.arrayList);
                sharedPreferences.edit().putStringSet("notes",set).apply();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });


    }
}
