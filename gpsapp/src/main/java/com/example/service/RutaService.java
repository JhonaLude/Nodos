/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.service;

import com.example.dao.RutaDAO;
import com.example.models.Ruta;

/**
 *
 * @author Jhonathan Alexander Ludeña Cevallos
 */

import com.example.dao.RutaDAO;
import com.example.models.Ruta;
import com.example.tda.list.LinkedList;
import java.util.HashMap;

public class RutaService {

    private RutaDAO rutaDAO = new RutaDAO();

    public LinkedList<Ruta> listAll() {
        return rutaDAO.listAll();
    }

    public Ruta getById(Integer id) {
        Ruta ruta = rutaDAO.getById(id);
        if (ruta == null) {
            throw new IllegalArgumentException("Ruta no encontrada con ID: " + id);
        }
        return ruta;
    }

    public void save(HashMap<String, Object> map) {
        String origen = (String) map.get("origen");
        String destino = (String) map.get("destino");
        Double peso = (Double) map.get("peso");

        if (origen == null || origen.isEmpty() || destino == null || destino.isEmpty() || peso == null) {
            throw new IllegalArgumentException("Se requieren Orígenes, Destino y Peso");
        }

        rutaDAO.save(new Ruta(null, origen, destino, peso));
    }
}
