
function showAlert(message, type) {
    const alertContainer = document.getElementById("alert-container");

    // Crear el elemento de alerta
    const alert = document.createElement("div");
    alert.classList =`alert ${type}`;
    alert.innerHTML = `
        <span>${message}</span>
        <button class="alert-close-btn" onclick="this.parentElement.remove()">×</button>
    `;

    // Agregar la alerta al contenedor
    alertContainer.appendChild(alert);

    // Eliminar automáticamente después de 5 segundos
    setTimeout(() => {
        alertElement.remove();
    }, 10000);
}
