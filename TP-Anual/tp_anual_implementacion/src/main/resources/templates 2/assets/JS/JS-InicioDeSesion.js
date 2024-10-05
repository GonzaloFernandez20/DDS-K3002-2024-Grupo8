document.getElementById('registrationForm').addEventListener('submit', function(event) {
    event.preventDefault(); 

    const usuario = document.getElementById('usuario');
    const contrasena = document.getElementById('contrasena');

    let hasError = false;

    if (!usuario.value) {
        hasError = true;
        document.getElementById('usuarioError').innerText = 'El usuario es requerido';
        usuario.classList.add('error');
    } else {
        document.getElementById('usuarioError').innerText = '';
        usuario.classList.remove('error');
    }

    if (!contrasena.value) {
        hasError = true;
        document.getElementById('contrasenaError').innerText = 'La contraseña es requerida';
        contrasena.classList.add('error');
    } else {
        document.getElementById('contrasenaError').innerText = '';
        contrasena.classList.remove('error');
    }

    if (!hasError) {
        alert('Inicio de Sesion Exitoso');
        if (usuario.value == "admin" && contrasena.value == "admin") {
            location.href="Administrador.html";    
        } 
        else {
            location.href="Home.html";    
        }
    }
});

document.getElementById('username').addEventListener('input', function() {
    const usernameError = document.getElementById('usernameError');
    this.classList.remove('error');
    usernameError.textContent = '';
});

document.getElementById('password').addEventListener('input', function() {
    const passwordError = document.getElementById('passwordError');
    this.classList.remove('error');
    passwordError.textContent = '';
});
