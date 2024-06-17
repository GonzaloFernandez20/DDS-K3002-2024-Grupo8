package tp_anual.tp_anual_implementacion;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import seguridad.Criterio;
import seguridad.Validador;
import seguridad.CumpleConElLargo;
import seguridad.EsContraseniaFuerte;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.util.Lists.newArrayList;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class TpAnualImplementacionApplicationTests {

	@Test
	void contextLoads() {
		CumpleConElLargo contraseniaValida = new CumpleConElLargo();
		EsContraseniaFuerte esContraseniaFuerte = new EsContraseniaFuerte();
		List<Criterio> criterios;
        criterios = new ArrayList<>();
		criterios.add(contraseniaValida);
		criterios.add(esContraseniaFuerte);
        Validador validador;
        validador = new Validador("1234", criterios);
        assertFalse(validador.validarRegistro(criterios, "1234"), "Contrasenia insegura");
        assertTrue(validador.validarRegistro(criterios, "j5is75nk4"), "Contrasenia segura");
		assertFalse(validador.validarRegistro(criterios, "j5is75nk4jwbch4ihd98bdajncxiabfbqcnacbn3h4chbqiuea4n5g83ng58ngun3g"), "Contrasenia larga");
		assertFalse(validador.validarRegistro(criterios, "3k94d6"), "Contrasenia corta");
	}

}
