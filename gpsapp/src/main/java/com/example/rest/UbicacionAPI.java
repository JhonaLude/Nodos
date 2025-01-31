/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.rest;

/**
 *
 * @author Jhonathan Alexander Ludeña Cevallos
 */

import com.example.models.Ubicacion;
import com.example.service.UbicacionService;
import com.example.tda.list.LinkedList;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.HashMap;
import java.util.List;

@Path("ubicacion")
public class UbicacionAPI {

    @Path("/list")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAll() {
        HashMap<String, Object> map = new HashMap<>();
        UbicacionService ubicacionService = new UbicacionService();

        try {
            LinkedList<Ubicacion> ubicaciones = ubicacionService.listAll();
            System.out.println("Ubicaciones: " + ubicaciones); // Para depurar
            map.put("msg", "OK");
            map.put("data", ubicaciones);
        } catch (Exception e) {
            map.put("msg", "ERROR");
            map.put("data", "Error retrieving locations: " + e.getMessage());
        }

        return Response.ok(map).build();
    }

    @Path("/get/{id}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getById(@PathParam("id") Integer id) {
        HashMap<String, Object> map = new HashMap<>();
        UbicacionService ubicacionService = new UbicacionService();

        try {
            map.put("msg", "OK");
            map.put("data", ubicacionService.getById(id));
        } catch (Exception e) {
            map.put("msg", "ERROR");
            map.put("data", "Error retrieving location: " + e.getMessage());
        }

        return Response.ok(map).build();
    }

    @Path("/save")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response save(List<HashMap<String, Object>> ubicaciones) {
        HashMap<String, Object> response = new HashMap<>();
        UbicacionService ubicacionService = new UbicacionService();

        try {
            for (HashMap<String, Object> ubicacion : ubicaciones) {
                ubicacionService.save(ubicacion);
            }
            response.put("msg", "OK");
            response.put("data", "Locations saved successfully");
        } catch (Exception e) {
            response.put("msg", "ERROR");
            response.put("data", "Error saving locations: " + e.getMessage());
        }

        return Response.ok(response).build();
    }
}
