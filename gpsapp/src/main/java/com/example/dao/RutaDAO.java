/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.dao;

/**
 *
 * @author Jhonathan Alexander Ludeña Cevallos
 */
import com.example.models.Ruta;
import com.example.tda.list.LinkedList;

public class RutaDAO {

    private LinkedList<Ruta> rutas = new LinkedList<>();
    private Integer nextId = 1;

    public void save(Ruta ruta) {
        if (ruta.getId() == null) {
            ruta.setId(nextId++);
        }
        rutas.add(ruta);
    }

    public LinkedList<Ruta> listAll() {
        return rutas;
    }

    public Ruta getById(Integer id) {
        for (Ruta ruta : rutas) {
            if (ruta.getId().equals(id)) {
                return ruta;
            }
        }
        return null;
    }
}
