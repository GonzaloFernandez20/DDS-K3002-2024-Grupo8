package tp_anual.tp_anual_implementacion;

public class HeladeraCliente {
/*    private static final String BROKER_ADDRESS = "localhost"; // Dirección del broker
    private static final int BROKER_PORT = 12345; // Puerto del broker

    public static void main(String[] args) {
        Direccion direccion = new Direccion("Rivadavia", "100", "1111", "Local");
        Persona persona = new PersonaJuridica("SRL", ONG, "Muebles", direccion);
        Colaborador colaborador = new Colaborador(persona);
        try (Socket socket = new Socket(BROKER_ADDRESS, BROKER_PORT)) {
            System.out.println("Conectado al broker");

            DataInputStream in = new DataInputStream(socket.getInputStream());
            List<Vianda> stock1 = new ArrayList<Vianda>();
            Modelo modeloEstandar = new Modelo(10F, 2F);
            Heladera heladera = new Heladera(colaborador, modeloEstandar, stock1, null,3,null);

            Sistema sistema = new Sistema(); // Crea una instancia del sistema
            heladera.setSistema(sistema);

            while (true) {
                // Leemos la temperatura enviada por el broker
                float temperatura = in.readFloat();
                System.out.println("Temperatura recibida: " + temperatura);

                // Actualiza la temperatura en la heladera
                heladera.setUltimaTemperaturaRegistrada(temperatura);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }*/
}