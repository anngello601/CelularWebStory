package com.example.celular.Controller;

import com.example.celular.Model.User;
import com.example.celular.Repository.UserRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class LoginController {

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/login")
    public String mostrarLogin() {
        return "sesion";
    }

    @PostMapping("/login")
    public String login(
            @RequestParam String correo,
            @RequestParam String password,
            HttpSession session,
            Model model) {

        User user = userRepository.findByCorreoAndPassword(
                correo,
                password);

        if (user != null) {

            session.setAttribute("usuario", user);

            return "redirect:/inicio";
        }

        model.addAttribute("error", true);

        return "sesion";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {

        session.invalidate();

        return "redirect:/";
    }
}