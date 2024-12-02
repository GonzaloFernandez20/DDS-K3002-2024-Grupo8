document.getElementById('registrationForm').addEventListener('submit', async function (event) {
    event.preventDefault();
    console.log('Formulario enviado');

    const usuario = document.getElementById('usuario');
    const contrasena = document.getElementById('contrasena');

    // Reset de mensajes de error y clases
    resetearErrores([usuario, contrasena]);

    let huboError = false;

    // Validación para el formulario de inicio de sesión
    huboError |= validarCampo(usuario, 'El usuario es requerido');
    huboError |= validarCampo(contrasena, 'La contraseña es requerida');

    // Resultado después de validación de registro e inicio de sesión
    if (!huboError) {
        await verificarUsuario(usuario.value, contrasena.value);
    }

    // -----------------------------------------------------------------------------------------------

    // Verificamos el usuario en el Back
    async function verificarUsuario(usuario, contrasena) {
        const datosDeUsuario = {
            nombreDeUsuario: usuario,
            contrasenia: contrasena
        };

        try {
            const respuesta = await fetch('/InicioDeSesion', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                },
                body: JSON.stringify(datosDeUsuario),
            });
            if (!respuesta.ok) {
                throw new Error("Usuario y contrasenia incorrectos. Vuelva a intentarlo");
            }
            alert("Usuario y contraseña validados exitosamente.");
            window.location.href = "/Home";
        } catch (error) {
            console.error('Error:', error);
            alert(error.message);
        }
    }

    // Función para resetear los errores
    function resetearErrores(inputs) {
        inputs.forEach(input => {
            input.classList.remove('error');
            const errorElement = input.nextElementSibling;
            if (errorElement) errorElement.textContent = '';
        });
    }

    // Función para validar un campo
    function validarCampo(input, errorMessage) {
        const errorElement = input.nextElementSibling;
        if (!input.value) {
            if (errorElement) errorElement.innerText = errorMessage;
            input.classList.add('error');
            return true;
        } else {
            return false;
        }
    }
});