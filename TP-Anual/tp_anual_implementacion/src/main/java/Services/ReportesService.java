package Services;

import DAOs.ViandasPorHeladeraDAO;
import Modelo.Dominio.reportes.FallasPorHeladera;
import Modelo.Dominio.reportes.ViandasPorColaborador;
import Modelo.Dominio.reportes.ViandasPorHeladera;

import java.util.List;


public interface ReportesService {
    public List<FallasPorHeladera> traerFallasPorHeladeras();
    public List<ViandasPorColaborador> traerViandasPorColaborador();
    public List<ViandasPorHeladera> traerViandasPorHeladera();
}
