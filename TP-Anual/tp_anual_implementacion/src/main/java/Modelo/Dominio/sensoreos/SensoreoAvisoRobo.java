package Modelo.Dominio.sensoreos;

import Modelo.Dominio.heladera.Heladera;

    public class SensoreoAvisoRobo {
        private Integer id;
        public Heladera heladera;

        public Integer getId() {
            return id;
        }

        public void notificarColaborador() {
            heladera.huboIncidente();
        }
}
