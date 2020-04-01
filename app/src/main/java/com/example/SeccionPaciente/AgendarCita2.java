package com.example.SeccionPaciente;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.FragmentsPaciente.BuscarFragment;
import com.example.MainActivity;
import com.example.PerfilCitas;
import com.example.SeccionCitaMedica.RecylclerViewAdapter;
import com.example.Usuarios.Medico;
import com.example.proyectofinal.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class AgendarCita2 extends AppCompatActivity implements View.OnClickListener, Response.Listener<JSONObject>, Response.ErrorListener{

    private String idPaciente;
    private Button btnFecha, btnHora, btnAgendar;
    private EditText edtObervaciones;
    private String[] tipoCita = new String[]{"Tipo de Cita", "Nuevo", "Seguimiento"};
    private Spinner spMedico, spEspecialidad, spHospital, spTipoCita;
    private ArrayList<String> lMedico = new ArrayList<String>();
    private ArrayList<String> lIdMedico = new ArrayList<String>();
    private ArrayList<String> lEspecialidad = new ArrayList<String>();
    private ArrayList<String> lHospital = new ArrayList<String>();
    private ProgressDialog dialog;
    //private final String url = "https://proyectofinalprog2.000webhostapp.com/wsJSONMedicoById.php";
    private int num;

    private RequestQueue request;
    JsonObjectRequest jsonObjectRequest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agendar_cita2);

        SharedPreferences preferences = getSharedPreferences("datos", Context.MODE_PRIVATE);
        idPaciente = preferences.getString("idpaciente", "");

        edtObervaciones = findViewById(R.id.edtObservaciones);
        btnFecha = findViewById(R.id.btnFecha);
        btnFecha.setOnClickListener(this);
        btnHora = findViewById(R.id.btnHora);
        btnHora.setOnClickListener(this);

        spMedico = findViewById(R.id.spMedico);
        spEspecialidad = findViewById(R.id.spEspecialidad);
        spHospital = findViewById(R.id.spHospital);
        spTipoCita = findViewById(R.id.spTipoCita);
        spTipoCita.setAdapter(new ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item, tipoCita));

        btnAgendar = findViewById(R.id.btnAgendar);
        btnAgendar.setOnClickListener(this);

        request = Volley.newRequestQueue(this);

        CargarWebService();
        //final String url = "https://proyectofinalprog2.000webhostapp.com/wsJSONMedicoById.php?id=1";

        spMedico.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                num = position;
                //CargarWebService();
                CargarDatosMed(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    public void onClick(View v) {
        switch (v.getId()){

            case R.id.btnFecha:
                getDate();
                break;
            case R.id.btnHora:
                getHour();
                break;
            case R.id.btnAgendar:
                agendarCita();
                break;
        }
    }

    private void CargarWebService(){
        dialog = new ProgressDialog(this);
        dialog.setMessage("Cargando...");
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();

        String url = "https://proyectofinalprog2.000webhostapp.com/wsJSONConsultarListaMedicos.php";
        url = url.replace(" ", "%20");

        jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, this, this);
        request.add(jsonObjectRequest);
    }

    @Override
    public void onResponse(JSONObject response) {
        JSONArray json = response.optJSONArray("medico");

        try {
            for (int i = 0; i < json.length(); i++){
                JSONObject jsonObject = null;
                jsonObject = json.getJSONObject(i);

                lMedico.add(jsonObject.getString("Nombres") + " " + jsonObject.getString("Apellidos"));
                lIdMedico.add(jsonObject.getString("IdMedico"));
            }
            dialog.dismiss();
            spMedico.setAdapter(new ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item, lMedico));

        } catch (JSONException e) {
            e.printStackTrace();
            dialog.dismiss();
            Toast.makeText(this, "No se ha podido establecer conexion con el servidor", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        Toast.makeText(this, "Ha ocurrido un error", Toast.LENGTH_SHORT).show();
        dialog.dismiss();
    }

    private void getDate()
    {
        final Calendar c = Calendar.getInstance();
        int dia = c.get(Calendar.DAY_OF_MONTH);
        int mes = c.get(Calendar.MONTH);
        int ano = c.get(Calendar.YEAR);

        DatePickerDialog datepicker = new DatePickerDialog(AgendarCita2.this, new DatePickerDialog.OnDateSetListener() {
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
        mTimePicker = new TimePickerDialog(AgendarCita2.this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                btnHora.setText( selectedHour + ":" + selectedMinute);
            }
        }, hour, minute, false);//Yes 24 hour time
        mTimePicker.setTitle("Select Hour");
        mTimePicker.show();
    }

    private void CargarDatosMed(final int position){
        final ProgressDialog dialog = new ProgressDialog(this);
        dialog.setMessage("Cargando...");
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();

        lEspecialidad.clear();
        lHospital.clear();

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, "https://proyectofinalprog2.000webhostapp.com/wsJSONMedicoById.php?id=" + lIdMedico.get(position), null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                JSONArray json = response.optJSONArray("medico");

                try {
                    //for (int i = 0; i < json.length(); i++){
                        JSONObject jsonObject = null;
                        jsonObject = json.getJSONObject(0);

                        //lEspecialidad.add();
                        lHospital.add(jsonObject.getString("Especialidad"));
                        lEspecialidad.add(jsonObject.getString("Hospital"));

                    dialog.dismiss();
                    spEspecialidad.setAdapter(new ArrayAdapter<String>(AgendarCita2.this, R.layout.support_simple_spinner_dropdown_item, lEspecialidad));
                    spHospital.setAdapter(new ArrayAdapter<String>(AgendarCita2.this, R.layout.support_simple_spinner_dropdown_item, lHospital));

                } catch (JSONException e) {
                    e.printStackTrace();
                    dialog.dismiss();
                    Toast.makeText(AgendarCita2.this, e.toString(), Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(AgendarCita2.this, error.toString(), Toast.LENGTH_SHORT).show();
                dialog.dismiss();
            }
        });

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(jsonObjectRequest);
    }

    private void agendarCita(){
        dialog = new ProgressDialog(this);
        dialog.setMessage("Agendando Cita...");
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();

        String fecha = btnFecha.getText().toString();
        String hora = btnHora.getText().toString();
        String observaciones = edtObervaciones.getText().toString();
        String tipo = spTipoCita.getSelectedItem().toString();

        if (observaciones.isEmpty() || fecha.equalsIgnoreCase("fecha") || hora.equalsIgnoreCase("hora") || tipo.equalsIgnoreCase("tipo de cita")){
            Toast.makeText(this, "Debes llenar todos los campos", Toast.LENGTH_SHORT).show();
            dialog.dismiss();
        }
        else{
            String url = "https://proyectofinalprog2.000webhostapp.com/wsJSONRegistrarCita.php?hora=" + hora + "&observaciones=" + observaciones + "&fecha=" + fecha + "&tipo=" + tipo + "&idMedico=" + lIdMedico.get(num) + "&idPaciente=" + idPaciente;
            url = url.replace(" ", "%20");

            jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    Toast.makeText(AgendarCita2.this, "Se ha agendado correctamente", Toast.LENGTH_SHORT).show();
                    dialog.dismiss();
                    startActivity(new Intent(AgendarCita2.this, CitasAgendadas.class));
                    finish();
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(AgendarCita2.this, "Ha ocurrido un error al agendar la cita", Toast.LENGTH_SHORT).show();
                    //Toast.makeText(this, error.toString(), Toast.LENGTH_SHORT).show();
                    dialog.dismiss();
                }
            });

            request.add(jsonObjectRequest);
        }
    }
}
