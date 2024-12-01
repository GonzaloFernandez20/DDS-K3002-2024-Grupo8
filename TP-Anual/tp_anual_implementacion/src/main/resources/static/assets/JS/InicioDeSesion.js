document.getElementById('registrationForm').addEventListener('submit', async function (event) {
    event.preventDefault();
    console.log('Formulario enviado');

    const usuario = document.getElementById('usuario');
    const contrasena = document.getElementById('contrasena');
    const usuarioError = document.getElementById('usuarioError');
    const contrasenaError = document.getElementById('contrasenaError');

    console.log('Usuario:', usuario.value);
    console.log('Contraseña:', contrasena.value)

    // Reset de mensajes de error y clases
    resetearErrores([usuario, contrasena], [usuarioError, contrasenaError]);

    let huboError = false;

    // Validación para el formulario de inicio de sesión
    huboError |= validateField(usuario, usuarioError, 'El usuario es requerido');
    huboError |= validateField(contrasena, contrasenaError, 'La contraseña es requerida');

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
    function resetearErrores(inputs, errorElements) {
        inputs.forEach(input => input.classList.remove('error'));
        errorElements.forEach(errorElement => errorElement.textContent = '');
    }

    // Función para validar un campo
    function validateField(input, errorElement, errorMessage) {
        if (!input.value) {
            errorElement.innerText = errorMessage;
            input.classList.add('error');
            return true;
        } else {
            return false;
        }
    }

    /*    if (isValid) {
            if (usuario.value === "admin" && contrasena.value === "admin") {
                location.href = "Administrador.html";
            } else if (usuario.value && contrasena.value) {

                location.href = "Home.html";
            } else {
                alert('Registro exitoso!');
            }
        }*/
});


// Manejo de eventos "input" para eliminar errores de los campos

document.getElementById('usuario').addEventListener('input', function() {
    document.getElementById('usuarioError').textContent = '';
    this.classList.remove('error');
});

document.getElementById('contrasena').addEventListener('input', function() {
    document.getElementById('contrasenaError').textContent = '';
    this.classList.remove('error');
});

