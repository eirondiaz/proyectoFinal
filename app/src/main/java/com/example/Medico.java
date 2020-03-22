package com.example;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.SeccionMedico.DashboardMedico;
import com.example.SeccionMedico.MedicoLogin;
import com.example.SeccionMedico.RegistroMedico;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Medico extends Usuario {

    private String exequatur;
    private String especialidad;
    private String ars;
    private String hospital;

    private static DatabaseReference mDatabase;
    private static FirebaseAuth mAuth;

    ProgressDialog dialog;

    public static void Registrar(final Context context, final String email, final String password, final String nombre, final String apellido, final String telefono, final String fecha, final String sexo, final String exequatur, final String especialidad, final String ars, final String hospital){
        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();

        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){

                    UsuarioFactory factory = new UsuarioFactory();

                    Medico med = (Medico) factory.getUsuario("medico");

                    med.setNombre(nombre);
                    med.setApellido(apellido);
                    med.setTelefono(telefono);
                    med.setEmail(email);
                    med.setContraseña(password);
                    med.setFecha(fecha);
                    med.setSexo(sexo);
                    med.setExequatur(exequatur);
                    med.setEspecialidad(especialidad);
                    med.setArs(ars);
                    med.setHospital(hospital);

                    String id = mAuth.getCurrentUser().getUid();

                    mDatabase.child("Users").child("Medicos").child(id).setValue(med).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()){
                                Toast.makeText(context, "Medico agregado", Toast.LENGTH_SHORT).show();
                                context.startActivity(new Intent(context, MedicoLogin.class));
                            }
                        }
                    });
                }
                else{

                    if (task.getException() instanceof FirebaseAuthUserCollisionException) {

                        Toast.makeText(context, "Ese correo ya existe", Toast.LENGTH_SHORT).show();
                    }
                    else{

                        Toast.makeText(context, "No se pudo registrar", Toast.LENGTH_SHORT).show();
                    }

                }
            }
        });
    }

    public static void IniciarSesion(final Context context, String email, String password){
        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();

        final ProgressDialog dialog = new ProgressDialog(context);

        dialog.setMessage("Iniciando Sesion...");
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();

        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    Toast.makeText(context, "Bienvenido", Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(context, DashboardMedico.class);
                    context.startActivity(i);
                }
                else{
                    Toast.makeText(context, "Datos incorrectos", Toast.LENGTH_SHORT).show();
                }

                dialog.dismiss();
            }
        });
    }

    public static void LogOut(Context context){
        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();

        mAuth.signOut();
        Intent i = new Intent(context, PreLogin.class);
        context.startActivity(i);
    }

    public String getExequatur() {
        return exequatur;
    }

    public void setExequatur(String exequatur) {
        this.exequatur = exequatur;
    }

    public String getEspecialidad() {
        return especialidad;
    }

    public void setEspecialidad(String especialidad) {
        this.especialidad = especialidad;
    }

    public String getArs() {
        return ars;
    }

    public void setArs(String ars) {
        this.ars = ars;
    }

    public String getHospital() {
        return hospital;
    }

    public void setHospital(String hospital) {
        this.hospital = hospital;
    }
}
