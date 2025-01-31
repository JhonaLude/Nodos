/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.models;

/**
 *
 * @author Jhonathan Alexander Ludeña Cevallos
 */

public class Ruta {

    private Integer id;
    private String origen;
    private String destino;
    private double peso;

    public Ruta(Integer id, String origen, String destino, double peso) {
        this.id = id;
        this.origen = origen;
        this.destino = destino;
        this.peso = peso;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getOrigen() {
        return origen;
    }

    public void setOrigen(String origen) {
        this.origen = origen;
    }

    public String getDestino() {
        return destino;
    }

    public void setDestino(String destino) {
        this.destino = destino;
    }

    public double getPeso() {
        return peso;
    }

    public void setPeso(double peso) {
        this.peso = peso;
    }
}
