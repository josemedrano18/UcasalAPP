package com.example.myapplication.web;

public class Carrera {
    public String cod_carrera;
    public String nom_carrera;
    public String eje_carrera;
    public String titulo;
    public String duracion;
    public String salida;
    public String perfil;
    public String requisitos;
    public String curso;
    public String plan_estudio;


    public Carrera(String cod_carrera, String nom_carrera, String eje_carrera, String titulo, String duracion, String salida, String perfil, String requisitos, String curso, String plan_estudio) {
        this.cod_carrera = cod_carrera;
        this.nom_carrera=nom_carrera;
        this.eje_carrera=eje_carrera;
        this.titulo=titulo;
        this.duracion=duracion;
        this.salida=salida;
        this.perfil=perfil;
        this.requisitos=requisitos;
        this.curso=curso;
        this.plan_estudio=plan_estudio;

    }
}
