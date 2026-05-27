package client;

import connection.RMIConnection;
import interfaces.GameService;
import ui.GameScreenSwing; // Importação da nova tela Swing

public class ClientMain {

    public static void main(String[] args) {
        try {
            RMIConnection connection = new RMIConnection();
            GameService service = connection.connect();


            GameScreenSwing screen = new GameScreenSwing(service);
            screen.iniciar();

        } catch (Exception e) {
            System.out.println("Erro ao iniciar cliente gráfico:");
            e.printStackTrace();
        }
    }
}