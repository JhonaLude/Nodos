/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.tda.list;

/**
 *
 * @author Jhonathan Alexander Ludeña Cevallos
 */

import java.lang.reflect.Method;
import com.example.exception.ListEmptyException;
import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 *
 *
 */
public class LinkedList<E> implements Iterable<E> {

    private Node<E> header;
    private Node<E> last;
    private Integer size;
    public static Integer ASC = 1;
    public static Integer DESC = 0;

    /**
     * Clase que permite realizar una lista enlazada
     *
     */
    public LinkedList() {
        this.header = null;
        this.last = null;
        this.size = 0;
    }

    public Boolean isEmpty() {
        return (this.header == null || this.size == 0);
    }

    private void addHeader(E dato) {
        Node<E> help;
        if (isEmpty()) {
            help = new Node<>(dato);
            header = help;
            this.last = help;
        } else {
            Node<E> healpHeader = this.header;
            help = new Node<>(dato, healpHeader);
            this.header = help;

            // this.size++;
        }
        this.size++;
    }

    private void addLast(E info) {
        Node<E> help;
        if (isEmpty()) {
            addHeader(info);
        } else {
            help = new Node<>(info, null);
            last.setNext(help);
            last = help;
            this.size++;
        }
    }

    public boolean add(E info) {
        addLast(info);
        return true; // O implementar una lógica más robusta de validación
    }

    // ********** GET */
    private Node<E> getNode(Integer index) throws ListEmptyException, IndexOutOfBoundsException {
        if (isEmpty()) {
            throw new ListEmptyException("Error, List empty");
        } else if (index.intValue() < 0 || index.intValue() >= this.size) {
            throw new IndexOutOfBoundsException("Error, fuera de rango");
        } else if (index.intValue() == 0) {
            return header;
        } else if (index.intValue() == (this.size - 1)) {
            return last;
        } else {
            Node<E> search = header;
            int cont = 0;
            while (cont < index.intValue()) {
                cont++;
                search = search.getNext();
            }
            return search;
        }
    }

    private E getFirst() throws ListEmptyException {
        if (isEmpty()) {
            throw new ListEmptyException("Error, Lista vacia");
        }
        return header.getInfo();
    }

    private E getLast() throws ListEmptyException {
        if (isEmpty()) {
            throw new ListEmptyException("Error, Lista vacia");
        }
        return last.getInfo();
    }

    public E get(Integer index) throws ListEmptyException, IndexOutOfBoundsException {
        if (isEmpty()) {
            throw new ListEmptyException("Error, List empty");
        } else if (index.intValue() < 0 || index.intValue() >= this.size.intValue()) {
            throw new IndexOutOfBoundsException("Error, fuera de rango");
        } else if (index.intValue() == 0) {
            return header.getInfo();
        } else if (index.intValue() == (this.size - 1)) {
            return last.getInfo();
        } else {
            Node<E> search = header;
            int cont = 0;
            while (cont < index.intValue()) {
                cont++;
                search = search.getNext();
            }
            return search.getInfo();
        }

    }

    /**
     * ******* END GET
     */
    /**
     * * ADD BY POSITION
     */
    public void add(E info, Integer index) throws ListEmptyException, IndexOutOfBoundsException {
        if (isEmpty() || index.intValue() == 0) {
            addHeader(info);
        } else if (index.intValue() == this.size.intValue()) {
            addLast(info);
        } else {

            Node<E> search_preview = getNode(index - 1);
            Node<E> search = getNode(index);
            Node<E> help = new Node<>(info, search);
            search_preview.setNext(help);
            this.size++;
        }
    }

    /**
     * * END BY POSITION
     */
    public void reset() {
        this.header = null;
        this.last = null;
        this.size = 0;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("List Data \n");
        try {

            Node<E> help = header;
            while (help != null) {
                sb.append(help.getInfo()).append(" -> ");
                help = help.getNext();
            }
        } catch (Exception e) {
            sb.append(e.getMessage());
        }

        return sb.toString();
    }

    public Integer getSize() {
        return this.size;
    }

    public E[] toArray() {
        E[] matrix = null;
        if (!isEmpty()) {
            Class clazz = header.getInfo().getClass();
            matrix = (E[]) java.lang.reflect.Array.newInstance(clazz, size);
            Node<E> aux = header;
            for (int i = 0; i < this.size; i++) {
                matrix[i] = aux.getInfo();
                aux = aux.getNext();
            }
        }
        return matrix;
    }

    public LinkedList<E> toList(E[] matrix) {
        reset();
        for (int i = 0; i < matrix.length; i++) {
            this.add(matrix[i]);
        }
        return this;
    }

