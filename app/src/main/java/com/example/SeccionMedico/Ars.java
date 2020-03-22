package com.example.SeccionMedico;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.proyectofinal.R;

public class Ars extends AppCompatActivity {

    private ListView lv;
    String[] ARSList = new String[]
            {"No tengo seguro", "ARS Universal", "ARS Palic Salud"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ars);

        lv = findViewById(R.id.lv);
        lv.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_expandable_list_item_1, ARSList));

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent i = new Intent(Ars.this, RegistroMedico.class);
                Bundle b = new Bundle();
                b.putString("ars", lv.getItemAtPosition(position).toString());
                i.putExtras(b);
                startActivity(i);
            }
        });
    }
}
