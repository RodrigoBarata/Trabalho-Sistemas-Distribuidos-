package ui;

import interfaces.GameService;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.RemoteException;

public class GameScreenSwing extends JFrame {

    private final GameService service;
    private String simbolo;
    private final JButton[][] botoes = new JButton[3][3];
    private JLabel statusLabel;
    private Timer timer;

    public GameScreenSwing(GameService service) {
        this.service = service;
        configurarJanela();
    }

    private void configurarJanela() {
        setTitle("Jogo da Velha Multiplayer - RMI & Swing");
        setSize(450, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // Centraliza a janela na tela
        setLayout(new BorderLayout());


        statusLabel = new JLabel("Conectando ao jogo...", SwingConstants.CENTER);
        statusLabel.setFont(new Font("Arial", Font.BOLD, 16));
        statusLabel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        add(statusLabel, BorderLayout.NORTH);


        JPanel painelTabuleiro = new JPanel(new GridLayout(3, 3, 5, 5));
        painelTabuleiro.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                JButton botao = new JButton("");
                botao.setFont(new Font("Arial", Font.BOLD, 55));
                botao.setFocusPainted(false);
                botao.setEnabled(false); // Começam desabilitados até a validação do turno

                final int linha = i;
                final int coluna = j;

                botao.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        tentarJogada(linha, coluna);
                    }
                });

                botoes[i][j] = botao;
                painelTabuleiro.add(botao);
            }
        }
        add(painelTabuleiro, BorderLayout.CENTER);
    }

    public void iniciar() {
        setVisible(true);


        String nome = JOptionPane.showInputDialog(this, "Digite seu nome para entrar na partida:", "Identificação", JOptionPane.PLAIN_MESSAGE);

        if (nome == null || nome.trim().isEmpty()) {
            System.exit(0);
        }

        try {
            simbolo = service.conectarJogador(nome.trim());

            if ("Sala cheia".equals(simbolo)) {
                JOptionPane.showMessageDialog(this, "A sala já está cheia! Não é possível entrar.", "Erro", JOptionPane.ERROR_MESSAGE);
                System.exit(0);
            }

            statusLabel.setText("Você é o jogador: " + simbolo + " | Aguardando início...");


            timer = new Timer(1000, e -> rotinaDeSincronizacao());
            timer.start();

        } catch (RemoteException e) {
            JOptionPane.showMessageDialog(this, "Erro de comunicação com o servidor RMI.", "Erro de Conexão", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
            System.exit(1);
        }
    }

    private void tentarJogada(int linha, int coluna) {
        try {
            boolean sucesso = service.fazerJogada(linha, coluna, simbolo);
            if (!sucesso) {
                JOptionPane.showMessageDialog(this, "Jogada inválida! Escolha uma célula vazia.", "Aviso", JOptionPane.WARNING_MESSAGE);
            } else {
                rotinaDeSincronizacao();
            }
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    private void rotinaDeSincronizacao() {
        try {

            String[][] tabuleiro = service.obterTabuleiro();
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    String valor = tabuleiro[i][j];
                    if (valor == null || valor.trim().isEmpty() || valor.equals("\0") || valor.equals(" ")) {
                        botoes[i][j].setText("");
                    } else {
                        botoes[i][j].setText(valor);
                    }
                }
            }


            if (service.jogoFinalizado()) {
                timer.stop();
                finalizarPartida();
                return;
            }


            String turnoAtual = service.obterTurnoAtual();
            boolean meuTurno = turnoAtual.equals(simbolo);

            if (meuTurno) {
                statusLabel.setText("Seu turno! Você joga com (" + simbolo + ")");
            } else {
                statusLabel.setText("Aguardando a jogada do oponente... (" + simbolo + ")");
            }


            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    botoes[i][j].setEnabled(meuTurno && botoes[i][j].getText().isEmpty());
                }
            }

        } catch (RemoteException e) {
            statusLabel.setText("Erro de conexão com o servidor.");
            e.printStackTrace();
        }
    }

    private void finalizarPartida() {
        try {

            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    botoes[i][j].setEnabled(false);
                }
            }

            String vencedor = service.verificarVencedor();
            String mensagem;

            if (vencedor == null || vencedor.trim().isEmpty() || vencedor.equals("Empate")) {
                mensagem = "O jogo terminou em Empate (Deu Velha)!";
            } else if (vencedor.equals(simbolo)) {
                mensagem = "Vitória! Você venceu a partida! 🎉";
            } else {
                mensagem = "Derrota! O outro jogador venceu. 😢";
            }

            statusLabel.setText("Fim de jogo!");
            JOptionPane.showMessageDialog(this, mensagem, "Resultado Final", JOptionPane.INFORMATION_MESSAGE);

        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }
}