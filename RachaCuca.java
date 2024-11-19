import java.util.Random;
import java.util.Scanner;

public class RachaCuca {
    private static final int tamanho = 3;
    private int[][] tabuleiro;
    private int linhavazia, colunavazia;

    public RachaCuca() {
        tabuleiro = new int[tamanho][tamanho];
        inicio();
  
    }

    private void inicio() {
        int num = 1;
        for (int i = 0; i < tamanho; i++) {
            for (int j = 0; j < tamanho; j++) {
                if (num < tamanho * tamanho) {
                    tabuleiro[i][j] = num++;
                } else {
                    tabuleiro[i][j] = 0; // Espaço vazio
                    linhavazia = i;
                    colunavazia = j;
                }
            }
        }
    }

    private void embaralhartabuleiro(int moves) {
        Random rand = new Random();
        for (int i = 0; i < moves; i++) {
            int direcao = rand.nextInt(4);
            switch (direcao) {
                case 0: move(linhavazia - 1, colunavazia); break; // Cima
                case 1: move(linhavazia + 1, colunavazia); break; // Baixo
                case 2: move(linhavazia, colunavazia - 1); break; // Esquerda
                case 3: move(linhavazia, colunavazia + 1); break; // Direita
            }
        }
    }

    private boolean move(int novalinha, int novacoluna) {
        if (movimentovalido(novalinha, novacoluna)) {
            tabuleiro[linhavazia][colunavazia] = tabuleiro[novalinha][novacoluna];
            tabuleiro[novalinha][novacoluna] = 0;
            linhavazia = novalinha;
            colunavazia = novacoluna;
          
            return true;
        }
        return false;
    }

    private boolean movimentovalido(int linha, int coluna) {
        return linha >= 0 && linha < tamanho && coluna >= 0 && coluna < tamanho;
    }

    private boolean resolvido() {
        int num = 1;
        for (int i = 0; i < tamanho; i++) {
            for (int j = 0; j < tamanho; j++) {
                if (i == tamanho - 1 && j == tamanho - 1) {
                    return tabuleiro[i][j] == 0; // Última posição deve estar vazia
                }
                if (tabuleiro[i][j] != num++) {
                    return false;
                }
            }
        }
        return true;
    }

    public void mostrartabuleiro() {
        for (int i = 0; i < tamanho; i++) {
            for (int j = 0; j < tamanho; j++) {
                if (tabuleiro[i][j] == 0) {
                    System.out.print("   ");
                } else {
                    System.out.printf("%2d ", tabuleiro[i][j]);
                }
            }
            System.out.println();
        }
        
    }

    public void startGame() {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            mostrartabuleiro();
            if (resolvido()) {
                System.out.println("Parabens! Você completou o quebra-cabeça! ");
                break;
            }
            System.out.print("Escolha uma peca para mover (ou 0 para sair): ");
            int choice = scanner.nextInt();
            if (choice == 0) {
                System.out.println("Obrigado por jogar! ");
                break;
            }
            if (!makeMove(choice)) {
                System.out.println("Movimento inválido! Tente novamente.");
            }
        }
        scanner.close();
    }

    private boolean makeMove(int choice) {
        for (int i = 0; i < tamanho; i++) {
            for (int j = 0; j < tamanho; j++) {
                if (tabuleiro[i][j] == choice) {
                     if ((i == linhavazia && Math.abs(j - colunavazia) == 1) || (j == colunavazia && Math.abs(i - linhavazia) == 1)) {
                    return move(i, j);
                }
            }
        }
        }
        return false; // Se a peça não foi encontrada
    }

    public static void main(String[] args) {
        RachaCuca game = new RachaCuca();
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("Menu:");
            System.out.println("0 - Sair");
            System.out.println("1 - Novo Jogo");
            System.out.println("2 - Instrucoes");
            int option = scanner.nextInt();
            if (option == 0) {
                System.out.println("Obrigado por jogar!");
                break;
            } else if (option == 1) {
                System.out.print("Escolha o nivel de dificuldade (1 - Facil, 2 - Medio, 3 - Dificil): ");
                int level = scanner.nextInt();
                int moves = 20; // Default para fácil
                if (level == 2) moves = 40;
                else if (level == 3) moves = 80;
                game.embaralhartabuleiro(moves);
                game.startGame();
            } else if (option == 2) {
                mostrarinstrucoes();
            } else {
                System.out.println("Opcao invalida! Tente novamente.");
            }
        }
        scanner.close();
    }

    private static void mostrarinstrucoes() {
        System.out.println("Instrucoes:");
        System.out.println("1. Mova as pecas adjacentes ao espaco vazio.");
        System.out.println("2. O objetivo e ordenar as peças de 1 a 8, "
                + "deixando o espaco vazio no canto inferior direito.");
        System.out.println("3. Digite o numero da peca que deseja mover ou 0 para sair.");
    }
}