    public void update(E data, Integer post) throws ListEmptyException {
        if (isEmpty()) {
            throw new ListEmptyException("Error, la lista esta vacia");
        } else if (post < 0 || post >= size) {
            throw new IndexOutOfBoundsException("Error, esta fuera de los limites de la lista");
        } else if (post == 0) {
            header.setInfo(data);
        } else if (post == (size - 1)) {
            last.setInfo(data);
        } else {
            // 2 5 6 9 --> 2
            Node<E> search = header;
            Integer cont = 0;
            while (cont < post) {
                cont++;
                search = search.getNext();
            }
            search.setInfo(data);
        }
    }

    public E deleteFirst() throws ListEmptyException {
        if (isEmpty()) {
            throw new ListEmptyException("Lista vacia");
        } else {
            E element = header.getInfo();
            Node<E> aux = header.getNext();
            header = aux;
            if (size.intValue() == 1) {
                last = null;
            }
            size--;
            return element;
        }
    }

    public E deleteLast() throws ListEmptyException {
        if (isEmpty()) {
            throw new ListEmptyException("Lista vacia");
        } else {
            E element = last.getInfo();
            Node<E> aux = getNode(size - 2);
            if (aux == null) {
                last = null;
                if (size == 2) {
                    last = header;
                } else {
                    header = null;
                }
            } else {
                last = null;
                last = aux;
                last.setNext(null);
            }
            size--;
            return element;
        }
    }

    public E delete(Integer post) throws ListEmptyException {
        if (isEmpty()) {
            throw new ListEmptyException("Error, la lista esta vacia");
        } else if (post < 0 || post >= size) {
            throw new IndexOutOfBoundsException("Error, esta fuera de los limites de la lista");
        } else if (post == 0) {
            return deleteFirst();
        } else if (post == (size - 1)) {
            return deleteLast();
        } else {
            Node<E> preview = getNode(post - 1);
            Node<E> actually = getNode(post);
            E element = preview.getInfo();
            Node<E> next = actually.getNext();
            actually = null;
            preview.setNext(next);
            size--;
            return element;
        }
    }

    public LinkedList<E> order(Integer type) throws Exception {
        if (!isEmpty()) {
            E data = this.header.getInfo();
            if (data instanceof Number || data instanceof String) {
                E[] lista = this.toArray();
                reset();
                for (int i = 1; i < lista.length; i++) {
                    E aux = lista[i]; // valor a ordenar
                    int j = i - 1; // índice anterior
                    while (j >= 0 && compare(lista[j], aux, type)) {
                        lista[j + 1] = lista[j--]; // desplaza elementos hacia la derecha
                    }
                    lista[j + 1] = aux; // inserta el valor en su posición correcta
                }
                this.toList(lista);
            }
        }
        return this;
    }

    public LinkedList<E> order(String attribute, Integer type) throws Exception {
        if (!isEmpty()) {
            E data = this.header.getInfo();
            if (data instanceof Object) {
                E[] lista = this.toArray();
                reset();
                for (int i = 1; i < lista.length; i++) {
                    E aux = lista[i]; // valor a ordenar
                    int j = i - 1; // índice anterior
                    while (j >= 0 && atrribute_compare(attribute, lista[j], aux, type)) {
                        lista[j + 1] = lista[j--]; // desplaza elementos hacia la derecha
                    }
                    lista[j + 1] = aux; // inserta el valor en su posición correcta
                }
                this.toList(lista);
            }
        }
        return this;
    }

    private Boolean compare(Object a, Object b, Integer type) {
        switch (type) {
            case 0:
                if (a instanceof Number) {
                    Number a1 = (Number) a;
                    Number b1 = (Number) b;
                    return a1.doubleValue() > b1.doubleValue();
                } else {
                    // "casa" > "pedro"
                    return (a.toString()).compareTo(b.toString()) > 0;
                }
            // break;

            default:
                // mayor a menor
                if (a instanceof Number) {
                    Number a1 = (Number) a;
                    Number b1 = (Number) b;
                    return a1.doubleValue() < b1.doubleValue();
                } else {
                    // "casa" > "pedro"
                    return (a.toString()).compareTo(b.toString()) < 0;
                }
            // break;
        }

    }

    // compare class
    private Boolean atrribute_compare(String attribute, E a, E b, Integer type) throws Exception {
        return compare(exist_attribute(a, attribute), exist_attribute(b, attribute), type);
    }

    private Object exist_attribute(E a, String attribute) throws Exception {
        Method method = null;
        attribute = attribute.substring(0, 1).toUpperCase() + attribute.substring(1);
        attribute = "get" + attribute;
        for (Method aux : a.getClass().getMethods()) {
            if (aux.getName().contains(attribute)) {
                method = aux;
                break;
            }
        }
        if (method == null) {
            for (Method aux : a.getClass().getSuperclass().getMethods()) {
                if (aux.getName().contains(attribute)) {
                    method = aux;
                    break;
                }
            }
        }
        if (method != null) {
            return method.invoke(a);
        }

        return null;
    }

