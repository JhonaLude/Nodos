/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.tda.graph;

import com.example.exception.ListEmptyException;
import com.example.tda.list.LinkedList;
import com.example.models.Ruta;
import com.example.models.Ubicacion;
import java.util.Random;

/**
 *
 * @author Jhonathan Alexander Ludeña Cevallos
 */

public class Graph {
    private LinkedList<Ubicacion> vertices;
    private LinkedList<Ruta> edges;
    private int numVertices;
    private int nextId;

    public Graph() {
        this.vertices = new LinkedList<>();
        this.edges = new LinkedList<>();
        this.numVertices = 0;
        this.nextId = 1;
    }

    public void clear() {
        vertices = new LinkedList<>();
        edges = new LinkedList<>();
        numVertices = 0;
        nextId = 1;
    }

    public void addVertex(Ubicacion vertex) {
        if (vertex.getId() == null) {
            vertex.setId(nextId++);
        }
        vertices.add(vertex);
        numVertices++;
    }

    public void addEdge(Ruta edge) {
        if (edge.getId() == null) {
            edge.setId(nextId++);
        }
        edges.add(edge);
    }

    public void generateRandomGraph(int size) throws ListEmptyException {
        clear();
        Random random = new Random();

        // Generar vértices
        for (int i = 0; i < size; i++) {
            String nodeName = "Node" + (i + 1);
            addVertex(new Ubicacion(nextId++, nodeName));
        }

        // Generar aristas aleatorias
        int edgeCount = size * 2;  // Cantidad de aristas = 2 * número de nodos
        for (int i = 0; i < edgeCount; i++) {
            int fromIndex = random.nextInt(size);
            int toIndex = random.nextInt(size);

            if (fromIndex != toIndex) {
                String origen = vertices.get(fromIndex).getNombre();
                String destino = vertices.get(toIndex).getNombre();
                double peso = random.nextInt(100) + 1;

                addEdge(new Ruta(nextId++, origen, destino, peso));
            }
        }
    }

    // Implementación de Dijkstra
    public LinkedList<String> dijkstra(String start, String end) throws ListEmptyException {
        double[] distances = new double[numVertices];
        int[] previous = new int[numVertices];
        boolean[] visited = new boolean[numVertices];

        // Inicialización
        for (int i = 0; i < numVertices; i++) {
            distances[i] = Double.POSITIVE_INFINITY;
            previous[i] = -1;
            visited[i] = false;
        }

        int startIndex = getVertexIndex(start);
        if (startIndex == -1) {
            throw new IllegalArgumentException("Nodo inicial no encontrado: " + start);
        }

        distances[startIndex] = 0;

        for (int count = 0; count < numVertices - 1; count++) {
            int u = minDistance(distances, visited);
            if (u == -1) break;
            
            visited[u] = true;

            // Actualizar distancias de los vértices adyacentes
            for (Ruta edge : edges) {
                if (edge.getOrigen().equals(vertices.get(u).getNombre())) {
                    int v = getVertexIndex(edge.getDestino());
                    if (v != -1 && !visited[v]) {
                        double newDist = distances[u] + edge.getPeso();
                        if (newDist < distances[v]) {
                            distances[v] = newDist;
                            previous[v] = u;
                        }
                    }
                }
            }
        }

        return constructPath(previous, getVertexIndex(end));
    }

