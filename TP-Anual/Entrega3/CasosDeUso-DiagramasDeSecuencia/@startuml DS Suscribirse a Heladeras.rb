@startuml
title Diagrama de Secuencia para "Suscribirse a Heladeras"

actor "Colaborador" as C
boundary "Interfaz Suscripcion Heladeras" as IS
control "Gestor de Suscripciones" as GS
entity "Repositorio de Contribuciones" as RC

C -> IS: Elegir tipo de suscripcion en heladera
IS -> GS: Generar nueva suscripcion en sistema
GS -> RC: Vincular al actor con la notificacion determinada
RC -> GS: Respuesta: Suscripcion Registrada
GS -> IS: Vinculacion realizada exitosamente
IS -> C: Mostrar mensaje "Te notificaremos frente a cambios en la Heladera"

@enduml