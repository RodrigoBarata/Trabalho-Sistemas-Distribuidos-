package service;

import interfaces.GameService;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import model.GameRoom;
import utils.Validator;

public class GameServiceImpl extends UnicastRemoteObject implements GameService {
    private GameRoom gameRoom;

    public GameServiceImpl() throws RemoteException {
        super();
        this.gameRoom = new GameRoom();
    }

    @Override
    public synchronized String conectarJogador(String nome) throws RemoteException {
        if (gameRoom.obterContagemJogadores() == 0) {
            gameRoom.incrementarContagemJogadores();
            System.out.println("Jogador conectado: " + nome + " (X)");
            return "X";
        } else if (gameRoom.obterContagemJogadores() == 1) {
            gameRoom.incrementarContagemJogadores();
            System.out.println("Jogador conectado: " + nome + " (O)");
            return "O";
        }
        return "Sala cheia";
    }

    @Override
    public synchronized boolean fazerJogada(int linha, int coluna, String jogador) throws RemoteException {
        if (gameRoom.estaFinalizado()) return false;
        
        char playerChar = jogador.charAt(0);
        if (gameRoom.obterTurnoAtual() != playerChar) return false;

        if (Validator.isValidMove(gameRoom.obterTabuleiro(), linha, coluna)) {
            gameRoom.definirCelula(linha, coluna, playerChar);
            
            String vencedor = Validator.checkWinner(gameRoom.obterTabuleiro());
            if (vencedor != null) {
                gameRoom.definirFinalizado(true);
            } else {
                gameRoom.trocarTurno();
            }
            return true;
        }
        return false;
    }

    @Override
    public String[][] obterTabuleiro() throws RemoteException {
        char[][] board = gameRoom.obterTabuleiro();
        String[][] stringBoard = new String[3][3];
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                stringBoard[i][j] = String.valueOf(board[i][j]);
            }
        }
        return stringBoard;
    }

    @Override
    public String verificarVencedor() throws RemoteException {
        String vencedor = Validator.checkWinner(gameRoom.obterTabuleiro());
        if ("Empate".equals(vencedor)) return null;
        return vencedor;
    }

    @Override
    public String obterTurnoAtual() throws RemoteException {
        return String.valueOf(gameRoom.obterTurnoAtual());
    }

    @Override
    public boolean jogoFinalizado() throws RemoteException {
        return gameRoom.estaFinalizado();
    }
}
