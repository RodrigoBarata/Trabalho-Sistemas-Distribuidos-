package server;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import service.GameServiceImpl;

public class ServerMain {
    public static void main(String[] args) {
        try {
           
            Registry registry = LocateRegistry.createRegistry(1099);
            
           
            GameServiceImpl gameService = new GameServiceImpl();
            
            // Faz o bind do serviço no registro
            registry.rebind("GameService", gameService);
            
            System.out.println("Servidor RMI iniciado com sucesso!");
        } catch (Exception e) {
            System.err.println("Erro no servidor: " + e.toString());
            e.printStackTrace();
        }
    }
}
