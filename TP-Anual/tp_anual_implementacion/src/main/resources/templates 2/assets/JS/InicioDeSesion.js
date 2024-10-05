document.getElementById('registrationForm').addEventListener('submit', function(event) {
    event.preventDefault(); 

    const username = document.getElementById('username').value.trim();
    const password = document.getElementById('password').value.trim();
    const usernameError = document.getElementById('usernameError');
    const passwordError = document.getElementById('passwordError');

    usernameError.textContent = '';
    passwordError.textContent = '';
    document.getElementById('username').classList.remove('error');
    document.getElementById('password').classList.remove('error');

    let isValid = true;

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

    if (isValid) { alert('Registro exitoso!'); }
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
