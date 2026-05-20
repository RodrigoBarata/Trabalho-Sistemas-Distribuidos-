package ui;

import interfaces.GameService;
import utils.BoardPrinter;

import java.rmi.RemoteException;
import java.util.Scanner;

public class GameScreen {

    private GameService service;

    private String simbolo;

    private Scanner scanner;

    public GameScreen(GameService service) {

        this.service = service;

        scanner = new Scanner(System.in);
    }

    public void iniciar() {

        try {

            System.out.println("=== JOGO DA VELHA MULTIPLAYER ===");

            System.out.print("Digite seu nome: ");

            String nome = scanner.nextLine();

            simbolo = service.conectarJogador(nome);

            if (simbolo.equals("Sala cheia")) {

                System.out.println("A sala já está cheia.");

                return;
            }

            System.out.println("Você é o jogador: " + simbolo);

            loopJogo();

        } catch (Exception e) {

            System.out.println("Erro no jogo:");

            e.printStackTrace();
        }
    }

    private void loopJogo() throws RemoteException {

        while (!service.jogoFinalizado()) {

            atualizarTela();

            String turno = service.obterTurnoAtual();

            if (turno.equals(simbolo)) {

                System.out.println("Seu turno.");

                System.out.print("Linha (0-2): ");
                int linha = scanner.nextInt();

                System.out.print("Coluna (0-2): ");
                int coluna = scanner.nextInt();

                boolean jogada = service.fazerJogada(linha, coluna, simbolo);

                if (!jogada) {

                    System.out.println("Jogada inválida.");
                }

            } else {

                System.out.println("Aguardando jogador adversário...");
            }

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        finalizarJogo();
    }

    private void atualizarTela() throws RemoteException {

        String[][] tabuleiro = service.obterTabuleiro();

        BoardPrinter.print(tabuleiro);
    }

    private void finalizarJogo() throws RemoteException {

        atualizarTela();

        String vencedor = service.verificarVencedor();

        if (vencedor == null) {

            System.out.println("Empate!");

        } else if (vencedor.equals(simbolo)) {

            System.out.println("Você venceu!");

        } else {

            System.out.println("Você perdeu!");
        }
    }
}
