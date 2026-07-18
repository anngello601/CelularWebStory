document.addEventListener("DOMContentLoaded", function () {

    const buscador = document.getElementById('buscador');
    const resultados = document.getElementById('resultadosBusqueda');

    if (!buscador || !resultados) return;

    buscador.addEventListener('input', function () {

        const texto = this.value.trim();

        resultados.innerHTML = '';
        resultados.style.display = 'none';

        if (texto.length < 2) return;

        fetch('/api/productos/buscar?q=' + encodeURIComponent(texto))
            .then(r => {
                if (!r.ok) throw new Error('Error en el servidor');
                return r.json();
            })
            .then(productos => {
                // 🔴 Validar que sea un arreglo
                if (!Array.isArray(productos)) {
                    throw new Error('Respuesta no válida');
                }

                productos.forEach(p => {

                    resultados.innerHTML += `
                        <a href="/producto/${p.id}"
                           class="list-group-item list-group-item-action">

                            <div class="d-flex align-items-center">

                                <img src="/imagenes/${p.imagen}"
                                     width="50"
                                     height="50"
                                     class="me-2">

                                <div>
                                    <div>${p.nombre}</div>
                                    <small>S/ ${p.precio}</small>
                                </div>

                            </div>

                        </a>`;
                });

                resultados.style.display = 'block';
            });
    });

    document.addEventListener('click', function (e) {

        if (!buscador.contains(e.target) &&
            !resultados.contains(e.target)) {

            resultados.style.display = 'none';
        }
    });

});