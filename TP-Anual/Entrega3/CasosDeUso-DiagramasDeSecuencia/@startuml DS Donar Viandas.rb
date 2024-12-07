@startuml
title Diagrama de Secuencia para "Donar Vianda"

actor ColaboradorHumano as CH
boundary "Interfaz Donacion de Viandas" as ID
control "Controlador Donacion" as CD
entity "Repositorio de Heladeras" as RH
entity "Heladera a Donar" as H
entity "Contribuciones de Colaborador" as CC

CH -> ID: Buscar Heladeras Disponibles
ID -> CD: Obtener Lista de Heladeras
CD -> RH: Buscar Heladeras
RH -> RH: Validar Heladeras Activas
group error - heladeras inactivas
    RH -> CD: Respuesta: No hay Heladeras Activas
    CD -> ID: Error en busqueda de Heladeras
    ID -> CH: Mostrar Error
    end

RH -> CD: Respuesta: Lista de Heladeras
CD -> ID: Devolver Lista de Heladeras
ID -> CH: Mostrar Lista de Heladeras

CH -> ID: Seleccionar Viandas a Donar a una Heladera
ID -> CD: Enviar Viandas a Heladera
CD -> H: Ingresar Viandas donadas
H -> CC: Registrar Contribucion
H -> CD: Respuesta: Viandas Donadas
CD -> ID: Devolver Mensaje de Éxito
ID -> CH: Mostrar Mensaje de Éxito
@enduml