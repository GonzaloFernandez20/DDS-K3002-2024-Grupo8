document.addEventListener('DOMContentLoaded', () => {
    // Suponiendo que tienes un valor de puntos
    const puntos = 5; // Habria que traer el valor de una base de datos

    // Selecciona el contenedor donde mostrarás los puntos
    const starsContainer = document.getElementById('stars');
    starsContainer.innerHTML = puntos;
});