/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.rest;

/**
 *
 * @author Jhonathan Alexander Ludeña Cevallos
 */

import com.example.service.RutaService;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.HashMap;

@Path("ruta")
public class RutaAPI {

    @Path("/list")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAll() {
        HashMap<String, Object> map = new HashMap<>();
        RutaService rutaService = new RutaService();

        try {
            map.put("msg", "OK");
            map.put("data", rutaService.listAll());
        } catch (Exception e) {
            map.put("msg", "ERROR");
            map.put("data", "Error retrieving routes: " + e.getMessage());
        }

        return Response.ok(map).build();
    }

    @Path("/get/{id}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getById(@PathParam("id") Integer id) {
        HashMap<String, Object> map = new HashMap<>();
        RutaService rutaService = new RutaService();

        try {
            map.put("msg", "OK");
            map.put("data", rutaService.getById(id));
        } catch (Exception e) {
            map.put("msg", "ERROR");
            map.put("data", "Error retrieving route: " + e.getMessage());
        }

        return Response.ok(map).build();
    }

    @Path("/save")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response save(HashMap<String, Object> map) {
        HashMap<String, Object> response = new HashMap<>();
        RutaService rutaService = new RutaService();

        try {
            rutaService.save(map);
            response.put("msg", "OK");
            response.put("data", "Route saved successfully");
        } catch (Exception e) {
            response.put("msg", "ERROR");
            response.put("data", "Error saving route: " + e.getMessage());
        }

        return Response.ok(response).build();
    }
}
