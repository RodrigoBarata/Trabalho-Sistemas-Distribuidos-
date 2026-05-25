package connection;

import interfaces.GameService;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class RMIConnection {

    public GameService connect() throws Exception {

        Registry registry = LocateRegistry.getRegistry("10.8.181.9", 1099);

        return (GameService) registry.lookup("GameService");
    }
}
