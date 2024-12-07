@startuml
title Diagrama de Secuencia para "Manejar Incidentes"

actor Técnico as T
boundary "Interfaz Manejo de Incidentes" as SV
control "Controlador de Notificaciones" as SN
entity "Heladera" as H
entity "Manejo de Notificaciones" as MN

T -> SV: Reportar incidente
SV -> SN: Solicitar registro de incidente
SN -> H: Set heladera inactiva
H-> MN: Enviar Notificacion a Suscriptor
MN -> SN: Respuesta: Heladera Inactiva y Notificacion Enviada
SN -> SV: Confirmación reporte completado
SV -> T: Mostrar mensaje de reporte exitoso
@enduml