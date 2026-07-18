package com.example.celular.Controller;

import com.example.celular.Model.Rol;
import com.example.celular.Model.User;
import com.example.celular.Repository.RolRepository;
import com.example.celular.Repository.UserRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/admin/usuarios")
public class AdminUsuarioController {

    private final UserRepository userRepository;
    private final RolRepository rolRepository; // 🔴 Nuevo

    public AdminUsuarioController(UserRepository userRepository, RolRepository rolRepository) {
        this.userRepository = userRepository;
        this.rolRepository = rolRepository;
    }

    // Listar usuarios
    @GetMapping
    public String listarUsuarios(HttpSession session, Model model) {
        User usuario = (User) session.getAttribute("usuario");
        if (usuario == null || !usuario.isAdmin()) {
            return "redirect:/login";
        }
        model.addAttribute("usuarios", userRepository.findAll());
        model.addAttribute("roles", rolRepository.findAll()); // Para llenar selects
        return "adminUsuarios";
    }

    // Guardar nuevo usuario
    @PostMapping("/guardar")
    public String guardarUsuario(@RequestParam String nombre,
            @RequestParam String correo,
            @RequestParam String rolNombre, // Recibimos el nombre del rol
            @RequestParam(required = false) String password,
            RedirectAttributes redirectAttrs) {
        try {
            if (userRepository.findByCorreo(correo) != null) {
                redirectAttrs.addFlashAttribute("error", "El correo ya está registrado");
                return "redirect:/admin/usuarios";
            }

            if (password == null || password.trim().isEmpty() || password.length() < 6) {
                redirectAttrs.addFlashAttribute("error", "La contraseña debe tener al menos 6 caracteres");
                return "redirect:/admin/usuarios";
            }

            // 🔴 Buscar el rol por nombre
            Rol rol = rolRepository.findByNombre(rolNombre)
                    .orElse(rolRepository.findByNombre("USUARIO")
                            .orElseThrow(() -> new RuntimeException("Rol USUARIO no encontrado")));

            User user = new User();
            user.setNombre(nombre);
            user.setCorreo(correo);
            user.setPassword(password);
            user.setRol(rol);
            user.setActivo(true);

            userRepository.save(user);
            redirectAttrs.addFlashAttribute("mensaje", "Usuario creado correctamente");
        } catch (Exception e) {
            redirectAttrs.addFlashAttribute("error", "Error al crear usuario: " + e.getMessage());
        }
        return "redirect:/admin/usuarios";
    }

    // Editar usuario
    @PostMapping("/editar")
    public String editarUsuario(@RequestParam Integer id,
            @RequestParam String nombre,
            @RequestParam String correo,
            @RequestParam String rolNombre,
            @RequestParam(required = false) String password,
            @RequestParam(required = false) Boolean activo,
            HttpSession session,
            RedirectAttributes redirectAttrs) {
        try {
            User existente = userRepository.findById(id).orElse(null);
            if (existente == null) {
                redirectAttrs.addFlashAttribute("error", "Usuario no encontrado");
                return "redirect:/admin/usuarios";
            }

            existente.setNombre(nombre);
            existente.setCorreo(correo);
            if (activo != null)
                existente.setActivo(activo);

            // 🔴 Actualizar rol
            Rol rol = rolRepository.findByNombre(rolNombre)
                    .orElseThrow(() -> new RuntimeException("Rol no encontrado: " + rolNombre));
            existente.setRol(rol);

            if (password != null && !password.trim().isEmpty()) {
                if (password.length() < 6) {
                    redirectAttrs.addFlashAttribute("error", "La contraseña debe tener al menos 6 caracteres");
                    return "redirect:/admin/usuarios";
                }
                existente.setPassword(password);
            }

            userRepository.save(existente);

            // Actualizar sesión si es el mismo usuario
            User usuarioSesion = (User) session.getAttribute("usuario");
            if (usuarioSesion != null && usuarioSesion.getId().equals(existente.getId())) {
                session.setAttribute("usuario", existente);
            }

            redirectAttrs.addFlashAttribute("mensaje", "Usuario actualizado correctamente");
        } catch (Exception e) {
            redirectAttrs.addFlashAttribute("error", "Error al actualizar usuario: " + e.getMessage());
        }
        return "redirect:/admin/usuarios";
    }

    // Eliminar usuario
    @PostMapping("/eliminar")
    public String eliminarUsuario(@RequestParam Integer idUsuario, HttpSession session,
            RedirectAttributes redirectAttrs) {
        try {
            User usuarioActual = (User) session.getAttribute("usuario");
            if (usuarioActual.getId().equals(idUsuario)) {
                redirectAttrs.addFlashAttribute("error", "No puedes eliminarte a ti mismo");
                return "redirect:/admin/usuarios";
            }
            if (userRepository.existsById(idUsuario)) {
                userRepository.deleteById(idUsuario);
                redirectAttrs.addFlashAttribute("mensaje", "Usuario eliminado correctamente");
            } else {
                redirectAttrs.addFlashAttribute("error", "Usuario no encontrado");
            }
        } catch (Exception e) {
            redirectAttrs.addFlashAttribute("error", "Error al eliminar usuario: " + e.getMessage());
        }
        return "redirect:/admin/usuarios";
    }

    // Cambiar rol (acción rápida)
    @PostMapping("/cambiar-rol")
    public String cambiarRol(@RequestParam Integer idUsuario,
            @RequestParam String nuevoRolNombre,
            HttpSession session,
            RedirectAttributes redirectAttrs) {
        try {
            User usuario = userRepository.findById(idUsuario).orElse(null);
            if (usuario == null) {
                redirectAttrs.addFlashAttribute("error", "Usuario no encontrado");
                return "redirect:/admin/usuarios";
            }

            Rol nuevoRol = rolRepository.findByNombre(nuevoRolNombre)
                    .orElseThrow(() -> new RuntimeException("Rol no encontrado: " + nuevoRolNombre));
            usuario.setRol(nuevoRol);
            userRepository.save(usuario);

            // Actualizar sesión si es el mismo usuario
            User usuarioSesion = (User) session.getAttribute("usuario");
            if (usuarioSesion != null && usuarioSesion.getId().equals(usuario.getId())) {
                session.setAttribute("usuario", usuario);
            }

            redirectAttrs.addFlashAttribute("mensaje", "Rol actualizado a " + nuevoRol.getNombre());
        } catch (Exception e) {
            redirectAttrs.addFlashAttribute("error", "Error al cambiar rol: " + e.getMessage());
        }
        return "redirect:/admin/usuarios";
    }
}