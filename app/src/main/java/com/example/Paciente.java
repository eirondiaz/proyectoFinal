package com.example;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;

import com.example.SeccionPaciente.PacienteLogin;
import com.example.SeccionPaciente.RegistroPaciente;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class Paciente extends Usuario{

    private static DatabaseReference mDatabase;
    private static FirebaseAuth mAuth;

    ProgressDialog dialog;

    public static void Registrar(final Context context, final String email, final String password, final String nombre, final String apellido, final String telefono, final String fecha, final String sexo) {
        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();

        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){

                    UsuarioFactory factory = new UsuarioFactory();

                    Usuario pac = factory.getUsuario("paciente");

                    pac.setNombre(nombre);
                    pac.setApellido(apellido);
                    pac.setTelefono(telefono);
                    pac.setEmail(email);
                    pac.setContraseña(password);
                    pac.setFecha(fecha);
                    pac.setSexo(sexo);

                    String id = mAuth.getCurrentUser().getUid();

                    mDatabase.child("Users").child("Pacientes").child(id).setValue(pac).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()){
                                Toast.makeText(context, "Paciente agregado", Toast.LENGTH_SHORT).show();
                                context.startActivity(new Intent(context, PacienteLogin.class));
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
                    Intent i = new Intent(context, MainActivity.class);
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

    public static void Actualizar(final Context context, String nombre, String apellido, String telefono){
        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();

        String id = mAuth.getCurrentUser().getUid();

        Map<String, Object> map = new HashMap<>();
        map.put("nombre", nombre);
        map.put("apellido", apellido);
        map.put("telefono", telefono);

        mDatabase.child("Users").child("Pacientes").child(id).updateChildren(map)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()){
                    Toast.makeText(context, "Datos actualizados", Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(context, "Error al actualizar los datos", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
