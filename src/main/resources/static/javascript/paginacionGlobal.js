/**
 * paginacionGlobal.js - Universal (tablas y grids)
 */
const paginacionEstado = {};

function inicializarPaginacion(contenedorId, elementosPorPagina = 10) {
    const contenedor = document.getElementById(contenedorId);
    if (!contenedor) {
        console.warn(`⚠️ Contenedor "${contenedorId}" no encontrado`);
        return;
    }

    // 🔴 DETECTAR si es tabla o grid
    let elementos;
    const tabla = contenedor.closest('table') || contenedor.querySelector('table');
    if (tabla) {
        const tbody = tabla.querySelector('tbody');
        if (!tbody) {
            console.warn('⚠️ No se encontró <tbody>');
            return;
        }
        elementos = Array.from(tbody.querySelectorAll('tr'))
            .filter(fila => fila.style.display !== 'none');
    } else {
        // Grid: usar .product-item o todos los hijos directos visibles
        elementos = Array.from(contenedor.children)
            .filter(el => el.style.display !== 'none' && el.tagName !== 'SCRIPT');
    }

    const total = elementos.length;
    if (total === 0) {
        document.getElementById('pagInfo').textContent = 'No hay registros';
        document.getElementById('pagBtns').innerHTML = '';
        return;
    }

    paginacionEstado[contenedorId] = {
        elementos: elementos,
        total: total,
        porPagina: elementosPorPagina,
        pagina: 1,
        totalPaginas: Math.ceil(total / elementosPorPagina)
    };
    mostrarPagina(contenedorId, 1);
}

function mostrarPagina(contenedorId, pagina) {
    const estado = paginacionEstado[contenedorId];
    if (!estado) return;
    const { elementos, total, porPagina, totalPaginas } = estado;
    if (pagina < 1) pagina = 1;
    if (pagina > totalPaginas) pagina = totalPaginas;
    estado.pagina = pagina;

    const inicio = (pagina - 1) * porPagina;
    const fin = Math.min(inicio + porPagina, total);

    elementos.forEach(el => el.style.display = 'none');
    for (let i = inicio; i < fin; i++) {
        if (elementos[i]) elementos[i].style.display = '';
    }

    document.getElementById('pagInfo').textContent =
        `Mostrando ${inicio + 1} - ${fin} de ${total} registros`;
    generarBotones(contenedorId, pagina, totalPaginas);
}

function generarBotones(contenedorId, pagina, total) {
    const contenedor = document.getElementById('pagBtns');
    if (!contenedor) return;
    let html = `<button class="btn-pag" onclick="cambiarPagina('${contenedorId}', ${pagina - 1})" ${pagina <= 1 ? 'disabled' : ''}>&laquo;</button>`;
    const inicio = Math.max(1, pagina - 2);
    const fin = Math.min(total, pagina + 2);
    if (inicio > 1) html += `<button class="btn-pag" onclick="cambiarPagina('${contenedorId}', 1)">1</button>`;
    if (inicio > 2) html += `<span class="btn-pag disabled">…</span>`;
    for (let i = inicio; i <= fin; i++) {
        html += `<button class="btn-pag ${i === pagina ? 'active' : ''}" onclick="cambiarPagina('${contenedorId}', ${i})">${i}</button>`;
    }
    if (fin < total - 1) html += `<span class="btn-pag disabled">…</span>`;
    if (fin < total) html += `<button class="btn-pag" onclick="cambiarPagina('${contenedorId}', ${total})">${total}</button>`;
    html += `<button class="btn-pag" onclick="cambiarPagina('${contenedorId}', ${pagina + 1})" ${pagina >= total ? 'disabled' : ''}>&raquo;</button>`;
    contenedor.innerHTML = html;
}

function cambiarPagina(contenedorId, pagina) {
    mostrarPagina(contenedorId, pagina);
}

function reiniciarPaginacion(contenedorId, elementosPorPagina = 10) {
    delete paginacionEstado[contenedorId];
    setTimeout(() => {
        inicializarPaginacion(contenedorId, elementosPorPagina);
    }, 100);
}