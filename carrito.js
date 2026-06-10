function agregarAlCarrito(btn) {
    if (btn.dataset.logueado === 'false') {
        _mostrarAlertaLogin();
        return;
    }
    _procesarAgregar(btn, 1);
}

function agregarAlCarritoConCantidad(btn) {
    if (btn.dataset.logueado === 'false') {
        _mostrarAlertaLogin();
        return;
    }
    const input    = document.getElementById("cantidad");
    let cantidad   = parseInt(input.value) || 1;
    const maxStock = parseInt(input.max);

    if (!isNaN(maxStock) && cantidad > maxStock) {
        cantidad    = maxStock;
        input.value = maxStock;
    }
    if (cantidad < 1) {
        cantidad    = 1;
        input.value = 1;
    }

    _procesarAgregar(btn, cantidad);
}

function _procesarAgregar(btn, cantidad) {
    const productoId = btn.dataset.id;
    const nombre     = btn.dataset.nombre;
    const precio     = parseFloat(btn.dataset.precio);
    const imagen     = btn.dataset.imagen;

    const params = new URLSearchParams({ productoId, cantidad });

    fetch('/api/carrito/agregar', {
        method: 'POST',
        headers: { 'Content-Type': 'application/x-www-form-urlencoded' },
        body: params
    })
    .then(res => res.json())
    .then(data => {
        if (data.ok) {
            _guardarEnLocalStorage(productoId, nombre, precio, imagen, cantidad);
            _mostrarToast(nombre);
            _actualizarStockUI(btn, data.nuevoStock);
        } else {
            _mostrarError(data.mensaje || "Error al agregar al carrito");
            if (data.stockActual !== undefined) {
                _actualizarStockUI(btn, data.stockActual);
            }
        }
    })
    .catch(() => _mostrarError("Error de conexión con el servidor"));
}

function _guardarEnLocalStorage(id, nombre, precio, imagen, cantidad) {
    let carrito = JSON.parse(localStorage.getItem("carrito")) || [];
    const existente = carrito.find(p => p.id == id);
    if (existente) {
        existente.cantidad += cantidad;
    } else {
        carrito.push({ id, nombre, precio, imagen, cantidad });
    }
    localStorage.setItem("carrito", JSON.stringify(carrito));
}

function _actualizarStockUI(btn, nuevoStock) {
    btn.dataset.stock = nuevoStock;

    // Actualiza el texto "Stock disponible: X" en detalle.html
    const stockDisplay = document.getElementById("stockDisplay");
    if (stockDisplay) stockDisplay.textContent = nuevoStock;

    // Actualiza el max del input de cantidad en detalle.html
    const inputCantidad = document.getElementById("cantidad");
    if (inputCantidad) {
        inputCantidad.max = nuevoStock;
        if (parseInt(inputCantidad.value) > nuevoStock) {
            inputCantidad.value = nuevoStock;
        }
    }

    // Deshabilita botón y controles si stock llegó a 0
    if (nuevoStock <= 0) {
        btn.disabled = true;
        if (inputCantidad) inputCantidad.disabled = true;

        // Cambia el texto del botón en detalle.html
        const spanTexto = btn.querySelector('span');
        if (spanTexto) spanTexto.textContent = 'Sin stock';
    }
}

function _mostrarToast(nombre) {
    _crearToast(
        `<i class="fas fa-check-circle me-2"></i><strong>${nombre}</strong> agregado al carrito`,
        "bg-success text-white"
    );
}

function _mostrarError(mensaje) {
    _crearToast(
        `<i class="fas fa-exclamation-circle me-2"></i>${mensaje}`,
        "bg-danger text-white"
    );
}

function _mostrarAlertaLogin() {
    _crearToast(
        `<i class="fas fa-lock me-2"></i>Debes <a href="/login" class="fw-bold text-dark">iniciar sesión</a> para agregar al carrito`,
        "bg-warning"
    );
}

function _crearToast(html, clases) {
    const toastEl = document.createElement("div");
    toastEl.className = `toast align-items-center border-0 position-fixed bottom-0 end-0 m-3 ${clases}`;
    toastEl.setAttribute("role", "alert");
    toastEl.style.zIndex = "9999";
    toastEl.innerHTML = `
        <div class="d-flex">
            <div class="toast-body">${html}</div>
            <button type="button" class="btn-close btn-close-white me-2 m-auto" data-bs-dismiss="toast"></button>
        </div>`;
    document.body.appendChild(toastEl);
    new bootstrap.Toast(toastEl, { delay: 3000 }).show();
    toastEl.addEventListener("hidden.bs.toast", () => toastEl.remove());
}
