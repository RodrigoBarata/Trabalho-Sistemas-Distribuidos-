package utils;

public class BoardPrinter {

    public static void print(String[][] tabuleiro) {

        System.out.println();

        for (int i = 0; i < 3; i++) {

            for (int j = 0; j < 3; j++) {

                String valor = tabuleiro[i][j];

                if (valor == null || valor.trim().isEmpty() || valor.equals("\0")) {
                    valor = " ";
                }

                System.out.print(" " + valor + " ");

                if (j < 2) {
                    System.out.print("|");
                }
            }

            System.out.println();

            if (i < 2) {
                System.out.println("-----------");
            }
        }

        System.out.println();
    }
}
