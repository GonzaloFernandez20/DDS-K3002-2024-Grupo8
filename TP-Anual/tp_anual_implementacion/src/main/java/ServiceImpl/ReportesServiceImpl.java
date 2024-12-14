package ServiceImpl;

import DAOs.FallasPorHeladeraDAO;
import DAOs.ViandasPorColaboradorDAO;
import Modelo.Dominio.colaborador.Colaborador;
import Modelo.Dominio.reportes.ViandasPorColaborador;
import Modelo.Dominio.reportes.ViandasPorHeladera;
import Repositories.Accesos_a_heladeras.AperturaRepository;
import Repositories.colaborador.ColaboradorRepository;
import Repositories.heladera.HeladeraRepository;
import Modelo.Dominio.heladera.Heladera;
import Modelo.Dominio.reportes.FallasPorHeladera;
import Services.ReportesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

@Service
public class ReportesServiceImpl implements ReportesService {

    private final HeladeraRepository heladeraRepository;
    private final ColaboradorRepository colaboradorRepository;
    private final AperturaRepository aperturaRepository;

    @Autowired
    public ReportesServiceImpl(HeladeraRepository heladeraRepository, ColaboradorRepository colaboradorRepository, AperturaRepository aperturaRepository) {
        this.heladeraRepository = heladeraRepository;
        this.colaboradorRepository = colaboradorRepository;
        this.aperturaRepository = aperturaRepository;
    }

