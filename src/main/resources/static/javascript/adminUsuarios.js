/**
 * adminUsuarios.js - Gestión de usuarios (NEXO)
 */

// ==========================================
// FILTROS
// ==========================================
function filtrarUsuarios() {
    const busqueda = document.getElementById('buscarUsuario').value.toLowerCase().trim();
    const rolFiltro = document.getElementById('filtroRol').value;
    const estadoFiltro = document.getElementById('filtroEstado').value;
    const filas = document.querySelectorAll('#cuerpoTabla tr');

    let filasVisibles = 0;

    filas.forEach(fila => {
        const nombre = fila.cells[1]?.textContent?.toLowerCase().trim() || '';
        const correo = fila.cells[2]?.textContent?.toLowerCase().trim() || '';
        const rol = fila.cells[3]?.textContent?.trim() || '';
        const estado = fila.cells[4]?.textContent?.trim() || '';

        const coincideBusqueda = busqueda === '' || nombre.includes(busqueda) || correo.includes(busqueda);
        const coincideRol = rolFiltro === '' || rol === rolFiltro;
        const coincideEstado = estadoFiltro === '' || estado === estadoFiltro;

        const mostrar = coincideBusqueda && coincideRol && coincideEstado;
        fila.style.display = mostrar ? '' : 'none';
        if (mostrar) filasVisibles++;
    });

    console.log(`✅ Filtros aplicados: ${filasVisibles} filas visibles`);

    if (typeof reiniciarPaginacion === 'function') {
        reiniciarPaginacion('tablaUsuarios', 10);
    } else {
        delete paginacionEstado['tablaUsuarios'];
        if (typeof inicializarPaginacion === 'function') {
            setTimeout(() => inicializarPaginacion('tablaUsuarios', 10), 100);
        }
    }
}

// ==========================================
// MODAL AGREGAR
// ==========================================
function abrirModalAgregar() {
    console.log('✅ abrirModalAgregar ejecutado');
    try {
        document.getElementById('modalUsuarioTitulo').innerText = 'Agregar Usuario';
        document.getElementById('formUsuario').action = '/admin/usuarios/guardar';
        document.getElementById('editId').value = '';
        document.getElementById('editNombre').value = '';
        document.getElementById('editCorreo').value = '';
        // 🔴 CORREGIDO: usar editRolId (no editRol)
        document.getElementById('editRolId').value = 'USUARIO';
        document.getElementById('editActivo').value = 'true';
        document.getElementById('editPassword').value = '';
        document.getElementById('editPassword').required = true;
        document.getElementById('divPassword').style.display = 'block';

        const modal = new bootstrap.Modal(document.getElementById('modalUsuario'));
        modal.show();
    } catch (e) {
        console.error('Error en abrirModalAgregar:', e);
        alert('Error al abrir el modal de agregar: ' + e.message);
    }
}

// ==========================================
// MODAL EDITAR
// ==========================================
function editarUsuario(boton) {
    console.log('✅ editarUsuario ejecutado, id:', boton.getAttribute('data-id'));
    try {
        document.getElementById('modalUsuarioTitulo').innerText = 'Editar Usuario';
        document.getElementById('formUsuario').action = '/admin/usuarios/editar';
        document.getElementById('editId').value = boton.getAttribute('data-id');
        document.getElementById('editNombre').value = boton.getAttribute('data-nombre') || '';
        document.getElementById('editCorreo').value = boton.getAttribute('data-correo') || '';
        // 🔴 CORREGIDO: usar data-rol-id y editRolId
        const rolId = boton.getAttribute('data-rol-id');
        document.getElementById('editRolId').value = rolId || 'USUARIO';
        document.getElementById('editActivo').value = boton.getAttribute('data-activo') === 'true' ? 'true' : 'false';
        document.getElementById('editPassword').value = '';
        document.getElementById('editPassword').required = false;
        document.getElementById('divPassword').style.display = 'block';

        const modal = new bootstrap.Modal(document.getElementById('modalUsuario'));
        modal.show();
    } catch (e) {
        console.error('Error en editarUsuario:', e);
        alert('Error al abrir el modal de edición: ' + e.message);
    }
}

// ==========================================
// ELIMINAR
// ==========================================
function eliminarUsuario(boton) {
    console.log('✅ eliminarUsuario ejecutado, id:', boton.getAttribute('data-id'));
    try {
        const id = boton.getAttribute('data-id');
        document.getElementById('eliminarId').value = id;
        const modal = new bootstrap.Modal(document.getElementById('modalEliminar'));
        modal.show();
    } catch (e) {
        console.error('Error en eliminarUsuario:', e);
        alert('Error al abrir el modal de eliminación: ' + e.message);
    }
}

// ==========================================
// CAMBIAR ROL (acción rápida)
// ==========================================
function cambiarRol(elemento, nuevoRol) {
    const row = elemento.closest('tr');
    const id = row?.querySelector('[data-id]')?.getAttribute('data-id');
    if (!id) return;

    if (confirm('¿Cambiar rol del usuario a ' + nuevoRol + '?')) {
        document.getElementById('cambiarRolId').value = id;
        document.getElementById('cambiarRolNuevo').value = nuevoRol;
        document.getElementById('formCambiarRol').submit();
    }
}

// ==========================================
// INICIALIZAR PAGINACIÓN
// ==========================================
document.addEventListener('DOMContentLoaded', function () {
    console.log('✅ DOMContentLoaded - adminUsuarios');
    if (typeof inicializarPaginacion === 'function') {
        inicializarPaginacion('tablaUsuarios', 10);
    } else {
        console.warn('⚠️ inicializarPaginacion no está definida');
    }
});