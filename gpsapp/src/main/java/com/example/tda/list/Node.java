/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.tda.list;

/**
 *
 * @author Jhonathan Alexander Ludeña Cevallos
 */

//datos genéricos T E, K , V
//T significa que puede tomar cualquier tipo de dato: Integer, String, ObjectModel, ect.
public class Node<E> {
    private E info;
    //Clase recursiva porque se apunta a sí misma
    Node<E> next;

    //Apunta a Nulo
    public Node(E info) {
        this.info = info;
        this.next = null;
    }

    //Apunta al siguiente
    public Node(E info, Node<E> next) {
        this.info = info;
        this.next = next;
    }

    public E getInfo() {
        return this.info;
    }

    public void setInfo(E info) {
        this.info = info;
    }

    public Node<E> getNext() {
        return this.next;
    }

    public void setNext(Node<E> next) {
        this.next = next;
    }

}

