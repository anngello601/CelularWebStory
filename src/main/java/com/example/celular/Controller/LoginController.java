package com.example.celular.Controller;

import com.example.celular.Model.User;
import com.example.celular.Repository.UserRepository;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
public class LoginController {


    private final UserRepository userRepository;

    LoginController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
    

    // LOGIN FORM
    @GetMapping("/login")
    public String mostrarLogin() {
        return "sesion";
    }

    // LOGIN PROCESS
    @PostMapping("/login")
    public String login(
            @RequestParam String correo,
            @RequestParam String password,
            HttpSession session,
            Model model) {

        if (password.length() < 6) {
            model.addAttribute("passwordError", "La contraseña debe tener al menos 6 caracteres");
            return "sesion";
        }

        User user = userRepository.findByCorreo(correo);

        if (user != null && user.getPassword().equals(password)) {
            session.setAttribute("usuario", user);
            return "redirect:/inicio";
        }

        model.addAttribute("error", true);
        return "sesion";
    }

    // LOGOUT
    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/";
    }

    // REGISTRO FORM
    @GetMapping("/registro")
    public String mostrarRegistro(Model model) {
        model.addAttribute("user", new User());
        return "registro";
    }

    // REGISTRO PROCESS
    @PostMapping("/registro")
    public String registrar(@Valid @ModelAttribute User user, BindingResult result) {
        if (result.hasErrors()) {
            return "registro";
        }
        userRepository.save(user);
        return "redirect:/login";
    }

    // VER PERFIL
    @GetMapping("/perfil")
    public String verPerfil(HttpSession session, Model model,
                             @RequestParam(required = false) String exito) {
        User usuario = (User) session.getAttribute("usuario");
        if (usuario == null) return "redirect:/login";
        model.addAttribute("usuario", usuario);
        if ("true".equals(exito)) model.addAttribute("exito", true);
        return "perfil";
    }

    // EDITAR PERFIL - FORM
    @GetMapping("/perfil/editar")
    public String mostrarEditar(HttpSession session, Model model) {
        User usuario = (User) session.getAttribute("usuario");
        if (usuario == null) return "redirect:/login";
        User userFromDB = userRepository.findById(Long.valueOf(usuario.getId())).orElse(null);
        if (userFromDB == null) return "redirect:/login";
        model.addAttribute("user", userFromDB);
        return "editarPerfil";
    }

    // EDITAR PERFIL - PROCESS
    @PostMapping("/perfil/editar")
    public String editarPerfil(
            @RequestParam String nombre,
            @RequestParam String correo,
            @RequestParam(required = false) String nuevaPassword,
            HttpSession session,
            Model model) {

        User usuario = (User) session.getAttribute("usuario");
        if (usuario == null) return "redirect:/login";

        User userFromDB = userRepository.findById(Long.valueOf(usuario.getId())).orElse(null);
        if (userFromDB == null) return "redirect:/login";

        if (nombre.isBlank()) {
            model.addAttribute("error", "El nombre es obligatorio");
            model.addAttribute("user", userFromDB);
            return "editarPerfil";
        }

        if (nuevaPassword != null && !nuevaPassword.isBlank() && nuevaPassword.length() < 6) {
            model.addAttribute("error", "La contraseña debe tener al menos 6 caracteres");
            model.addAttribute("user", userFromDB);
            return "editarPerfil";
        }

        userFromDB.setNombre(nombre.trim());
        userFromDB.setCorreo(correo.trim());
        if (nuevaPassword != null && !nuevaPassword.isBlank()) {
            userFromDB.setPassword(nuevaPassword);
        }

        userRepository.save(userFromDB);
        session.setAttribute("usuario", userFromDB);
        return "redirect:/perfil?exito=true";
    }

    // ELIMINAR CUENTA
    @PostMapping("/perfil/eliminar")
    public String eliminarCuenta(HttpSession session) {
        User usuario = (User) session.getAttribute("usuario");
        if (usuario == null) return "redirect:/login";
        userRepository.deleteById(Long.valueOf(usuario.getId()));
        session.invalidate();
        return "redirect:/";
    }
}