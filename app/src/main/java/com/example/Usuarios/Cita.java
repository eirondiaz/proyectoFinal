package com.example.Usuarios;

public class Cita {

    private String idCita;
    private String NombrePac;
    private String HospitalMed;
    private String fecha;
    private String hora;
    private String status;
    private String tipo;
    private String observaciones;
    private String duracion;
    private int foto;

    public String getIdCita() {
        return idCita;
    }

    public void setIdCita(String idCita) {
        this.idCita = idCita;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getHora() {
        return hora;
    }

    public void setHora(String hora) {
        this.hora = hora;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    public String getDuracion() {
        return duracion;
    }

    public void setDuracion(String duracion) {
        this.duracion = duracion;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getFoto() {
        return foto;
    }

    public void setFoto(int foto) {
        this.foto = foto;
    }

    public String getNombrePac() {
        return NombrePac;
    }

    public void setNombrePac(String nombrePac) {
        NombrePac = nombrePac;
    }

    public String getHospitalMed() {
        return HospitalMed;
    }

    public void setHospitalMed(String hospitalMed) {
        HospitalMed = hospitalMed;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }
}
