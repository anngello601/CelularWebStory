package com.example.celular.Controller;

import com.example.celular.Model.Producto;
import com.example.celular.Repository.MarcaRepository;
import com.example.celular.Service.ProductoService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class ProductoController {

    private final ProductoService service;
    private final MarcaRepository marcaRepo;

    // Constructor actualizado
    public ProductoController(ProductoService service, MarcaRepository marcaRepo) {
        this.service = service;
        this.marcaRepo = marcaRepo;
    }

    // LISTAR PRODUCTOS CON FILTRO POR MARCA
    @GetMapping("/productos")
    public String listar(
            @RequestParam(value = "marca", required = false) List<String> marcasSeleccionadas,
            Model model) {

        // 🔴 CARGAR MARCAS DESDE LA BASE DE DATOS
        model.addAttribute("marcas", marcaRepo.findAll());

        // Filtrar productos
        if (marcasSeleccionadas != null && !marcasSeleccionadas.isEmpty()) {
            model.addAttribute("productos", service.buscarPorMarcas(marcasSeleccionadas));
            model.addAttribute("marcasSeleccionadas", marcasSeleccionadas);
        } else {
            model.addAttribute("productos", service.listar());
        }

        return "productos";
    }

    // DETALLE DE PRODUCTO
    @GetMapping("/producto/{id}")
    public String verDetalle(@PathVariable Integer id, Model model) {
        Producto producto = service.buscarPorId(id);
        if (producto == null) {
            return "redirect:/productos";
        }
        model.addAttribute("producto", producto);
        return "detalle";
    }

    // BÚSQUEDA DINÁMICA (API)
    @GetMapping("/api/productos/buscar")
    @ResponseBody
    public List<Producto> buscarProductos(@RequestParam String q) {
        return service.buscarPorNombre(q);
    }
}