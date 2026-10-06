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

        int value = -1;
        int linha = 0;
        int coluna = 0;

        while (value != 0) {
            System.out.println("===== MENU FLOOD FILL =====");
            System.out.println("1 - Executar com pilha");
            System.out.println("2 - Executar com fila");
            System.out.println("3 - Escolher imagem");
            System.out.println("4 - Escolher coordenada de inicio");
            System.out.println("0 - Encerrar");
            System.out.print("Opcao: ");

            value = scanner.nextInt();

            switch (value) {

                case 1:
                    Stack pilha = new Stack();

                    Position inicio = new Position(linha, coluna);
                    Node noInicial = new Node(inicio);

                    pilha.push(noInicial);

                    System.out.println("Posicao inicial adicionada na pilha.");

                    while (!pilha.isEmpty()) {

                        Node atual = pilha.pop();

                        int linhaAtual = atual.pos.linha;
                        int colunaAtual = atual.pos.coluna;

                        System.out.println(
                            "Processando: linha "
                            + linhaAtual
                            + ", coluna "
                            + colunaAtual
                        );

                        // A fazer:
                        // Verificar limites da imagem
                        // Verificar cor original
                        // Alterar cor
                        // Adicionar os 4 vizinhos
                    }

                    break;

                case 2:

                    // A fazer - Flood Fill com fila

                    break;

                case 3:

                    // A fazer - Escolher imagem

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



