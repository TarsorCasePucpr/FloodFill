import java.io.IOException;

public class FloodFill {

    private static final int MAX_QUADROS = 300;

    private final ImageService imagem;

    public FloodFill(ImageService imagem) {
        this.imagem = imagem;
    }

    // Retorna a quantidade de pixels pintados (0 se nada foi alterado).
    public int executarComPilha(int linha, int coluna, int novaCor, String pasta) throws IOException {
        return executar(true, linha, coluna, novaCor, pasta);
    }

    public int executarComFila(int linha, int coluna, int novaCor, String pasta) throws IOException {
        return executar(false, linha, coluna, novaCor, pasta);
    }

    private int executar(boolean usarPilha, int linha, int coluna, int novaCor, String pasta) throws IOException {
        if (!imagem.temImagem()) {
            throw new IllegalStateException("Nenhuma imagem carregada. Use a opcao 3 primeiro.");
        }
        if (linha < 0 || linha >= imagem.getAltura() || coluna < 0 || coluna >= imagem.getLargura()) {
            throw new IllegalArgumentException("Coordenada fora da imagem.");
        }

        imagem.restaurar();

        int corOriginal = imagem.getPixel(linha, coluna);
        if (corOriginal == novaCor) {
            return 0;
        }

        // Para imagens grandes salva um quadro a cada 'intervalo' pixels pintados.
        int intervalo = Math.max(1, imagem.getLargura() * imagem.getAltura() / MAX_QUADROS);

        imagem.prepararPasta(pasta);
        int numeroPasso = 1;
        imagem.salvarPasso(pasta, numeroPasso);

        Stack pilha = usarPilha ? new Stack() : null;
        Queue fila = usarPilha ? null : new Queue();
        inserir(pilha, fila, linha, coluna);

        int pintados = 0;
        boolean ultimoSalvo = false;

        while (!vazia(pilha, fila)) {
            Node atual = remover(pilha, fila);
            int linhaAtual = atual.pos.linha;
            int colunaAtual = atual.pos.coluna;

            if (linhaAtual < 0 || linhaAtual >= imagem.getAltura()
                    || colunaAtual < 0 || colunaAtual >= imagem.getLargura()) {
                continue;
            }

            if (imagem.getPixel(linhaAtual, colunaAtual) != corOriginal) {
                continue;
            }

            imagem.setPixel(linhaAtual, colunaAtual, novaCor);
            pintados++;

            ultimoSalvo = pintados % intervalo == 0;
            if (ultimoSalvo) {
                numeroPasso++;
                imagem.salvarPasso(pasta, numeroPasso);
            }

            inserir(pilha, fila, linhaAtual - 1, colunaAtual);
            inserir(pilha, fila, linhaAtual + 1, colunaAtual);
            inserir(pilha, fila, linhaAtual, colunaAtual - 1);
            inserir(pilha, fila, linhaAtual, colunaAtual + 1);
        }

        if (!ultimoSalvo) {
            numeroPasso++;
            imagem.salvarPasso(pasta, numeroPasso);
        }

        return pintados;
    }

    private void inserir(Stack pilha, Queue fila, int linha, int coluna) {
        Node no = new Node(new Position(linha, coluna));
        if (pilha != null) {
            pilha.push(no);
        } else {
            fila.enqueue(no);
        }
    }

    private Node remover(Stack pilha, Queue fila) {
        return pilha != null ? pilha.pop() : fila.dequeue();
    }

    private boolean vazia(Stack pilha, Queue fila) {
        return pilha != null ? pilha.isEmpty() : fila.isEmpty();
    }
}
