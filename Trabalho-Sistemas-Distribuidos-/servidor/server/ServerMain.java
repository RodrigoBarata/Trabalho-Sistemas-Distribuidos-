package server;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import service.GameServiceImpl;

public class ServerMain {
    public static void main(String[] args) {
        try {

            String ipDoServidor = "10.8.184.17";

            System.setProperty("java.rmi.server.hostname", ipDoServidor);
            Registry registry = LocateRegistry.createRegistry(1099);

            GameServiceImpl gameService = new GameServiceImpl();
            registry.rebind("GameService", gameService);

            System.out.println("Servidor RMI iniciado com sucesso no IP: " + ipDoServidor);
        } catch (Exception e) {
            System.err.println("Erro no servidor: " + e.toString());
            e.printStackTrace();
        }
    }
}