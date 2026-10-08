import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class ImageService {

    private BufferedImage original;
    private BufferedImage imagem;

    public void abrirImagem(String caminho) throws IOException {
        File arquivo = new File(caminho);

        if (!arquivo.exists()) {
            throw new IOException("Arquivo nao encontrado: " + caminho);
        }
        if (!arquivo.isFile()) {
            throw new IOException("O caminho nao e um arquivo: " + caminho);
        }

        BufferedImage lida = ImageIO.read(arquivo);
        if (lida == null) {
            throw new IOException("Formato de imagem nao suportado (use PNG, BMP ou JPG).");
        }

        original = copiar(lida);
        imagem = copiar(lida);
    }

    public boolean temImagem() {
        return imagem != null;
    }

    // Volta a imagem de trabalho ao estado original, para executar pilha e fila sobre a mesma imagem.
    public void restaurar() {
        imagem = copiar(original);
    }

    public int getPixel(int linha, int coluna) {
        return imagem.getRGB(coluna, linha);
        //o tipo BufferedImage inverte a ordem dos parametros, por isso esta inversão, mas é apenas a forma que ele trabalha
    }

    public void setPixel(int linha, int coluna, int cor) {
        imagem.setRGB(coluna, linha, cor);
    }

    public int getAltura() {
        return imagem.getHeight();
    }

    public int getLargura() {
        return imagem.getWidth();
    }

    public void salvarImagem(String caminho) throws IOException {
        ImageIO.write(imagem, "png", new File(caminho));
    }

    // Salva a etapa como <pasta>/passo_0001.png, passo_0002.png, ...
    public void salvarPasso(String pasta, int numero) throws IOException {
        salvarImagem(pasta + File.separator + String.format("passo_%04d.png", numero));
    }

    // Cria a pasta de saida e remove etapas antigas (passo_*.png) para nao misturar execucoes.
    public void prepararPasta(String pasta) throws IOException {
        File dir = new File(pasta);
        if (!dir.exists() && !dir.mkdirs()) {
            throw new IOException("Nao foi possivel criar a pasta: " + pasta);
        }
        File[] antigos = dir.listFiles((d, nome) -> nome.matches("passo_\\d+\\.png"));
        if (antigos != null) {
            for (File f : antigos) {
                f.delete();
            }
        }
    }

    private BufferedImage copiar(BufferedImage origem) {
        BufferedImage copia = new BufferedImage(origem.getWidth(), origem.getHeight(), BufferedImage.TYPE_INT_ARGB);
        for (int y = 0; y < origem.getHeight(); y++) {
            for (int x = 0; x < origem.getWidth(); x++) {
                copia.setRGB(x, y, origem.getRGB(x, y));
            }
        }
        return copia;
    }
}
