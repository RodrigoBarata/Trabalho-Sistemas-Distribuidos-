package model;

import java.io.Serializable;

public class GameRoom implements Serializable {
    private static final long serialVersionUID = 1L;
    private char[][] tabuleiro;
    private int contagemJogadores;
    private char turnoAtual;
    private boolean finalizado;

    public GameRoom() {
        tabuleiro = new char[3][3];
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                tabuleiro[i][j] = ' ';
            }
        }
        contagemJogadores = 0;
        turnoAtual = 'X';
        finalizado = false;
    }

    public char[][] obterTabuleiro() {
        return tabuleiro;
    }

    public int obterContagemJogadores() {
        return contagemJogadores;
    }

    public void incrementarContagemJogadores() {
        contagemJogadores++;
    }

    public char obterTurnoAtual() {
        return turnoAtual;
    }

    public void trocarTurno() {
        turnoAtual = (turnoAtual == 'X') ? 'O' : 'X';
    }

    public boolean estaFinalizado() {
        return finalizado;
    }

    public void definirFinalizado(boolean finalizado) {
        this.finalizado = finalizado;
    }

    public void definirCelula(int linha, int coluna, char jogador) {
        tabuleiro[linha][coluna] = jogador;
    }
}