    @Override
    public List<FallasPorHeladera> traerFallasPorHeladeras() {
        List<FallasPorHeladera> fallasPorHeladerasLista = new ArrayList<FallasPorHeladera>();

        List<FallasPorHeladeraDAO> fallasPorHeladeraDAOs = heladeraRepository.traerReportesDeFallasEntreFechas(LocalDateTime.now().minusWeeks(1), LocalDateTime.now());

        List<Integer> ids_de_heladeras = fallasPorHeladeraDAOs.stream().map(fallasPorHeladeraDAO -> fallasPorHeladeraDAO.getId_heladera()).toList();
        List<Heladera> heladeras_con_fallas = heladeraRepository.findAllById(ids_de_heladeras);

        System.out.println(Arrays.toString(ids_de_heladeras.toArray()));
        System.out.println(Arrays.toString(heladeras_con_fallas.stream().map(heladera -> heladera.getid_heladera()).toArray()));

        for (int i = 0; i < fallasPorHeladeraDAOs.size(); i++){
            FallasPorHeladera fallasPorHeladera = new FallasPorHeladera();

            Integer cantidad_de_fallas = fallasPorHeladeraDAOs.get(i).getCantidad_de_fallas();
            fallasPorHeladera.setCantidadDeFallas(cantidad_de_fallas);

            Heladera heladera = heladeras_con_fallas.get(i);
            fallasPorHeladera.setHeladera(heladera);

            fallasPorHeladerasLista.add(fallasPorHeladera);
        }
        System.out.println("Lista de FallasPorHeladera: ");
        System.out.println(Arrays.toString(fallasPorHeladerasLista.stream().map(fallasPorHeladera -> fallasPorHeladera.getHeladera().getid_heladera()).toArray()));
        System.out.println(Arrays.toString(fallasPorHeladerasLista.stream().map(fallasPorHeladera -> fallasPorHeladera.getCantidadDeFallas()).toArray()));
        return fallasPorHeladerasLista;
    }
    @Override
    public List<ViandasPorColaborador> traerViandasPorColaborador(){
        List<ViandasPorColaborador> viandasPorColaboradorLista = new ArrayList<ViandasPorColaborador>();

        List<ViandasPorColaboradorDAO> viandasPorColaboradorDAOS= colaboradorRepository.traerViandasPorColaboradoresEntreFechas(LocalDate.now().minusWeeks(1), LocalDate.now());

        List<Integer> ids_colaboradores = viandasPorColaboradorDAOS.stream().map(viandasPorColaboradorDAO -> viandasPorColaboradorDAO.getId_colaborador()).toList();
        List<Colaborador> colaboradores = colaboradorRepository.findAllById(ids_colaboradores);

        for (int i = 0; i < viandasPorColaboradorDAOS.size(); i++){
            ViandasPorColaborador viandasPorColaborador = new ViandasPorColaborador();

            Integer cantidad_de_viandas = viandasPorColaboradorDAOS.get(i).getCantidad_de_viandas();
            viandasPorColaborador.setCantidadDeViandas(cantidad_de_viandas);

            Colaborador colaborador = colaboradores.get(i);
            viandasPorColaborador.setColaborador(colaborador);

            viandasPorColaboradorLista.add(viandasPorColaborador);
        }
        System.out.println("Lista de ViandasPorColaborador:");
        System.out.println(Arrays.toString(viandasPorColaboradorLista.stream().map(viandasPorColaborador -> viandasPorColaborador.getColaborador().getId_colaborador()).toArray()));
        System.out.println(Arrays.toString(viandasPorColaboradorLista.stream().map(viandasPorColaborador -> viandasPorColaborador.getCantidadDeViandas()).toArray()));
        return viandasPorColaboradorLista;
    }
    @Override
    public List<ViandasPorHeladera> traerViandasPorHeladera(){
        List<ViandasPorHeladera> viandasPorHeladeraList = new ArrayList<ViandasPorHeladera>();

        List<Heladera> heladeras_BD = heladeraRepository.findAll();

        heladeras_BD.forEach( heladera -> {
            ViandasPorHeladera viandasPorHeladera = new ViandasPorHeladera();
            viandasPorHeladera.setHeladera(heladera);
            viandasPorHeladera.setViandasRetiradas(aperturaRepository.traerViandasRetiradasEntreFechasDeUnaHeladera(heladera.getid_heladera(), LocalDateTime.now().minusWeeks(1), LocalDateTime.now()));
            viandasPorHeladera.setViandasColocadas(aperturaRepository.traerViandasIngresadasEntreFechasDeUnaHeladera(heladera.getid_heladera(), LocalDateTime.now().minusWeeks(1), LocalDateTime.now()));
            viandasPorHeladeraList.add(viandasPorHeladera);
        });

        /*List<ViandasPorHeladeraDAO> viandasPorHeladeraDAOS = aperturaRepository.traerViandasIngresadasYRetiradasEntreFechas(LocalDateTime.now().minusWeeks(1), LocalDateTime.now());

        List<Integer> viandas_colocadas_lista = viandasPorHeladeraDAOS.stream().map(viandasPorHeladeraDAO -> viandasPorHeladeraDAO.getViandasColocadas()).toList();
        List<Integer> viandas_retiradas_lista = viandasPorHeladeraDAOS.stream().map(viandasPorHeladeraDAO -> viandasPorHeladeraDAO.getViandasRetiradas()).toList();
        List<Integer> ids_de_heladeras        = viandasPorHeladeraDAOS.stream().map(viandasPorHeladeraDAO -> viandasPorHeladeraDAO.getId_heladera()     ).toList();

        List<Heladera> heladeras_BD = heladeraRepository.findAllById(ids_de_heladeras);

        for (int i = 0; i < viandasPorHeladeraDAOS.size(); i++){
            ViandasPorHeladera viandasPorHeladera = new ViandasPorHeladera();

            Integer cantidad_de_viandas_colocadas = viandas_colocadas_lista.get(i);
            viandasPorHeladera.setViandasColocadas(cantidad_de_viandas_colocadas);

            Integer cantidad_de_viandas_retiradas = viandas_retiradas_lista.get(i);
            viandasPorHeladera.setViandasRetiradas(cantidad_de_viandas_retiradas);

            Heladera heladera = heladeras_BD.get(i);
            viandasPorHeladera.setHeladera(heladera);

            viandasPorHeladeraList.add(viandasPorHeladera);
        }*/

        System.out.println("Lista de ViandasPorHeladera:");
        System.out.println(Arrays.toString(viandasPorHeladeraList.stream().map(viandasPorHeladera -> viandasPorHeladera.getHeladera().getid_heladera()).toArray()));
        System.out.println(Arrays.toString(viandasPorHeladeraList.stream().map(viandasPorHeladera -> viandasPorHeladera.getViandasColocadas()).toArray()));
        System.out.println(Arrays.toString(viandasPorHeladeraList.stream().map(viandasPorHeladera -> viandasPorHeladera.getViandasRetiradas()).toArray()));
        return viandasPorHeladeraList;
    }

}