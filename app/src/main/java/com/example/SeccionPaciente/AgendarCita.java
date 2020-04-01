package com.example.SeccionPaciente;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.proyectofinal.R;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;

public class AgendarCita extends AppCompatActivity implements View.OnClickListener, Response.Listener<JSONObject>, Response.ErrorListener{

    private Button btnFecha, btnHora, btnAgendar;
    private EditText edtObervaciones;
    private String idMedico, idPaciente, medico, especialidad, hospital;
    //private ArrayList<String> lMedico, lEspecialidad, lHospital;
    private ArrayList<String> lMedico = new ArrayList<String>();
    private ArrayList<String> lEspecialidad = new ArrayList<String>();
    private ArrayList<String> lHospital = new ArrayList<String>();
    private String[] tipoCita = new String[]{"Tipo de Cita", "Nuevo", "Seguimiento"};
    private Spinner spMedico, spEspecialidad, spHospital, spTipoCita;
    private ProgressDialog dialog;

    private RequestQueue request;
    JsonObjectRequest jsonObjectRequest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //para commit
        setContentView(R.layout.activity_agendar_cita);

        try{
            Bundle b = getIntent().getExtras();
            idMedico = b.getString("id");
            medico = b.getString("medico");
            especialidad = b.getString("especialidad");
            hospital = b.getString("hospital");

            lMedico.add(medico);
            lEspecialidad.add(especialidad);
            lHospital.add(hospital);
        }catch (Exception e){ }

        SharedPreferences preferences = getSharedPreferences("datos", Context.MODE_PRIVATE);
        idPaciente = preferences.getString("idpaciente", "");

        edtObervaciones = findViewById(R.id.edtObservaciones);
        btnFecha = findViewById(R.id.btnFecha);
        btnFecha.setOnClickListener(this);
        btnHora = findViewById(R.id.btnHora);
        btnHora.setOnClickListener(this);

        spMedico = findViewById(R.id.spMedico);
        spMedico.setAdapter(new ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item, lMedico));
        spEspecialidad = findViewById(R.id.spEspecialidad);
        spEspecialidad.setAdapter(new ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item, lEspecialidad));
        spHospital = findViewById(R.id.spHospital);
        spHospital.setAdapter(new ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item, lHospital));
        spTipoCita = findViewById(R.id.spTipoCita);
        spTipoCita.setAdapter(new ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item, tipoCita));

        btnAgendar = findViewById(R.id.btnAgendar);
        btnAgendar.setOnClickListener(this);

        request = Volley.newRequestQueue(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){

            case R.id.btnFecha:
                getDate();
                break;
            case R.id.btnHora:
                getHour();
                break;
            case R.id.btnAgendar:
                CargarWebService();
                break;
        }
    }

    private void CargarWebService(){
            dialog = new ProgressDialog(this);
            dialog.setMessage("Agendando Cita...");
            dialog.setCanceledOnTouchOutside(false);
            dialog.show();

            String fecha = btnFecha.getText().toString();
            String hora = btnHora.getText().toString();
            String observaciones = edtObervaciones.getText().toString();
            String medico = spMedico.getSelectedItem().toString();
            String especialidad = spEspecialidad.getSelectedItem().toString();
            String hospital = spHospital.getSelectedItem().toString();
            String tipo = spTipoCita.getSelectedItem().toString();

            if (observaciones.isEmpty() || fecha.equalsIgnoreCase("fecha") || hora.equalsIgnoreCase("hora") || tipo.equalsIgnoreCase("tipo de cita")){
                Toast.makeText(this, "Debes llenar todos los campos", Toast.LENGTH_SHORT).show();
                dialog.dismiss();
            }
            else{
                String url = "https://proyectofinalprog2.000webhostapp.com/wsJSONRegistrarCita.php?hora=" + hora + "&observaciones=" + observaciones + "&fecha=" + fecha + "&tipo=" + tipo + "&idMedico=" + idMedico + "&idPaciente=" + idPaciente;
                url = url.replace(" ", "%20");

                jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, this, this);
                request.add(jsonObjectRequest);
            }
    }

    @Override
    public void onResponse(JSONObject response) {
        Toast.makeText(this, "Se ha agendado correctamente", Toast.LENGTH_SHORT).show();
        dialog.dismiss();
        startActivity(new Intent(AgendarCita.this, CitasAgendadas.class));
        finish();
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        Toast.makeText(this, "Ha ocurrido un error al agendar la cita", Toast.LENGTH_SHORT).show();
        //Toast.makeText(this, error.toString(), Toast.LENGTH_SHORT).show();
        dialog.dismiss();
    }

    private void getDate()
    {
        final Calendar c = Calendar.getInstance();
        int dia = c.get(Calendar.DAY_OF_MONTH);
        int mes = c.get(Calendar.MONTH);
        int ano = c.get(Calendar.YEAR);

        DatePickerDialog datepicker = new DatePickerDialog(AgendarCita.this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                btnFecha.setText(year + "-" + (month + 1) + "-" + dayOfMonth);
            }
        } , dia, mes, ano);
        datepicker.getDatePicker().setMinDate(System.currentTimeMillis());
        datepicker.show();
    }

    private void getHour(){
        Calendar mcurrentTime = Calendar.getInstance();
        int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
        int minute = mcurrentTime.get(Calendar.MINUTE);
        TimePickerDialog mTimePicker;
        mTimePicker = new TimePickerDialog(AgendarCita.this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                btnHora.setText( selectedHour + ":" + selectedMinute);
            }
        }, hour, minute, false);//Yes 24 hour time
        mTimePicker.setTitle("Select Hour");
        mTimePicker.show();
    }
}