    @Override
    public Iterator<E> iterator() {
        return new Iterator<E>() {
            private Node<E> current = header;

            @Override
            public boolean hasNext() {
                return current != null;
            }

            @Override
            public E next() {
                if (!hasNext()) {
                    throw new NoSuchElementException();
                }
                E data = current.getInfo(); 
                current = current.getNext(); 
                return data;
            }
        };
    }

    public int size() {
        return this.size; 
    }

    public void add(int i, String current) {
        throw new UnsupportedOperationException("Not supported yet."); 
    }

    public void add(int index, E element) {
        try {
            add(element, index);
        } catch (ListEmptyException | IndexOutOfBoundsException e) {
            throw new RuntimeException(e);
        }
    }

    private class Nodo {

        String nombre;
        Map<Nodo, Double> conexiones;

        Nodo(String nombre) {
            this.nombre = nombre;
            this.conexiones = new HashMap<>();
        }
    }

    private Map<String, Nodo> nodos = new HashMap<>();

    // Agregar un nodo al grafo
    public void agregarNodo(String nombre) {
        nodos.putIfAbsent(nombre, new Nodo(nombre));
    }

    // Agregar una conexión entre nodos con un peso
    public void agregarConexion(String origen, String destino, double peso) {
        Nodo nodoOrigen = nodos.get(origen);
        Nodo nodoDestino = nodos.get(destino);

        if (nodoOrigen == null || nodoDestino == null) {
            throw new IllegalArgumentException("Uno o ambos nodos no existen");
        }

        nodoOrigen.conexiones.put(nodoDestino, peso);
    }

    // Método de Dijkstra para encontrar el camino más corto desde un nodo origen
    public void dijkstra(String inicio) {
        Nodo nodoInicio = nodos.get(inicio);

        if (nodoInicio == null) {
            throw new IllegalArgumentException("El nodo inicial no existe");
        }

        Map<Nodo, Double> distancias = new HashMap<>();
        Map<Nodo, Nodo> predecesores = new HashMap<>();
        PriorityQueue<Nodo> cola = new PriorityQueue<>((a, b) -> Double.compare(distancias.get(a), distancias.get(b)));

        for (Nodo nodo : nodos.values()) {
            distancias.put(nodo, Double.POSITIVE_INFINITY);
        }
        distancias.put(nodoInicio, 0.0);

        cola.add(nodoInicio);

        while (!cola.isEmpty()) {
            Nodo actual = cola.poll();

            for (Map.Entry<Nodo, Double> conexion : actual.conexiones.entrySet()) {
                Nodo vecino = conexion.getKey();
                double peso = conexion.getValue();

                double nuevaDistancia = distancias.get(actual) + peso;

                if (nuevaDistancia < distancias.get(vecino)) {
                    distancias.put(vecino, nuevaDistancia);
                    predecesores.put(vecino, actual);
                    cola.add(vecino);
                }
            }
        }

        System.out.println("Distancias desde " + inicio + ":");
        for (Map.Entry<Nodo, Double> entrada : distancias.entrySet()) {
            System.out.println("A " + entrada.getKey().nombre + " -> " + entrada.getValue());
        }
    }

    // Método de Floyd-Warshall para calcular las distancias más cortas 
    public void floydWarshall() {
        int n = nodos.size();
        double[][] distancias = new double[n][n];
        String[] nombresNodos = nodos.keySet().toArray(new String[0]);

        // Inicializar matriz de distancias
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (i == j) {
                    distancias[i][j] = 0;
                } else {
                    Nodo nodoI = nodos.get(nombresNodos[i]);
                    Nodo nodoJ = nodos.get(nombresNodos[j]);

                    distancias[i][j] = nodoI.conexiones.getOrDefault(nodoJ, Double.POSITIVE_INFINITY);
                }
            }
        }

        // Aplicar el algoritmo de Floyd-Warshall
        for (int k = 0; k < n; k++) {
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n; j++) {
                    if (distancias[i][k] + distancias[k][j] < distancias[i][j]) {
                        distancias[i][j] = distancias[i][k] + distancias[k][j];
                    }
                }
            }
        }

        // Mostrar resultados
        System.out.println("Matriz de distancias (Floyd-Warshall):");
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                System.out.print((distancias[i][j] == Double.POSITIVE_INFINITY ? "INF" : distancias[i][j]) + "\t");
            }
            System.out.println();
        }
    }
}
