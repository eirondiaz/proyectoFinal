package com.example.Usuarios;

public abstract class Usuario {

    private String nombre;
    private String apellido;
    private String telefono;
    private String email;
    private String contraseña;
    private String fecha;
    private String sexo;
    private  int idUsuario ;
    private int foto;
    public void setFoto(int foto) {
        this.foto = foto;
    }

    public int getFoto() {
        return foto;
    }

    public static void Registrar(){

    }

    public static void IniciarSesion(){

    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getTelefono() {
        return telefono;
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getContraseña() {
        return contraseña;
    }

    public void setContraseña(String contraseña) {
        this.contraseña = contraseña;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getSexo() {
        return sexo;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }
}
