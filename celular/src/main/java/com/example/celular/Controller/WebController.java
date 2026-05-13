package com.example.celular.Controller;

import com.example.celular.Model.Producto;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
public class WebController {

    @GetMapping("/")
    public String raiz() {
        return "redirect:/inicio";
    }

    @GetMapping("/inicio")
    public String inicio() {
        return "inicio";
    }

    // 🔥 PRODUCTOS DINÁMICOS
    @GetMapping("/productos")
    public String productos(Model model) {

        List<Producto> lista = new ArrayList<>();

        lista.add(new Producto(1, "Galaxy A04", 800, 10, "Galaxy A04.png"));
        lista.add(new Producto(2, "Galaxy S23 Ultra", 4500, 5, "Galaxy S23 Ultra.png"));

        model.addAttribute("productos", lista);

        return "productos";
    }

    @GetMapping("/producto")
    public String infoProducto() {
        return "Info_producto";
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

    @GetMapping("/login")
    public String login() {
    return "sesion";
}
}