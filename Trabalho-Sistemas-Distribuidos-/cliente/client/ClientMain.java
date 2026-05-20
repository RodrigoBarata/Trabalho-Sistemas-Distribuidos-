package client;

import connection.RMIConnection;
import interfaces.GameService;
import ui.GameScreen;

public class ClientMain {

    public static void main(String[] args) {

        try {

            RMIConnection connection = new RMIConnection();

            GameService service = connection.connect();

            GameScreen screen = new GameScreen(service);

            screen.iniciar();

        } catch (Exception e) {

            System.out.println("Erro ao iniciar cliente:");

            e.printStackTrace();
        }
    }
}
