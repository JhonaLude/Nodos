/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.service;

import com.example.dao.UbicacionDAO;
import com.example.models.Ubicacion;
import com.example.tda.list.LinkedList;
import java.util.HashMap;

/**
 *
 * @author Jhonathan Alexander Ludeña Cevallos
 */

public class UbicacionService {
    private UbicacionDAO ubicacionDAO = new UbicacionDAO();

    public LinkedList<Ubicacion> listAll() {
        return ubicacionDAO.listAll();
    }

    public Ubicacion getById(Integer id) {
        Ubicacion ubicacion = ubicacionDAO.getById(id);
        if (ubicacion == null) {
            throw new IllegalArgumentException("Ubicacion no encontrada con ID: " + id);
        }
        return ubicacion;
    }

    public void save(HashMap<String, Object> map) {
        String nombre = (String) map.get("nombre");
        if (nombre == null || nombre.isEmpty()) {
            throw new IllegalArgumentException("Nombre es requerido");
        }
        ubicacionDAO.save(new Ubicacion(null, nombre));
    }
}