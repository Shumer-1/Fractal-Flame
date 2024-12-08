package backend.academy.generateFractalFlame;

import backend.academy.entities.Pixel;
import lombok.experimental.UtilityClass;

@UtilityClass
public class ImageCorrector {

    private static final double GAMMA = 2.2;

    public static void correction(int xRes, int yRes, Pixel[][] pixels) {
        double max = 0.0;

        for (int row = 0; row < xRes; row++) {
            for (int col = 0; col < yRes; col++) {
                if (pixels[row][col].counter() != 0) {
                    pixels[row][col].normal(Math.log10(pixels[row][col].counter()));
                    if (pixels[row][col].normal() > max) {
                        max = pixels[row][col].normal();
                    }
                }
            }
        }

        for (int row = 0; row < xRes; row++) {
            for (int col = 0; col < yRes; col++) {
                pixels[row][col].normal(pixels[row][col].normal() / max);
                pixels[row][col].red(
                    (int) (pixels[row][col].red() * Math.pow(pixels[row][col].normal(), (1.0 / GAMMA))));
                pixels[row][col].green(
                    (int) (pixels[row][col].green() * Math.pow(pixels[row][col].normal(), (1.0 / GAMMA))));
                pixels[row][col].blue(
                    (int) (pixels[row][col].blue() * Math.pow(pixels[row][col].normal(), (1.0 / GAMMA))));
            }
        }
    }
}
