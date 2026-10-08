import java.io.IOException;
import java.util.Scanner;

public class Main {

    private static final String PASTA_PILHA = "saida_pilha";
    private static final String PASTA_FILA = "saida_fila";

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        ImageService imagem = new ImageService();
        FloodFill floodFill = new FloodFill(imagem);

        int linha = -1;
        int coluna = -1;
        int novaCor = 0xFFFF0000;
        boolean executando = true;

        while (executando) {
            System.out.println("\n===== MENU FLOOD FILL =====");
            System.out.println("1 - Executar com pilha");
            System.out.println("2 - Executar com fila");
            System.out.println("3 - Escolher imagem");
            System.out.println("4 - Escolher coordenada de inicio");
            System.out.println("5 - Escolher nova cor");
            System.out.println("0 - Encerrar");
            System.out.println("Imagem: " + (imagem.temImagem()
                    ? imagem.getLargura() + "x" + imagem.getAltura() : "nenhuma")
                    + " | Coordenada: " + (linha < 0 ? "nao definida" : "X=" + coluna + ", Y=" + linha)
                    + " | Cor: " + formatarCor(novaCor));
            System.out.print("Opcao: ");

            if (!scanner.hasNextLine()) {
                break;
            }
            int opcao = lerInteiro(scanner.nextLine());

            switch (opcao) {
                case 1:
                case 2:
                    boolean pilha = opcao == 1;
                    if (!imagem.temImagem()) {
                        System.out.println("Nenhuma imagem carregada. Use a opcao 3 primeiro.");
                        break;
                    }
                    if (linha < 0) {
                        System.out.println("Coordenada nao definida. Use a opcao 4 primeiro.");
                        break;
                    }
                    String pasta = pilha ? PASTA_PILHA : PASTA_FILA;
                    try {
                        int pintados = pilha
                                ? floodFill.executarComPilha(linha, coluna, novaCor, pasta)
                                : floodFill.executarComFila(linha, coluna, novaCor, pasta);
                        if (pintados == 0) {
                            System.out.println("A nova cor e igual a cor original. Nenhuma alteracao necessaria.");
                        } else {
                            System.out.println("Concluido: " + pintados + " pixels pintados.");
                            System.out.println("Etapas salvas em: " + pasta + "/ (passo_0001.png = imagem original)");
                        }
                    } catch (IOException | IllegalArgumentException | IllegalStateException e) {
                        System.out.println("Erro: " + e.getMessage());
                    }
                    break;

                case 3:
                    System.out.print("Caminho da imagem: ");
                    if (!scanner.hasNextLine()) {
                        break;
                    }
                    String caminho = scanner.nextLine().trim();
                    if (caminho.length() >= 2 && caminho.startsWith("\"") && caminho.endsWith("\"")) {
                        caminho = caminho.substring(1, caminho.length() - 1);
                    }
                    if (caminho.isEmpty()) {
                        System.out.println("Caminho vazio.");
                        break;
                    }
                    try {
                        imagem.abrirImagem(caminho);
                        linha = -1;
                        coluna = -1;
                        System.out.println("Imagem carregada: " + imagem.getLargura() + "x" + imagem.getAltura()
                                + ". Defina a coordenada na opcao 4.");
                    } catch (IOException e) {
                        System.out.println("Erro na imagem: " + e.getMessage());
                    }
                    break;

                case 4:
                    if (!imagem.temImagem()) {
                        System.out.println("Nenhuma imagem carregada. Use a opcao 3 primeiro.");
                        break;
                    }
                    System.out.print("Digite X (coluna, 0 a " + (imagem.getLargura() - 1) + "): ");
                    if (!scanner.hasNextLine()) {
                        break;
                    }
                    int x = lerInteiro(scanner.nextLine());
                    if (x < 0 || x >= imagem.getLargura()) {
                        System.out.println("X invalido. Informe um numero entre 0 e " + (imagem.getLargura() - 1) + ".");
                        break;
                    }
                    System.out.print("Digite Y (linha, 0 a " + (imagem.getAltura() - 1) + "): ");
                    if (!scanner.hasNextLine()) {
                        break;
                    }
                    int y = lerInteiro(scanner.nextLine());
                    if (y < 0 || y >= imagem.getAltura()) {
                        System.out.println("Y invalido. Informe um numero entre 0 e " + (imagem.getAltura() - 1) + ".");
                        break;
                    }
                    coluna = x;
                    linha = y;
                    System.out.println("Coordenada escolhida: (X=" + coluna + ", Y=" + linha + ")");
                    break;

                case 5:
                    System.out.print("Nova cor em hexadecimal (RRGGBB, ex: FF0000): ");
                    if (!scanner.hasNextLine()) {
                        break;
                    }
                    String hex = scanner.nextLine().trim();
                    if (hex.startsWith("#")) {
                        hex = hex.substring(1);
                    }
                    if (!hex.matches("[0-9a-fA-F]{6}")) {
                        System.out.println("Cor invalida. Use 6 digitos hexadecimais, por exemplo FF0000.");
                        break;
                    }
                    novaCor = 0xFF000000 | Integer.parseInt(hex, 16);
                    System.out.println("Nova cor: " + formatarCor(novaCor));
                    break;

                case 0:
                    executando = false;
                    System.out.println("Encerrando programa.");
                    break;

                default:
                    System.out.println("Opcao invalida. Digite um numero de 0 a 5.");
                    break;
            }
        }

        scanner.close();
    }

    // Retorna -1 se o texto nao for um inteiro valido.
    private static int lerInteiro(String texto) {
        try {
            return Integer.parseInt(texto.trim());
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    private static String formatarCor(int argb) {
        return String.format("#%06X", argb & 0xFFFFFF);
    }
}
