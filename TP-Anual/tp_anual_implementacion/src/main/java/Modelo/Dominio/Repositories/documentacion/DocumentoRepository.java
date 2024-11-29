package Modelo.Dominio.Repositories.documentacion;

import Modelo.Dominio.documentacion.Documento;
import Modelo.Dominio.localizacion.Direccion;
import org.springframework.data.jpa.repository.JpaRepository;


public interface DocumentoRepository extends JpaRepository<Documento, Integer> {}
