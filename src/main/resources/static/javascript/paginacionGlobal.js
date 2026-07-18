/**
 * paginacionGlobal.js - Paginación reutilizable
 */
const paginacionEstado = {};

function inicializarPaginacion(idTabla, filasPorPagina = 10) {
    const tabla = document.getElementById(idTabla);
    if (!tabla) return;
    const tbody = tabla.querySelector('tbody');
    if (!tbody) return;
    // 🔴 Solo filas visibles (display !== 'none')
    const filas = Array.from(tbody.querySelectorAll('tr')).filter(fila => fila.style.display !== 'none');
    const total = filas.length;
    if (total === 0) {
        document.getElementById('pagInfo').textContent = 'No hay registros';
        document.getElementById('pagBtns').innerHTML = '';
        return;
    }
    paginacionEstado[idTabla] = { filas, total, porPagina: filasPorPagina, pagina: 1, totalPaginas: Math.ceil(total / filasPorPagina) };
    mostrarPagina(idTabla, 1);
}

function mostrarPagina(idTabla, pagina) {
    const estado = paginacionEstado[idTabla];
    if (!estado) return;
    const { filas, total, porPagina, totalPaginas } = estado;
    if (pagina < 1) pagina = 1;
    if (pagina > totalPaginas) pagina = totalPaginas;
    estado.pagina = pagina;
    const inicio = (pagina - 1) * porPagina;
    const fin = Math.min(inicio + porPagina, total);
    // Ocultar todas las filas primero
    filas.forEach(f => f.style.display = 'none');
    // Mostrar solo las filas de la página actual
    for (let i = inicio; i < fin; i++) {
        if (filas[i]) filas[i].style.display = '';
    }
    document.getElementById('pagInfo').textContent = `Mostrando ${inicio + 1} - ${fin} de ${total} registros`;
    generarBotones(idTabla, pagina, totalPaginas);
}

function generarBotones(idTabla, pagina, total) {
    const contenedor = document.getElementById('pagBtns');
    if (!contenedor) return;
    let html = `<button class="btn-pag" onclick="cambiarPagina('${idTabla}', ${pagina - 1})" ${pagina <= 1 ? 'disabled' : ''}>&laquo;</button>`;
    const inicio = Math.max(1, pagina - 2);
    const fin = Math.min(total, pagina + 2);
    if (inicio > 1) html += `<button class="btn-pag" onclick="cambiarPagina('${idTabla}', 1)">1</button>`;
    if (inicio > 2) html += `<span class="btn-pag disabled">…</span>`;
    for (let i = inicio; i <= fin; i++) {
        html += `<button class="btn-pag ${i === pagina ? 'active' : ''}" onclick="cambiarPagina('${idTabla}', ${i})">${i}</button>`;
    }
    if (fin < total - 1) html += `<span class="btn-pag disabled">…</span>`;
    if (fin < total) html += `<button class="btn-pag" onclick="cambiarPagina('${idTabla}', ${total})">${total}</button>`;
    html += `<button class="btn-pag" onclick="cambiarPagina('${idTabla}', ${pagina + 1})" ${pagina >= total ? 'disabled' : ''}>&raquo;</button>`;
    contenedor.innerHTML = html;
}

function cambiarPagina(idTabla, pagina) {
    mostrarPagina(idTabla, pagina);
}

function reiniciarPaginacion(idTabla, filasPorPagina = 10) {
    delete paginacionEstado[idTabla];
    setTimeout(() => {
        inicializarPaginacion(idTabla, filasPorPagina);
    }, 50);
}