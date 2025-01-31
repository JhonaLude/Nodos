/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.exception;

/**
 *
 * @author Alexander Ludena
 */
public class ListEmptyException extends Exception {
    public ListEmptyException() {}
    public ListEmptyException(String msg) {
        super(msg);
    }    
}

