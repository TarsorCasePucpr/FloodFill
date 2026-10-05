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
    public static void main(String[] args){
        Scanner scanner = new Scanner(System.in);
        while(){
            System.out.print("Menu");
            System.out.print("1.- Excutar com pilha");
            System.out.print("2.- Excutar com Fila");
            System.out.print("3.- Escolher imagem");
            System.out.print("4.- Escolher coordenada de inicio: ");
            System.out.println("0.- Encerrar");
            System.out.print("Opcao: ");
            int value = scanner.nextInt();
            int x;
            int y;
            switch(value):
                case 1:


                    break;
                case 2:


                    break;
                case 3:

                    break;
                case 4:
                    System.out.print("Digite o valor de x");
                    x = scanner.nextInt();
                    System.out.print("Digite o valor de y");
                    y = scanner.nextInt();
                    //tem que voltar para menu
                    break;
                case 0:

                    break;
    }
}



