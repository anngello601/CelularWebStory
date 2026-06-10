package com.example.celular.Controller;

import com.example.celular.Model.*;
import com.example.celular.Service.ProductoService;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class WebController {

    @Autowired
    private ProductoService service;

    @GetMapping("/")
    public String raiz() {
        return "redirect:/inicio";
    }

    @GetMapping("/inicio")
    public String inicio() {
        return "inicio";
    }

    // LISTAR PRODUCTOS CON FILTRO POR MARCA
    @GetMapping("/productos")
    public String listar(
            @RequestParam(value = "marca", required = false) List<String> marcasSeleccionadas,
            Model model) {

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

    // BUSQUEDA DINAMICA
    @GetMapping("/api/productos/buscar")
    @ResponseBody
    public List<Producto> buscarProductos(
            @RequestParam String q) {

        return service.buscarPorNombre(q);
    }

    @PostMapping("/api/carrito/agregar")
    @ResponseBody
    public Map<String, Object> agregarAlCarrito(
            @RequestParam Integer productoId,
            @RequestParam int cantidad,
            HttpSession session) {

        Map<String, Object> resp = new HashMap<>();
        if (session.getAttribute("usuario") == null) {
            resp.put("ok", false);
            resp.put("mensaje", "Debes iniciar sesión");
            return resp;
        }
        return service.reducirStock(productoId, cantidad);
    }

    @GetMapping("/carrito")
    public String carrito() {
        return "Carrito";
    }

    @GetMapping("/compra")
    public String compra() {
        return "Compra";
    }

    @GetMapping("/ayuda")
    public String ayuda() {
        return "Ayuda";
    }

    @GetMapping("/contactanos")
    public String contactanos() {
        return "Contactanos";
    }

    @GetMapping("/garantia")
    public String garantia() {
        return "Garantia";
    }
}
