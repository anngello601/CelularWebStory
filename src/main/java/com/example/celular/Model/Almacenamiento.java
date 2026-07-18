package com.example.celular.Model;

import jakarta.persistence.*;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "almacenamiento")
public class Almacenamiento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "capacidad", nullable = false, unique = true)
    private String capacidad;

    @OneToMany(mappedBy = "almacenamiento")
    @JsonIgnore 
    private List<Producto> productos;

    // Constructores
    public Almacenamiento() {
    }

    public Almacenamiento(String capacidad) {
        this.capacidad = capacidad;
    }

    // Getters y Setters
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCapacidad() {
        return capacidad;
    }

    public void setCapacidad(String capacidad) {
        this.capacidad = capacidad;
    }

    public List<Producto> getProductos() {
        return productos;
    }

    public void setProductos(List<Producto> productos) {
        this.productos = productos;
    }
}