package com.example.celular.Model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;

@Entity
@Table(name = "producto_definitivo")
public class Producto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @NotBlank(message = "El nombre es obligatorio")
    @Column(name = "nombre", nullable = false)
    private String nombre;

    // ===== NUEVAS RELACIONES =====
    @ManyToOne
    @JoinColumn(name = "marca_id")
    private Marca marca;

    @ManyToOne
    @JoinColumn(name = "categoria_id")
    private Categoria categoria;

    @ManyToOne
    @JoinColumn(name = "almacenamiento_id")
    private Almacenamiento almacenamiento;

    @ManyToOne
    @JoinColumn(name = "ram_id")
    private Ram ram;

    @ManyToOne
    @JoinColumn(name = "procesador_id")
    private Procesador procesador;
    // ===== FIN RELACIONES =====

    @Column(name = "precio_normal")
    private Double precioNormal;

    @Positive(message = "El precio debe ser mayor a cero")
    @Column(name = "precio", nullable = false)
    private double precio;

    @Column(name = "precio_tarjeta")
    private Double precioTarjeta;

    @Column(name = "stock")
    @Min(value = 0, message = "El stock no puede ser negativo")
    private int stock;

    @NotBlank(message = "La imagen es obligatoria")
    @Column(name = "imagen")
    private String imagen;

    @Column(name = "rating")
    private Double rating;

    @Column(name = "num_opiniones")
    private Integer numOpiniones;

    @Column(name = "ean_upc")
    private String eanUpc;

    @Column(name = "tiempo_envio")
    private String tiempoEnvio;

    @Column(name = "garantia", columnDefinition = "TEXT")
    private String garantia;

    @Column(name = "condiciones", columnDefinition = "TEXT")
    private String condiciones;

    @Column(name = "devoluciones", columnDefinition = "TEXT")
    private String devoluciones;

    // ===== Constructores =====
    public Producto() {
    }

    public Producto(String nombre, Marca marca, Categoria categoria, Almacenamiento almacenamiento,
            Ram ram, Procesador procesador, double precio, int stock, String imagen) {
        this.nombre = nombre;
        this.marca = marca;
        this.categoria = categoria;
        this.almacenamiento = almacenamiento;
        this.ram = ram;
        this.procesador = procesador;
        this.precio = precio;
        this.stock = stock;
        this.imagen = imagen;
    }

    // ===== Getters y Setters =====
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Marca getMarca() {
        return marca;
    }

    public void setMarca(Marca marca) {
        this.marca = marca;
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }

    public Almacenamiento getAlmacenamiento() {
        return almacenamiento;
    }

    public void setAlmacenamiento(Almacenamiento almacenamiento) {
        this.almacenamiento = almacenamiento;
    }

    public Ram getRam() {
        return ram;
    }

    public void setRam(Ram ram) {
        this.ram = ram;
    }

    public Procesador getProcesador() {
        return procesador;
    }

    public void setProcesador(Procesador procesador) {
        this.procesador = procesador;
    }

    public Double getPrecioNormal() {
        return precioNormal;
    }

    public void setPrecioNormal(Double precioNormal) {
        this.precioNormal = precioNormal;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public Double getPrecioTarjeta() {
        return precioTarjeta;
    }

    public void setPrecioTarjeta(Double precioTarjeta) {
        this.precioTarjeta = precioTarjeta;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    public Double getRating() {
        return rating;
    }

    public void setRating(Double rating) {
        this.rating = rating;
    }

    public Integer getNumOpiniones() {
        return numOpiniones;
    }

    public void setNumOpiniones(Integer numOpiniones) {
        this.numOpiniones = numOpiniones;
    }

    public String getEanUpc() {
        return eanUpc;
    }

    public void setEanUpc(String eanUpc) {
        this.eanUpc = eanUpc;
    }

    public String getTiempoEnvio() {
        return tiempoEnvio;
    }

    public void setTiempoEnvio(String tiempoEnvio) {
        this.tiempoEnvio = tiempoEnvio;
    }

    public String getGarantia() {
        return garantia;
    }

    public void setGarantia(String garantia) {
        this.garantia = garantia;
    }

    public String getCondiciones() {
        return condiciones;
    }

    public void setCondiciones(String condiciones) {
        this.condiciones = condiciones;
    }

    public String getDevoluciones() {
        return devoluciones;
    }

    public void setDevoluciones(String devoluciones) {
        this.devoluciones = devoluciones;
    }
}