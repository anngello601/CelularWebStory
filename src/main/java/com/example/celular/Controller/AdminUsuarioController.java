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
    private final RolRepository rolRepository;

    public AdminUsuarioController(UserRepository userRepository, RolRepository rolRepository) {
        this.userRepository = userRepository;
        this.rolRepository = rolRepository;
    }

    // Listar usuarios
    @GetMapping
    public String listarUsuarios(HttpSession session, Model model) {
        User usuario = (User) session.getAttribute("usuario");
        if (usuario == null || !(usuario.isAdmin() || usuario.isAsistente())) {
            return "redirect:/login";
        }
        model.addAttribute("usuarios", userRepository.findAll());
        model.addAttribute("roles", rolRepository.findAll());
        model.addAttribute("esAdmin", usuario.isAdmin());
        model.addAttribute("esAsistente", usuario.isAsistente());
        return "adminUsuarios";
    }

    // Guardar nuevo usuario
    @PostMapping("/guardar")
    public String guardarUsuario(@RequestParam String nombre,
                                 @RequestParam String correo,
                                 @RequestParam String rolNombre,
                                 @RequestParam(required = false) String password,
                                 HttpSession session,
                                 RedirectAttributes redirectAttrs) {
        try {
            User usuarioSesion = (User) session.getAttribute("usuario");

            // Validar que el usuario en sesión tenga permisos para crear
            if (usuarioSesion == null) {
                redirectAttrs.addFlashAttribute("error", "Debes iniciar sesión");
                return "redirect:/login";
            }

            // Si es ASISTENTE, no puede crear ADMIN
            if (usuarioSesion.isAsistente() && "ADMIN".equals(rolNombre)) {
                redirectAttrs.addFlashAttribute("error", "No tienes permisos para crear administradores");
                return "redirect:/admin/usuarios";
            }

            // Validar correo duplicado
            if (userRepository.findByCorreo(correo) != null) {
                redirectAttrs.addFlashAttribute("error", "El correo ya está registrado");
                return "redirect:/admin/usuarios";
            }

            // Validar contraseña
            if (password == null || password.trim().isEmpty() || password.length() < 6) {
                redirectAttrs.addFlashAttribute("error", "La contraseña debe tener al menos 6 caracteres");
                return "redirect:/admin/usuarios";
            }

            // Obtener el rol por nombre
            Rol rol = rolRepository.findByNombre(rolNombre)
                    .orElse(rolRepository.findByNombre("USUARIO")
                            .orElseThrow(() -> new RuntimeException("Rol por defecto no encontrado")));

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
            User usuarioSesion = (User) session.getAttribute("usuario");
            if (usuarioSesion == null) {
                redirectAttrs.addFlashAttribute("error", "Debes iniciar sesión");
                return "redirect:/login";
            }

            // Buscar el usuario a editar
            User existente = userRepository.findById(id).orElse(null);
            if (existente == null) {
                redirectAttrs.addFlashAttribute("error", "Usuario no encontrado");
                return "redirect:/admin/usuarios";
            }

            // 🔴 Validación: si el usuario en sesión es ASISTENTE, no puede tocar a ADMIN
            if (usuarioSesion.isAsistente() && existente.isAdmin()) {
                redirectAttrs.addFlashAttribute("error", "No tienes permisos para editar administradores");
                return "redirect:/admin/usuarios";
            }

            // 🔴 Validación: si el usuario en sesión es ASISTENTE, no puede asignar rol ADMIN
            if (usuarioSesion.isAsistente() && "ADMIN".equals(rolNombre)) {
                redirectAttrs.addFlashAttribute("error", "No tienes permisos para asignar el rol ADMIN");
                return "redirect:/admin/usuarios";
            }

            // Actualizar datos
            existente.setNombre(nombre);
            existente.setCorreo(correo);
            if (activo != null) existente.setActivo(activo);

            // Actualizar rol
            Rol rol = rolRepository.findByNombre(rolNombre)
                    .orElseThrow(() -> new RuntimeException("Rol no encontrado: " + rolNombre));
            existente.setRol(rol);

            // Actualizar contraseña solo si se envía una nueva
            if (password != null && !password.trim().isEmpty()) {
                if (password.length() < 6) {
                    redirectAttrs.addFlashAttribute("error", "La contraseña debe tener al menos 6 caracteres");
                    return "redirect:/admin/usuarios";
                }
                existente.setPassword(password);
            }

            userRepository.save(existente);

            // Si el usuario editado es el mismo que está en sesión, actualizar sesión
            if (usuarioSesion.getId().equals(existente.getId())) {
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
    public String eliminarUsuario(@RequestParam Integer idUsuario,
                                  HttpSession session,
                                  RedirectAttributes redirectAttrs) {
        try {
            User usuarioSesion = (User) session.getAttribute("usuario");
            if (usuarioSesion == null) {
                redirectAttrs.addFlashAttribute("error", "Debes iniciar sesión");
                return "redirect:/login";
            }

            // No puedes eliminarte a ti mismo
            if (usuarioSesion.getId().equals(idUsuario)) {
                redirectAttrs.addFlashAttribute("error", "No puedes eliminarte a ti mismo");
                return "redirect:/admin/usuarios";
            }

            // Buscar el usuario a eliminar
            User usuarioEliminar = userRepository.findById(idUsuario).orElse(null);
            if (usuarioEliminar == null) {
                redirectAttrs.addFlashAttribute("error", "Usuario no encontrado");
                return "redirect:/admin/usuarios";
            }

            // Si el usuario en sesión es ASISTENTE, no puede eliminar ADMIN
            if (usuarioSesion.isAsistente() && usuarioEliminar.isAdmin()) {
                redirectAttrs.addFlashAttribute("error", "No tienes permisos para eliminar administradores");
                return "redirect:/admin/usuarios";
            }

            // Si el usuario en sesión no es ADMIN, no puede eliminar ADMIN (redundante pero seguro)
            if (!usuarioSesion.isAdmin() && usuarioEliminar.isAdmin()) {
                redirectAttrs.addFlashAttribute("error", "No tienes permiso para eliminar un administrador");
                return "redirect:/admin/usuarios";
            }

            userRepository.deleteById(idUsuario);
            redirectAttrs.addFlashAttribute("mensaje", "Usuario eliminado correctamente");
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
            User usuarioSesion = (User) session.getAttribute("usuario");
            if (usuarioSesion == null) {
                redirectAttrs.addFlashAttribute("error", "Debes iniciar sesión");
                return "redirect:/login";
            }

            User usuario = userRepository.findById(idUsuario).orElse(null);
            if (usuario == null) {
                redirectAttrs.addFlashAttribute("error", "Usuario no encontrado");
                return "redirect:/admin/usuarios";
            }

            // Si el usuario en sesión es ASISTENTE, no puede cambiar rol de ADMIN
            if (usuarioSesion.isAsistente() && usuario.isAdmin()) {
                redirectAttrs.addFlashAttribute("error", "No tienes permisos para cambiar el rol de administradores");
                return "redirect:/admin/usuarios";
            }

            // Si el usuario en sesión es ASISTENTE, no puede asignar ADMIN
            if (usuarioSesion.isAsistente() && "ADMIN".equals(nuevoRolNombre)) {
                redirectAttrs.addFlashAttribute("error", "No tienes permisos para asignar el rol ADMIN");
                return "redirect:/admin/usuarios";
            }

            // Si el usuario en sesión no es ADMIN, no puede asignar ADMIN (redundante)
            if (!usuarioSesion.isAdmin() && "ADMIN".equals(nuevoRolNombre)) {
                redirectAttrs.addFlashAttribute("error", "No tienes permiso para asignar el rol ADMIN");
                return "redirect:/admin/usuarios";
            }

            Rol nuevoRol = rolRepository.findByNombre(nuevoRolNombre)
                    .orElseThrow(() -> new RuntimeException("Rol no encontrado: " + nuevoRolNombre));
            usuario.setRol(nuevoRol);
            userRepository.save(usuario);

            // Actualizar sesión si es el mismo usuario
            if (usuarioSesion.getId().equals(usuario.getId())) {
                session.setAttribute("usuario", usuario);
            }

            redirectAttrs.addFlashAttribute("mensaje", "Rol actualizado a " + nuevoRol.getNombre());
        } catch (Exception e) {
            redirectAttrs.addFlashAttribute("error", "Error al cambiar rol: " + e.getMessage());
        }
        return "redirect:/admin/usuarios";
    }
}