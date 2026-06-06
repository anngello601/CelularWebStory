package com.example.celular.Model;

import jakarta.persistence.*;

@Entity
@Table(name = "producto")
public class Producto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private String nombre;

    private String marca;
    
    private String categoria; // Nuevo

    @Column(name = "almacenamiento")
    private String almacenamiento;
    
    private String ram;
    
    private String procesador;

    // --- Precios ---
    @Column(name = "precio_normal")
    private Double precioNormal; // Nuevo (Precio tachado)
    
    @Column(nullable = false)
    private double precio; // Precio Internet
    
    @Column(name = "precio_tarjeta")
    private Double precioTarjeta; // Nuevo

    private int stock;
    private String imagen;
    
    // --- Métricas ---
    private Double rating; // Nuevo (Estrellas)
    
    @Column(name = "num_opiniones")
    private Integer numOpiniones; // Nuevo

    // --- Detalles Comerciales ---
    @Column(name = "ean_upc")
    private String eanUpc; // Nuevo
    
    @Column(name = "tiempo_envio")
    private String tiempoEnvio; // Nuevo

    private String garantia;

    @Lob
    @Column(columnDefinition = "TEXT")
    private String condiciones;

    @Lob
    @Column(columnDefinition = "TEXT")
    private String devoluciones;

    // ==========================================
    // Constructores
    // ==========================================
    
    public Producto() {}

    public Producto(Integer id, String nombre, String marca, String categoria, String almacenamiento, String ram,
            String procesador, Double precioNormal, double precio, Double precioTarjeta, int stock, String imagen,
            Double rating, Integer numOpiniones, String eanUpc, String tiempoEnvio, String garantia, String condiciones,
            String devoluciones) {
        this.id = id;
        this.nombre = nombre;
        this.marca = marca;
        this.categoria = categoria;
        this.almacenamiento = almacenamiento;
        this.ram = ram;
        this.procesador = procesador;
        this.precioNormal = precioNormal;
        this.precio = precio;
        this.precioTarjeta = precioTarjeta;
        this.stock = stock;
        this.imagen = imagen;
        this.rating = rating;
        this.numOpiniones = numOpiniones;
        this.eanUpc = eanUpc;
        this.tiempoEnvio = tiempoEnvio;
        this.garantia = garantia;
        this.condiciones = condiciones;
        this.devoluciones = devoluciones;
    }

    // ==========================================
    // Getters y Setters
    // ==========================================

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getMarca() { return marca; }
    public void setMarca(String marca) { this.marca = marca; }

    public String getCategoria() { return categoria; }
    public void setCategoria(String categoria) { this.categoria = categoria; }

    public String getAlmacenamiento() { return almacenamiento; }
    public void setAlmacenamiento(String almacenamiento) { this.almacenamiento = almacenamiento; }

    public String getRam() { return ram; }
    public void setRam(String ram) { this.ram = ram; }

    public String getProcesador() { return procesador; }
    public void setProcesador(String procesador) { this.procesador = procesador; }

    public Double getPrecioNormal() { return precioNormal; }
    public void setPrecioNormal(Double precioNormal) { this.precioNormal = precioNormal; }

    public double getPrecio() { return precio; }
    public void setPrecio(double precio) { this.precio = precio; }

    public Double getPrecioTarjeta() { return precioTarjeta; }
    public void setPrecioTarjeta(Double precioTarjeta) { this.precioTarjeta = precioTarjeta; }

    public int getStock() { return stock; }
    public void setStock(int stock) { this.stock = stock; }

    public String getImagen() { return imagen; }
    public void setImagen(String imagen) { this.imagen = imagen; }

    public Double getRating() { return rating; }
    public void setRating(Double rating) { this.rating = rating; }

    public Integer getNumOpiniones() { return numOpiniones; }
    public void setNumOpiniones(Integer numOpiniones) { this.numOpiniones = numOpiniones; }

    public String getEanUpc() { return eanUpc; }
    public void setEanUpc(String eanUpc) { this.eanUpc = eanUpc; }

    public String getTiempoEnvio() { return tiempoEnvio; }
    public void setTiempoEnvio(String tiempoEnvio) { this.tiempoEnvio = tiempoEnvio; }

    public String getGarantia() { return garantia; }
    public void setGarantia(String garantia) { this.garantia = garantia; }

    public String getCondiciones() { return condiciones; }
    public void setCondiciones(String condiciones) { this.condiciones = condiciones; }

    public String getDevoluciones() { return devoluciones; }
    public void setDevoluciones(String devoluciones) { this.devoluciones = devoluciones; }
}
