package cliente.interfaces;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface GameService extends Remote {
    String conectarJogador(String nome) throws RemoteException;
    boolean fazerJogada(int linha, int coluna, String jogador) throws RemoteException;
    String[][] obterTabuleiro() throws RemoteException;
    String verificarVencedor() throws RemoteException;
    String obterTurnoAtual() throws RemoteException;
    boolean jogoFinalizado() throws RemoteException;
}
