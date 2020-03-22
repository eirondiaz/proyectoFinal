package com.example;

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
