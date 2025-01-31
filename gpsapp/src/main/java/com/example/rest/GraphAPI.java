/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author Jhonathan Alexander Ludeña Cevallos
 */

package com.example.rest;

import com.example.exception.ListEmptyException;
import com.example.service.GraphService;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.HashMap;
import java.util.LinkedList;

@Path("graph")
public class GraphAPI {
    private GraphService graphService = new GraphService();
    
    @Path("/nodes")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getNodes() {
        HashMap<String, Object> response = new HashMap<>();
        response.put("nodes", graphService.getNodes());
        return Response.ok(response).build();
    }
    
    @Path("/edges")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getEdges() {
        HashMap<String, Object> response = new HashMap<>();
        response.put("edges", graphService.getEdges());
        return Response.ok(response).build();
    }
    
    @Path("/node")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response addNode(HashMap<String, String> data) {
        String nombre = data.get("nombre");
        graphService.addNode(nombre);
        return Response.ok().build();
    }
    
    @Path("/edge")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response addEdge(HashMap<String, Object> data) {
        String origen = (String) data.get("origen");
        String destino = (String) data.get("destino");
        double peso = ((Number) data.get("peso")).doubleValue();
        graphService.addEdge(origen, destino, peso);
        return Response.ok().build();
    }
    
    @Path("/generate/{size}")
    @POST
    public Response generateRandomGraph(@PathParam("size") int size) throws ListEmptyException {
        graphService.generateRandomGraph(size);
        return Response.ok().build();
    }
    
    @Path("/analyze/{size}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response analyzePerformance(@PathParam("size") int size) throws ListEmptyException {
        HashMap<String, Object> results = graphService.runPerformanceAnalysis(size);
        return Response.ok(results).build();
    }
    
    @Path("/clear")
    @POST
    public Response clearGraph() {
        graphService.clearGraph();
        return Response.ok().build();
    }
}