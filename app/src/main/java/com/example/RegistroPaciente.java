package com.example;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;

import com.example.proyectofinal.R;

import java.util.Calendar;

public class RegistroPaciente extends AppCompatActivity {

    private Spinner spSexo;
    private Button btnFecha;
    private String[] sexo = new String[]{"Masculino", "Femenino"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro_paciente);

        spSexo = findViewById(R.id.spSexo);
        spSexo.setAdapter(new ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item, sexo));

        btnFecha = findViewById(R.id.fecha);
        btnFecha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getDate();
            }
        });
    }

    private void getDate()
    {
        final Calendar c = Calendar.getInstance();
        int dia = c.get(Calendar.DAY_OF_MONTH);
        int mes = c.get(Calendar.MONTH);
        int ano = c.get(Calendar.YEAR);

        DatePickerDialog datepicker = new DatePickerDialog(RegistroPaciente.this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                btnFecha.setText(dayOfMonth + "/" + (month + 1) + "/" + year);
            }
        } , dia, mes, ano);
        datepicker.getDatePicker().setMinDate(System.currentTimeMillis());
        datepicker.show();
    }
}
