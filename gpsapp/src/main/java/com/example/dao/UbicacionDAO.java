/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.dao;

import com.example.models.Ubicacion;
import com.example.tda.list.LinkedList;

/**
 *
 * @author Jhonathan Alexander Ludeña Cevallos
 */

public class UbicacionDAO {

    private LinkedList<Ubicacion> ubicaciones = new LinkedList<>();
    private Integer nextId = 1;

    public void save(Ubicacion ubicacion) {
        if (ubicacion.getId() == null) {
            ubicacion.setId(nextId++);
        }
        ubicaciones.add(ubicacion);
    }

    public LinkedList<Ubicacion> listAll() {
        return ubicaciones;
    }

    public Ubicacion getById(Integer id) {
        for (Ubicacion ubicacion : ubicaciones) {
            if (ubicacion.getId().equals(id)) {
                return ubicacion;
            }
        }
        return null;
    }
}
