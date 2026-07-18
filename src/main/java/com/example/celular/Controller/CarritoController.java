package com.example.celular.Controller;

import com.example.celular.Service.ProductoService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@Controller
public class CarritoController {

    private final ProductoService service;

    public CarritoController(ProductoService service) {
        this.service = service;
    }

    // AGREGAR AL CARRITO (API)
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

    // VER CARRITO
    @GetMapping("/carrito")
    public String verCarrito() {
        return "Carrito";
    }

    // COMPRA (CHECKOUT)
    @GetMapping("/compra")
    public String compra() {
        return "Compra";
    }
}