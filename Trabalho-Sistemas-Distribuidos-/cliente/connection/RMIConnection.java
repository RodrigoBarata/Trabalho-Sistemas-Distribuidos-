package connection;

import interfaces.GameService;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class RMIConnection {

    public GameService connect() throws Exception {

        String ipDoServidor = "10.8.184.17";

        Registry registry = LocateRegistry.getRegistry(ipDoServidor, 1099);

        return (GameService) registry.lookup("GameService");
    }
}