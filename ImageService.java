import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class ImageService {

    private BufferedImage imagem;

    public void abrirImagem(String caminho) throws IOException {
        imagem = ImageIO.read(new File(caminho));

        if (imagem == null) {
            throw new IOException("Não foi possível abrir a imagem.");
        }
    }

    public int getPixel(int linha, int coluna) {
        return imagem.getRGB(coluna, linha);
        //o tipo BufferedImage inverte a ordem dos parametros, por isso esta inversão, mas é apenas a forma que ele trabalha
    }

    public void setPixel(int linha, int coluna, int cor) {
        imagem.setRGB(coluna, linha, cor);
    }

    public void salvarImagem(String caminho) throws IOException {
        ImageIO.write(imagem, "png", new File(caminho));
    }
}