    // Implementación de Floyd-Warshall
    public double[][] floydWarshall() throws ListEmptyException {
        long startTime = System.nanoTime();

        double[][] dist = new double[numVertices][numVertices];

        // Inicializar matriz de distancias
        for (int i = 0; i < numVertices; i++) {
            for (int j = 0; j < numVertices; j++) {
                if (i == j) {
                    dist[i][j] = 0;
                } else {
                    dist[i][j] = Double.POSITIVE_INFINITY;
                }
            }
        }

        // Llenar con pesos de aristas existentes
        for (Ruta edge : edges) {
            int i = getVertexIndex(edge.getOrigen());
            int j = getVertexIndex(edge.getDestino());
            if (i != -1 && j != -1) {
                dist[i][j] = edge.getPeso();
            }
        }

        // Algoritmo Floyd-Warshall
        for (int k = 0; k < numVertices; k++) {
            for (int i = 0; i < numVertices; i++) {
                for (int j = 0; j < numVertices; j++) {
                    if (dist[i][k] != Double.POSITIVE_INFINITY && 
                        dist[k][j] != Double.POSITIVE_INFINITY && 
                        dist[i][k] + dist[k][j] < dist[i][j]) {
                        dist[i][j] = dist[i][k] + dist[k][j];
                    }
                }
            }
        }

        long endTime = System.nanoTime();
        System.out.println("Floyd-Warshall execution time: " + (endTime - startTime) + " ns");

        return dist;
    }

    // Implementación de Bellman-Ford
    public double[] bellmanFord(String start) throws ListEmptyException {
        long startTime = System.nanoTime();

        double[] distances = new double[numVertices];
        int startIndex = getVertexIndex(start);

        if (startIndex == -1) {
            throw new IllegalArgumentException("Nodo inicial no encontrado: " + start);
        }

        // Inicialización
        for (int i = 0; i < numVertices; i++) {
            distances[i] = Double.POSITIVE_INFINITY;
        }
        distances[startIndex] = 0;

        // Relajación de aristas
        for (int i = 1; i < numVertices; i++) {
            for (Ruta edge : edges) {
                int u = getVertexIndex(edge.getOrigen());
                int v = getVertexIndex(edge.getDestino());
                if (u != -1 && v != -1 && 
                    distances[u] != Double.POSITIVE_INFINITY && 
                    distances[u] + edge.getPeso() < distances[v]) {
                    distances[v] = distances[u] + edge.getPeso();
                }
            }
        }

        // Verificar ciclos negativos
        for (Ruta edge : edges) {
            int u = getVertexIndex(edge.getOrigen());
            int v = getVertexIndex(edge.getDestino());
            if (u != -1 && v != -1 && 
                distances[u] != Double.POSITIVE_INFINITY && 
                distances[u] + edge.getPeso() < distances[v]) {
                throw new RuntimeException("El grafo contiene un ciclo negativo");
            }
        }

        long endTime = System.nanoTime();
        System.out.println("Bellman-Ford execution time: " + (endTime - startTime) + " ns");

        return distances;
    }

    private int getVertexIndex(String vertexName) throws ListEmptyException {
        for (int i = 0; i < vertices.size(); i++) {
            if (vertices.get(i).getNombre().equals(vertexName)) {
                return i;
            }
        }
        return -1;
    }

    private int minDistance(double[] distances, boolean[] visited) {
        double min = Double.POSITIVE_INFINITY;
        int minIndex = -1;

        for (int i = 0; i < numVertices; i++) {
            if (!visited[i] && distances[i] <= min) {
                min = distances[i];
                minIndex = i;
            }
        }

        return minIndex;
    }

    private LinkedList<String> constructPath(int[] previous, int endIndex) throws ListEmptyException {
        if (endIndex == -1) {
            throw new IllegalArgumentException("Nodo final no encontrado");
        }

        LinkedList<String> path = new LinkedList<>();
        int currentIndex = endIndex;

        while (currentIndex != -1) {
            path.add(vertices.get(currentIndex).getNombre());
            currentIndex = previous[currentIndex];
        }

        // Invertir el camino para que vaya desde el inicio hasta el final
        LinkedList<String> reversedPath = new LinkedList<>();
        for (int i = path.size() - 1; i >= 0; i--) {
            reversedPath.add(path.get(i));
        }

        return reversedPath;
    }

    public LinkedList<Ubicacion> getVertices() {
        return vertices;
    }

    public LinkedList<Ruta> getEdges() {
        return edges;
    }

    public int getNumVertices() {
        return numVertices;
    }
}