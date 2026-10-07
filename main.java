import java.io.IOException;
import java.util.Scanner;

class Position {
    int linha;
    int coluna;

    public Position(int linha, int coluna) {
        this.linha = linha;
        this.coluna = coluna;
    }
}

class Node {
    Position pos;
    Node proximo;

    public Node(Position pos) {
        this.pos = pos;
        this.proximo = null;
    }
}

class Stack {
    Node topo;

    public void push(Node n) {
        n.proximo = this.topo;
        this.topo = n;
    }

    public Node pop() {
        if (isEmpty()) return null;
        
        Node copy = this.topo;
        this.topo = this.topo.proximo; 
        
        return copy;
    }

    public boolean isEmpty() {
        return this.topo == null;
    }
}

class Queue {
    Node start;
    Node end;

    public void enqueue(Node n) {
        if (this.end == null) {
            this.start = n;
            this.end = n;
        } else {
            this.end.proximo = n;
            this.end = n;
        }
    }

    public Node dequeue() {
        if (isEmpty()) return null;
        
        Node copy = this.start;
        this.start = this.start.proximo;
        if (this.start == null) {
            this.end = null;
        }
        
        return copy;
    }

    public boolean isEmpty() {
        return this.start == null;
    }
}
public class main{
   public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        ImageService imagem = new ImageService();

        int value = -1;
        int linha = 0;
        int coluna = 0;
        int corNovaR = 255, corNovaG = 0, corNovaB = 0;
        boolean imagemCarregada = false;

        while (value != 0) {
            System.out.println("===== MENU FLOOD FILL =====");
            System.out.println("1 - Executar com pilha");
            System.out.println("2 - Executar com fila");
            System.out.println("3 - Escolher imagem");
            System.out.println("4 - Escolher coordenada de inicio");
            System.out.println("5 - Escolher nova cor");
            System.out.println("0 - Encerrar");
            System.out.print("Opcao: ");

            value = scanner.nextInt();

            switch (value) {

                case 1:
                    Stack pilha = new Stack();

                    Position inicio = new Position(linha, coluna);
                    Node noInicial = new Node(inicio);

                    int corOriginal = imagem.getPixel(linha,coluna);
                    int novaCor = 0xFFFF0000; // Cor provisoria, alterar quando for implementada a escolha da cor pelo usuario

                    pilha.push(noInicial);

                    System.out.println("Posicao inicial adicionada na pilha.");

                    while (!pilha.isEmpty()) {

                        Node atual = pilha.pop();

                        int linhaAtual = atual.pos.linha;
                        int colunaAtual = atual.pos.coluna;

                        System.out.println("Processando: linha "+ linhaAtual+ ", coluna "+ colunaAtual);

                        if (linhaAtual < 0 || linhaAtual >= imagem.getAltura()
                                || colunaAtual < 0 || colunaAtual >= imagem.getLargura()) {

                            continue;
                        }

                        int corAtual = imagem.getPixel(linhaAtual, colunaAtual);

                        if (corAtual != corOriginal) {
                            continue;
                        }

                        imagem.setPixel(linhaAtual,colunaAtual,novaCor);

                        Position cima = new Position(linhaAtual - 1,colunaAtual);
                        Node noCima = new Node(cima);
                        pilha.push(noCima);

                        Position baixo = new Position(linhaAtual + 1,colunaAtual);
                        Node noBaixo = new Node(baixo);
                        pilha.push(noBaixo);

                        Position esquerda = new Position(linhaAtual,colunaAtual - 1);
                        Node noEsquerda = new Node(esquerda);
                        pilha.push(noEsquerda);

                        Position direita = new Position(linhaAtual,colunaAtual + 1);
                        Node noDireita = new Node(direita);
                        pilha.push(noDireita);
                        
                    }

                    break;

                case 2:
                    if (!imagemCarregada) {
                        System.out.println("Nenhuma imagem carregada. Use a opcao 3 primeiro.");
                        break;
                    }

                    Queue fila = new Queue();

                    Position inicioF = new Position(linha, coluna);
                    Node noInicialF = new Node(inicioF);

                    int corOriginalF = imagem.getPixel(linha, coluna);
                    int novaCorF = (255 << 24) | (corNovaR << 16) | (corNovaG << 8) | corNovaB;

                    if (corOriginalF == novaCorF) {
                        System.out.println("A nova cor é igual à cor original. Nenhuma alteração necessária.");
                        break;
                    }

                    fila.enqueue(noInicialF);

                    System.out.println("Posicao inicial adicionada na fila.");

                    int passoF = 1;
                    try {
                        imagem.salvarImagem("passo_fila_0.png");
                    } catch (IOException e) {
                        System.out.println("Erro ao salvar etapa inicial: " + e.getMessage());
                    }

                    while (!fila.isEmpty()) {

                        Node atual = fila.dequeue();

                        int linhaAtual = atual.pos.linha;
                        int colunaAtual = atual.pos.coluna;

                        System.out.println("Processando: linha " + linhaAtual + ", coluna " + colunaAtual);

                        if (linhaAtual < 0 || linhaAtual >= imagem.getAltura()
                                || colunaAtual < 0 || colunaAtual >= imagem.getLargura()) {

                            continue;
                        }

                        int corAtual = imagem.getPixel(linhaAtual, colunaAtual);

                        if (corAtual != corOriginalF) {
                            continue;
                        }

                        imagem.setPixel(linhaAtual, colunaAtual, novaCorF);
                        passoF++;
                        if (passoF % 5 == 0) {
                            try {
                                String nomeArquivo = String.format("passo_fila_%d.png", passoF);
                                imagem.salvarImagem(nomeArquivo);
                            } catch (IOException e) {
                                System.out.println("Erro ao salvar etapa: " + e.getMessage());
                            }
                        }

                        Position cima = new Position(linhaAtual - 1, colunaAtual);
                        Node noCima = new Node(cima);
                        fila.enqueue(noCima);

                        Position baixo = new Position(linhaAtual + 1, colunaAtual);
                        Node noBaixo = new Node(baixo);
                        fila.enqueue(noBaixo);

                        Position esquerda = new Position(linhaAtual, colunaAtual - 1);
                        Node noEsquerda = new Node(esquerda);
                        fila.enqueue(noEsquerda);

                        Position direita = new Position(linhaAtual, colunaAtual + 1);
                        Node noDireita = new Node(direita);
                        fila.enqueue(noDireita);
                    }

                    try {
                        imagem.salvarImagem("saida.png");
                        System.out.println("Imagem salva como saida.png");
                    } catch (IOException e) {
                        System.out.println("Erro ao salvar imagem: " + e.getMessage());
                    }

                    break;

                case 3:
                    System.out.print("Caminho imagem:");
                    scanner.nextLine();
                    String caminho = scanner.nextLine();

                    try {
                        imagem.abrirImagem(caminho);
                        imagemCarregada = true;
                        System.out.println("Imagem carregada");
                    } catch (IOException e) {
                        System.out.println("Erro na imagem:" + e.getMessage());
                    }
                    break;
                case 4:

                    System.out.print("Digite a linha: ");
                    linha = scanner.nextInt();

                    System.out.print("Digite a coluna: ");
                    coluna = scanner.nextInt();

                    System.out.println( "Coordenada escolhida: ("+ linha+ ", "+ coluna+ ")");

                    break;

                case 0:

                    System.out.println("Encerrando programa.");

                    break;

                default:

                    System.out.println("Opcao invalida.");

                    break;
            }
        }

        scanner.close();
    } 
}



