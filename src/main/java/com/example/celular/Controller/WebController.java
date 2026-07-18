package com.example.celular.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

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