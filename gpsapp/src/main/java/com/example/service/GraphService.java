/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author Jhonathan Alexander Ludeña Cevallos
 */

package com.example.service;

import com.example.exception.ListEmptyException;
import com.example.tda.graph.Graph;
import com.example.models.Ruta;
import com.example.models.Ubicacion;
import com.example.tda.list.LinkedList;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;

public class GraphService {

    private Graph graph;
    private RutaService rutaService;
    private UbicacionService ubicacionService;

    public GraphService() {
        this.graph = new Graph();
        this.rutaService = new RutaService();
        this.ubicacionService = new UbicacionService();
        loadGraphFromServices();
    }

    // Método para cargar el grafo 
    private void loadGraphFromServices() {
        LinkedList<Ubicacion> ubicaciones = ubicacionService.listAll();
        LinkedList<Ruta> rutas = rutaService.listAll();

        for (Ubicacion ubicacion : ubicaciones) {
            graph.addVertex(ubicacion);
        }

        for (Ruta ruta : rutas) {
            graph.addEdge(ruta);
        }
    }

    // Métodos para gestión del grafo
    public void addNode(String nombre) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("nombre", nombre);
        ubicacionService.save(map);
        loadGraphFromServices();
    }

    public void addEdge(String origen, String destino, double peso) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("origen", origen);
        map.put("destino", destino);
        map.put("peso", peso);
        rutaService.save(map);
        loadGraphFromServices();
    }

    public LinkedList<Ubicacion> getNodes() {
        return graph.getVertices();
    }

    public LinkedList<Ruta> getEdges() {
        return graph.getEdges();
    }

    public void clearGraph() {
        graph.clear();
    }

    // Métodos para generación aleatoria y análisis
    public void generateRandomGraph(int size) throws ListEmptyException {
        graph.generateRandomGraph(size);
        saveGeneratedGraph();
    }

    private void saveGeneratedGraph() {

        clearGraph();

        // Guardar nuevos nodos
        for (Ubicacion ubicacion : graph.getVertices()) {
            HashMap<String, Object> map = new HashMap<>();
            map.put("nombre", ubicacion.getNombre());
            ubicacionService.save(map);
        }

        // Guardar nuevas rutas
        for (Ruta ruta : graph.getEdges()) {
            HashMap<String, Object> map = new HashMap<>();
            map.put("origen", ruta.getOrigen());
            map.put("destino", ruta.getDestino());
            map.put("peso", ruta.getPeso());
            rutaService.save(map);
        }
    }

    public HashMap<String, Object> runPerformanceAnalysis(int size) throws ListEmptyException {
        HashMap<String, Object> results = new HashMap<>();

        // Generar grafo aleatorio del tamaño especificado
        generateRandomGraph(size);

        // Medir tiempo de Floyd-Warshall
        long startTime = System.nanoTime();
        double[][] floydResults = graph.floydWarshall();
        long floydTime = (System.nanoTime() - startTime) / 1000000; 

        // Medir tiempo de Bellman-Ford
        startTime = System.nanoTime();
        String startNode = graph.getVertices().get(0).getNombre();
        double[] bellmanResults = graph.bellmanFord(startNode);
        long bellmanTime = (System.nanoTime() - startTime) / 1000000;

        // Imprimir resultados
        results.put("size", size);
        results.put("floydTime", floydTime);
        results.put("bellmanTime", bellmanTime);
        results.put("floydResults", floydResults);
        results.put("bellmanResults", bellmanResults);

        return results;
    }

    
    public void saveToJson(String filename) throws IOException {
        HashMap<String, Object> graphData = new HashMap<>();
        graphData.put("vertices", graph.getVertices());
        graphData.put("edges", graph.getEdges());

        ObjectMapper mapper = new ObjectMapper();
        mapper.writeValue(new File(filename), graphData);
    }

    public void loadFromJson(String filename) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        HashMap<String, Object> graphData = mapper.readValue(new File(filename), HashMap.class);

        // Limpiar grafo actual
        clearGraph();

        // Cargar vértices
        LinkedList<HashMap<String, Object>> vertices = (LinkedList<HashMap<String, Object>>) graphData.get("vertices");
        for (HashMap<String, Object> vertex : vertices) {
            Ubicacion ubicacion = new Ubicacion(
                    (Integer) vertex.get("id"),
                    (String) vertex.get("nombre")
            );
            graph.addVertex(ubicacion);
        }

        // Cargar aristas
        LinkedList<HashMap<String, Object>> edges = (LinkedList<HashMap<String, Object>>) graphData.get("edges");
        for (HashMap<String, Object> edge : edges) {
            Ruta ruta = new Ruta(
                    (Integer) edge.get("id"),
                    (String) edge.get("origen"),
                    (String) edge.get("destino"),
                    ((Number) edge.get("peso")).doubleValue()
            );
            graph.addEdge(ruta);
        }

        // Actualizar servicios
        saveGeneratedGraph();
    }

    
    public LinkedList<String> findShortestPath(String start, String end) throws ListEmptyException {
        return graph.dijkstra(start, end);
    }

    public double[][] getAllPairsShortestPaths() throws ListEmptyException {
        return graph.floydWarshall();
    }

    public HashMap<String, Object> getGraphStatistics() {
        HashMap<String, Object> stats = new HashMap<>();
        stats.put("numNodes", graph.getVertices().size());
        stats.put("numEdges", graph.getEdges().size());

        // Calcular grado promedio
        double avgDegree = 0;
        for (Ubicacion vertex : graph.getVertices()) {
            int degree = 0;
            for (Ruta edge : graph.getEdges()) {
                if (edge.getOrigen().equals(vertex.getNombre())
                        || edge.getDestino().equals(vertex.getNombre())) {
                    degree++;
                }
            }
            avgDegree += degree;
        }
        avgDegree /= graph.getVertices().size();

        stats.put("promedioGrado", avgDegree);
        return stats;
    }
}
