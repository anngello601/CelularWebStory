/* ================================================
   NEXO - Chatbot JavaScript
   ================================================ */

document.addEventListener("DOMContentLoaded", function () {

    const toggleBtn = document.getElementById("chat-toggle");
    const chatBox = document.getElementById("chat-box");
    const closeBtn = document.getElementById("chat-close");
    const sendBtn = document.getElementById("send-btn");
    const userInput = document.getElementById("user-input");
    const messages = document.getElementById("chat-messages");

    // ================================================
    // ABRIR / CERRAR CHAT
    // ================================================
    toggleBtn.addEventListener("click", function () {
        const isOpen = chatBox.style.display === "flex";
        chatBox.style.display = isOpen ? "none" : "flex";
    });

    closeBtn.addEventListener("click", function () {
        chatBox.style.display = "none";
    });

    // ================================================
    // SALUDO AUTOMÁTICO AL CARGAR
    // ================================================
    setTimeout(() => {
        addMessage(
            "👋 ¡Hola! Bienvenido a <b>NEXO</b>.<br><br>" +
            "Soy tu asistente virtual 🤖<br>" +
            "¿En qué puedo ayudarte hoy?",
            "bot"
        );
        mostrarBotonesRapidos([
            "📱 Celulares disponibles",
            "💰 Ver precios",
            "🚚 Envíos",
            "✅ Garantía",
            "🕐 Horario"
        ]);
    }, 800);

    // ================================================
    // AGREGAR MENSAJE
    // ================================================
    function addMessage(message, type) {
        const div = document.createElement("div");
        div.className = type === "user" ? "user-message" : "bot-message";
        div.innerHTML = message;
        messages.appendChild(div);
        messages.scrollTop = messages.scrollHeight;
    }

    // ================================================
    // INDICADOR DE ESCRITURA
    // ================================================
    function mostrarTyping() {
        const div = document.createElement("div");
        div.className = "typing-indicator";
        div.id = "typing";
        div.innerHTML = "<span></span><span></span><span></span>";
        messages.appendChild(div);
        messages.scrollTop = messages.scrollHeight;
    }

    function quitarTyping() {
        const t = document.getElementById("typing");
        if (t) t.remove();
    }

    // ================================================
    // BOTONES RÁPIDOS
    // ================================================
    function mostrarBotonesRapidos(opciones) {
        const contenedor = document.createElement("div");
        contenedor.className = "quick-replies";
        opciones.forEach(op => {
            const btn = document.createElement("button");
            btn.className = "quick-btn";
            btn.innerHTML = op;
            btn.addEventListener("click", function () {
                contenedor.remove();
                addMessage(op, "user");
                procesarRespuesta(op.toLowerCase());
            });
            contenedor.appendChild(btn);
        });
        messages.appendChild(contenedor);
        messages.scrollTop = messages.scrollHeight;
    }

    // ================================================
    // LÓGICA DE RESPUESTAS
    // ================================================
    function responderBot(text) {

        // SALUDOS
        if (/hola|buenas|buenos|hi|hey|saludos/.test(text)) {
            return {
                msg: "👋 ¡Hola! ¿En qué te puedo ayudar hoy?",
                btns: ["📱 Celulares disponibles", "💰 Ver precios", "🚚 Envíos", "✅ Garantía"]
            };
        }

        // CELULARES DISPONIBLES
        if (/celular|disponible|stock|tienes|tienen|catalogo|catálogo/.test(text)) {
            return {
                msg: "📱 <b>Celulares disponibles en NEXO:</b><br><br>" +
                    "🍎 <b>Apple</b> — iPhone 15, 15 Pro, 15 Pro Max<br>" +
                    "📲 <b>Samsung</b> — Galaxy S24, S24+, A55<br>" +
                    "📱 <b>Xiaomi</b> — Xiaomi 14, Redmi Note 13<br>" +
                    "⚡ <b>Motorola</b> — Edge 50, Moto G85<br>" +
                    "🏅 <b>Honor</b> — Honor 90, Magic6 Lite<br><br>" +
                    "¿Deseas info de alguna marca en particular?",
                btns: ["🍎 iPhone", "📲 Samsung", "📱 Xiaomi", "⚡ Motorola"]
            };
        }

        // IPHONE / APPLE
        if (/iphone|apple/.test(text)) {
            return {
                msg: "🍎 <b>iPhones disponibles:</b><br><br>" +
                    "📱 <b>iPhone 15</b> — 128GB · S/3,299<br>" +
                    "📱 <b>iPhone 15 Pro</b> — 256GB · S/4,599<br>" +
                    "📱 <b>iPhone 15 Pro Max</b> — 512GB · S/5,999<br><br>" +
                    "✅ Garantía de 12 meses<br>" +
                    "🚚 Envío a domicilio disponible",
                btns: ["💰 Ver precios", "✅ Garantía", "🚚 Envíos"]
            };
        }

        // SAMSUNG
        if (/samsung|galaxy/.test(text)) {
            return {
                msg: "📲 <b>Samsung disponibles:</b><br><br>" +
                    "📱 <b>Galaxy S24</b> — 256GB · S/3,199<br>" +
                    "📱 <b>Galaxy S24+</b> — 512GB · S/3,999<br>" +
                    "📱 <b>Galaxy A55</b> — 256GB · S/1,799<br><br>" +
                    "✅ Garantía de 12 meses<br>" +
                    "🚚 Envío a domicilio disponible",
                btns: ["💰 Ver precios", "✅ Garantía", "🚚 Envíos"]
            };
        }

        // XIAOMI / REDMI
        if (/xiaomi|redmi|poco/.test(text)) {
            return {
                msg: "📱 <b>Xiaomi disponibles:</b><br><br>" +
                    "📱 <b>Xiaomi 14</b> — 256GB · S/2,499<br>" +
                    "📱 <b>Redmi Note 13 Pro</b> — 256GB · S/1,299<br>" +
                    "📱 <b>Poco X6 Pro</b> — 256GB · S/1,499<br><br>" +
                    "✅ Garantía de 12 meses<br>" +
                    "🚚 Envío a domicilio disponible",
                btns: ["💰 Ver precios", "✅ Garantía", "🚚 Envíos"]
            };
        }

        // MOTOROLA
        if (/motorola|moto/.test(text)) {
            return {
                msg: "⚡ <b>Motorola disponibles:</b><br><br>" +
                    "📱 <b>Moto Edge 50</b> — 256GB · S/1,599<br>" +
                    "📱 <b>Moto G85</b> — 256GB · S/999<br><br>" +
                    "✅ Garantía de 12 meses<br>" +
                    "🚚 Envío a domicilio disponible",
                btns: ["💰 Ver precios", "✅ Garantía", "🚚 Envíos"]
            };
        }

        // HONOR
        if (/honor/.test(text)) {
            return {
                msg: "🏅 <b>Honor disponibles:</b><br><br>" +
                    "📱 <b>Honor 90</b> — 256GB · S/1,399<br>" +
                    "📱 <b>Honor Magic6 Lite</b> — 256GB · S/1,099<br><br>" +
                    "✅ Garantía de 12 meses<br>" +
                    "🚚 Envío a domicilio disponible",
                btns: ["💰 Ver precios", "✅ Garantía", "🚚 Envíos"]
            };
        }

        // PRECIOS
        if (/precio|precios|cuanto|cuánto|cuesta|vale|costa/.test(text)) {
            return {
                msg: "💰 <b>Rango de precios en NEXO:</b><br><br>" +
                    "🍎 Apple → S/3,299 – S/5,999<br>" +
                    "📲 Samsung → S/1,799 – S/3,999<br>" +
                    "📱 Xiaomi → S/1,299 – S/2,499<br>" +
                    "⚡ Motorola → S/999 – S/1,599<br>" +
                    "🏅 Honor → S/1,099 – S/1,399<br><br>" +
                    "¿Deseas ver los productos completos?",
                btns: ["📱 Ver todos los productos", "📲 Samsung", "🍎 iPhone"]
            };
        }

        // MÁS BARATO / ECONÓMICO
        if (/barato|economico|económico|bajo precio|menor precio/.test(text)) {
            return {
                msg: "💸 El más económico que tenemos es el <b>Moto G85</b> por <b>S/999</b>.<br><br>" +
                    "También tenemos opciones desde S/1,099 con Honor Magic6 Lite.",
                btns: ["⚡ Ver Motorola", "🏅 Ver Honor", "💰 Ver precios"]
            };
        }

        // MEJOR CELULAR / COMPARAR
        if (/mejor|comparar|comparación|recomien|recomienda/.test(text)) {
            return {
                msg: "⚖️ <b>¿Para qué lo usarías?</b><br><br>" +
                    "📸 <b>Fotos y video:</b> iPhone 15 Pro<br>" +
                    "🎮 <b>Gaming:</b> Samsung Galaxy S24+ o Poco X6 Pro<br>" +
                    "💼 <b>Trabajo diario:</b> Samsung Galaxy A55<br>" +
                    "💰 <b>Mejor precio:</b> Redmi Note 13 Pro<br><br>" +
                    "¿Cuál te interesa más?",
                btns: ["📸 iPhone 15 Pro", "🎮 Samsung S24+", "💰 Redmi Note 13"]
            };
        }

        // GARANTÍA
        if (/garantia|garantía|falla|daño|defecto/.test(text)) {
            return {
                msg: "✅ <b>Garantía NEXO:</b><br><br>" +
                    "• 12 meses de garantía por fallas de fábrica<br>" +
                    "• Cubre defectos de hardware y software<br>" +
                    "• No cubre daños por caída o agua<br><br>" +
                    "📞 Para hacer válida tu garantía contáctanos directamente.",
                btns: ["📞 Contacto", "🚚 Envíos", "💰 Ver precios"]
            };
        }

        // ENVÍOS
        if (/envio|envío|envíos|delivery|despacho|domicilio/.test(text)) {
            return {
                msg: "🚚 <b>Envíos NEXO:</b><br><br>" +
                    "• Envíos a todo Lima Metropolitana<br>" +
                    "• Tiempo de entrega: 1 a 3 días hábiles<br>" +
                    "• Envío gratis en compras mayores a S/1,500<br>" +
                    "• Costo de envío estándar: S/15<br><br>" +
                    "📦 También puedes recoger en tienda sin costo.",
                btns: ["📞 Contacto", "✅ Garantía", "💰 Ver precios"]
            };
        }

        // PAGO
        if (/pago|pagar|metodo|método|tarjeta|yape|plin|efectivo|transferencia/.test(text)) {
            return {
                msg: "💳 <b>Métodos de pago aceptados:</b><br><br>" +
                    "💵 Efectivo<br>" +
                    "💳 Tarjeta de crédito / débito (Visa, Mastercard)<br>" +
                    "📲 Yape<br>" +
                    "📲 Plin<br>" +
                    "🏦 Transferencia bancaria (BCP, Interbank)",
                btns: ["🚚 Envíos", "✅ Garantía", "🕐 Horario"]
            };
        }

        // HORARIO
        if (/horario|hora|atienden|atención|atencion|abierto/.test(text)) {
            return {
                msg: "🕐 <b>Horario de atención NEXO:</b><br><br>" +
                    "📅 Lunes a Viernes: 9:00 am – 7:00 pm<br>" +
                    "📅 Sábados: 9:00 am – 5:00 pm<br>" +
                    "❌ Domingos y feriados: Cerrado<br><br>" +
                    "📲 También puedes escribirnos por WhatsApp.",
                btns: ["📞 Contacto", "🚚 Envíos", "💰 Ver precios"]
            };
        }

        // CONTACTO
        if (/contacto|contactar|whatsapp|telefono|teléfono|llamar|escribir/.test(text)) {
            return {
                msg: "📞 <b>Contáctanos:</b><br><br>" +
                    "📲 WhatsApp: +51 987 654 321<br>" +
                    "📧 Email: contacto@nexo.pe<br>" +
                    "📍 Lima, Perú<br><br>" +
                    "⏰ Atención: Lun–Vie 9am–7pm | Sáb 9am–5pm",
                btns: ["🕐 Horario", "🚚 Envíos", "✅ Garantía"]
            };
        }

        // PROMOCIONES / DESCUENTOS
        if (/promoci|descuento|oferta|sale|rebaja/.test(text)) {
            return {
                msg: "🎉 <b>Promociones NEXO:</b><br><br>" +
                    "• Equipos seleccionados con <b>hasta 20% de descuento</b><br>" +
                    "• Envío gratis en compras mayores a S/1,500<br>" +
                    "• Paga en cuotas sin interés con tarjeta<br><br>" +
                    "Revisa nuestra sección de productos para ver las ofertas activas.",
                btns: ["📱 Ver productos", "💰 Ver precios", "💳 Métodos de pago"]
            };
        }

        // GRACIAS / DESPEDIDA
        if (/gracias|ok|listo|adios|adiós|chau|bye/.test(text)) {
            return {
                msg: "😊 ¡Fue un gusto ayudarte!<br>" +
                    "Gracias por visitar <b>NEXO</b> 📱<br>" +
                    "¡Que tengas un excelente día! 👋",
                btns: []
            };
        }

        // VER PRODUCTOS (botón rápido)
        if (/ver todos|ver producto/.test(text)) {
            window.location.href = "/productos";
            return null;
        }

        // RESPUESTA POR DEFECTO
        return {
            msg: "🤖 No entendí bien tu consulta.<br><br>" +
                "Puedes preguntarme sobre:",
            btns: ["📱 Celulares disponibles", "💰 Ver precios", "🚚 Envíos", "✅ Garantía", "🕐 Horario"]
        };
    }

    // ================================================
    // PROCESAR Y MOSTRAR RESPUESTA
    // ================================================
    function procesarRespuesta(text) {
        mostrarTyping();
        setTimeout(function () {
            quitarTyping();
            const resultado = responderBot(text);
            if (!resultado) return;
            addMessage(resultado.msg, "bot");
            if (resultado.btns && resultado.btns.length > 0) {
                mostrarBotonesRapidos(resultado.btns);
            }
        }, 700);
    }

    // ================================================
    // ENVIAR MENSAJE
    // ================================================
    function sendMessage() {
        const text = userInput.value.trim();
        if (text === "") return;
        addMessage(text, "user");
        userInput.value = "";
        procesarRespuesta(text.toLowerCase());
    }

    sendBtn.addEventListener("click", sendMessage);
    userInput.addEventListener("keypress", function (e) {
        if (e.key === "Enter") sendMessage();
    });

});