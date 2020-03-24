package com.example.Usuarios;

import com.example.Usuarios.Medico;
import com.example.Usuarios.Paciente;
import com.example.Usuarios.Usuario;

public class UsuarioFactory {

    public Usuario getUsuario(String user){
        if (user.equalsIgnoreCase("paciente")){
            return new Paciente();
        }
        else if(user.equalsIgnoreCase("medico")){
            return new Medico();
        }

        return null;
    }
}
