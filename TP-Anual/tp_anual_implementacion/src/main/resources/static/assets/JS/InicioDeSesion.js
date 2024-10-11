document.getElementById('registrationForm').addEventListener('submit', function(event) {
    event.preventDefault();

    const username = document.getElementById('username').value.trim();
    const password = document.getElementById('password').value.trim();
    const usernameError = document.getElementById('usernameError');
    const passwordError = document.getElementById('passwordError');

    const usuario = document.getElementById('usuario');
    const contrasena = document.getElementById('contrasena');
    const usuarioError = document.getElementById('usuarioError');
    const contrasenaError = document.getElementById('contrasenaError');

    // Reset de mensajes de error y clases
    usernameError.textContent = '';
    passwordError.textContent = '';
    usuarioError.textContent = '';
    contrasenaError.textContent = '';
    
    document.getElementById('username').classList.remove('error');
    document.getElementById('password').classList.remove('error');
    usuario.classList.remove('error');
    contrasena.classList.remove('error');

    let isValid = true;
    let hasError = false;

    // Validación para el formulario de registro
    if (username.toLowerCase() === 'admin') {
        usernameError.textContent = 'El nombre de usuario "admin" no está permitido.';
        document.getElementById('username').classList.add('error');
        isValid = false;
    }

    if (username === '') {
        usernameError.textContent = 'Por favor, ingrese un nombre de usuario.';
        document.getElementById('username').classList.add('error');
        isValid = false;
    }

    if (password === '1234' || password.toLowerCase() === 'admin') {
        passwordError.textContent = 'La contraseña no puede ser "1234" o "admin".';
        document.getElementById('password').classList.add('error');
        isValid = false;
    }

    if (password === '') {
        passwordError.textContent = 'Por favor, ingrese una contraseña.';
        document.getElementById('password').classList.add('error');
        isValid = false;
    }

    // Validación para el formulario de inicio de sesión
    if (!usuario.value) {
        usuarioError.innerText = 'El usuario es requerido';
        usuario.classList.add('error');
        hasError = true;
    } 

    if (!contrasena.value) {
        contrasenaError.innerText = 'La contraseña es requerida';
        contrasena.classList.add('error');
        hasError = true;
    } 

    // Resultado después de validación de registro e inicio de sesión
    if (!hasError && isValid) {
        if (usuario.value === "admin" && contrasena.value === "admin") {
            location.href = "Administrador.html";
        } else if (usuario.value && contrasena.value) {
            location.href = "Home.html";
        } else {
            alert('Registro exitoso!');
        }
    }
});

// Manejo de eventos "input" para eliminar errores de los campos
document.getElementById('username').addEventListener('input', function() {
    document.getElementById('usernameError').textContent = '';
    this.classList.remove('error');
});

document.getElementById('password').addEventListener('input', function() {
    document.getElementById('passwordError').textContent = '';
    this.classList.remove('error');
});

document.getElementById('usuario').addEventListener('input', function() {
    document.getElementById('usuarioError').textContent = '';
    this.classList.remove('error');
});

document.getElementById('contrasena').addEventListener('input', function() {
    document.getElementById('contrasenaError').textContent = '';
    this.classList.remove('error');
});
