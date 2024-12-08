package backend.academy.painter;

import backend.academy.entities.Pixel;
import backend.academy.exceptions.ImageSaveException;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;
import lombok.experimental.UtilityClass;

@UtilityClass
public class Painter {

    private static final int RED_BITS = 16;
    private static final int GREEN_BITS = 8;

    public static void saveImage(Pixel[][] pixels, int xRes, int yRes, String filename) throws ImageSaveException {
        BufferedImage image = new BufferedImage(xRes, yRes, BufferedImage.TYPE_INT_RGB);

        for (int x = 0; x < xRes; x++) {
            for (int y = 0; y < yRes; y++) {
                Pixel pixel = pixels[x][y];
                int color = (pixel.red() << RED_BITS) | (pixel.green() << GREEN_BITS) | pixel.blue();
                image.setRGB(x, y, color);
            }
        }

        try {
            ImageIO.write(image, "png", new File(filename));
        } catch (Exception e) {
            throw new ImageSaveException("Не удалось созхранить изображение");
        }
    }

}
