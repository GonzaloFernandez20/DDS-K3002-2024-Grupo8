async function guardarCambios() {
    const nuevaRazonSocial = document.getElementById("nuevaRazonSocial");
    const nuevoTipoOrg = document.getElementById("nuevoTipoOrg");
    const nuevoRubro = document.getElementById("nuevoRubro");
    const nuevaCalleJuridico = document.getElementById("nuevaCalleJuridico");
    const nuevaAlturaJuridico = document.getElementById("nuevaAlturaJuridico");
    const nuevoNombreJuridico = document.getElementById("nuevoNombreJuridico");
    const nuevaContraseniaJuridico = document.getElementById("nuevaContraseniaJuridico");
    /*
    const tipoMedioNuevo = document.getElementById("tipoNuevoMedioDeContactoHumano");
    const medioNuevo = document.getElementById("nuevoMedioDeContactoHumano");
    */

    const datosDeUsuario = {
        usuario: nuevoNombreJuridico.value,
        contrasenia: nuevaContraseniaJuridico.value,
        razonSocial: nuevaRazonSocial.value,
        tipoDeOrganizacion: nuevoTipoOrg.value,
        rubro: nuevoRubro.value,
        calle: nuevaCalleJuridico.value,
        altura: nuevaAlturaJuridico.value

        // email: emailCaja.value, TODO: AGREGAR CAMPOS
        // telefono: telefonoCaja.value,
        // whatsapp: whatsappChecked,
    };

    try {
        const response = await fetch('/ModificarColaborador/Juridico', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify(datosDeUsuario),
        });
        if (response.ok) {
            return true;
        } else {
            console.error('Error en la solicitud:', response.status);
            return false;
        }
    } catch (error) {
        console.error('Error de red o conexión:', error);
        return false;
    }
}
document.addEventListener('DOMContentLoaded', function () {

    const tipoMedioSelect = document.getElementById('tipoNuevoMedioDeContacto');
    const inputContainer = document.getElementById('inputContainer');
    const inputMedio = document.getElementById('nuevoMedioDeContactoJuridico');

    // Lista donde almacenaremos los nuevos medios de contacto
    let nuevosMediosDeContacto = [];

    // Mostrar el input cuando se selecciona un tipo de medio
    // y actualizar el placeholder dinámicamente según el tipo seleccionado.
    tipoMedioSelect.addEventListener('change', function () {
        const tipoSeleccionado = tipoMedioSelect.value;

        if (tipoSeleccionado != null) {
            inputContainer.style.display = 'block';

            // Actualizar placeholder según el tipo de medio
            if (tipoSeleccionado === 'Mail') {
                inputMedio.placeholder = 'Ingrese su correo electrónico';
            } else if (tipoSeleccionado === 'WhatsApp') {
                inputMedio.placeholder = 'Ingrese su número de WhatsApp';
            } else {
                inputMedio.placeholder = 'Ingrese el valor correspondiente';
            }

            inputMedio.value = ''; // Limpiar el campo
        } else {
            inputContainer.style.display = 'none';
        }
    });

    // Función para agregar un nuevo medio de contacto
    function agregarMedioDeContacto() {
        const tipoSeleccionado = tipoMedioSelect.value;
        const valorMedio = inputMedio.value.trim();

        // Validar que ambos campos estén completos
        if (!tipoSeleccionado || !valorMedio) {
            alert('Por favor, complete todos los campos antes de agregar un medio de contacto.');
            return;
        }

        // Crear un objeto para el nuevo medio de contacto
        const nuevoMedio = {
            tipo: tipoSeleccionado,
            valor: valorMedio,
        };

        // Agregar a la lista
        nuevosMediosDeContacto.push(nuevoMedio);

        // Mostrar el nuevo medio en la interfaz (puedes adaptar este código según tu diseño)
        const tagsContainer = document.querySelector('.tags-medios-de-contacto');
        const nuevoTag = document.createElement('span');
        nuevoTag.classList.add('badge', 'd-flex', 'p-2', 'rounded-pill');

        nuevoTag.innerHTML = `
            <span class="px-1">${nuevoMedio.tipo}: ${nuevoMedio.valor}</span>
            <a href="#" onclick="eliminarMedioDeContacto('${nuevoMedio.tipo}', '${nuevoMedio.valor}', this)">
                <svg class="bi ms-1" width="16" height="16">
                    <use xlink:href="#delete-circle"></use>
                </svg>
            </a>
        `;

        tagsContainer.appendChild(nuevoTag);

        // Limpiar el formulario
        tipoMedioSelect.value = '';
        inputContainer.style.display = 'none';
        inputMedio.value = '';
    }

    // Función para eliminar un medio de contacto
    function eliminarMedioDeContacto(tipo, valor, elemento) {
        // Filtrar el medio de contacto de la lista
        nuevosMediosDeContacto = nuevosMediosDeContacto.filter(
            medio => !(medio.tipo === tipo && medio.valor === valor)
        );

        // Eliminar el tag de la interfaz
        const tag = elemento.parentElement;
        tag.remove();
    }
/*
    // Crear el botón y agregarlo antes del usuario
    const agregarButton = document.createElement('button');
    agregarButton.textContent = 'Agregar Medio de Contacto';
    agregarButton.addEventListener('click', agregarMedioDeContacto);

// Agregar el botón antes del h3 con id 'usuario'
    const usuarioHeading = document.getElementById('usuario');
    usuarioHeading.parentElement.insertBefore(agregarButton, usuarioHeading);*/
});