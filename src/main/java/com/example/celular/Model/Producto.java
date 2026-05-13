package com.example.celular.Model;

public class Producto {
    private int id;
    private String nombre;
    private double precio;
    private int stock;
    private String imagen;

    // Constructor
    public Producto(int id, String nombre, double precio, int stock, String imagen) {
        this.id = id;
        this.nombre = nombre;
        this.precio = precio;
        this.stock = stock;
        this.imagen = imagen;
    }

    // Getters y Setters
    public int getId() { return id; }
    public String getNombre() { return nombre; }
    public double getPrecio() { return precio; }
    public int getStock() { return stock; }
    public String getImagen() { return imagen; }
}