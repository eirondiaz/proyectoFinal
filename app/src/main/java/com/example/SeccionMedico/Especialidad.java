package com.example.SeccionMedico;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.proyectofinal.R;

public class Especialidad extends AppCompatActivity {

    private ListView lv;
    String[] especialidadList = new String[]
            {"Anestesiología", "Alergología", "Cardiología", "Gastroenterología", "Geriatría", "Endocrinología"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_especialidad);

        lv = findViewById(R.id.lv);
        lv.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_expandable_list_item_1, especialidadList));

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent i = new Intent(Especialidad.this, RegistroMedico.class);
                Bundle b = new Bundle();
                b.putString("especialidad", lv.getItemAtPosition(position).toString());
                i.putExtras(b);
                startActivity(i);
            }
        });
    }
}
