package painterTests;

import backend.academy.entities.Pixel;
import backend.academy.painter.Painter;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.stream.Stream;
import javax.imageio.ImageIO;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.fail;

class PainterTests {

    @ParameterizedTest
    @MethodSource("imageDataProvider")
    void testSaveImage(int xRes, int yRes, String filename, Pixel[][] pixels) {
        try {
            Painter.saveImage(pixels, xRes, yRes, filename);

            BufferedImage savedImage = ImageIO.read(new File(filename));
            assertNotNull(savedImage);

            for (int x = 0; x < xRes; x++) {
                for (int y = 0; y < yRes; y++) {
                    int expectedColor = (pixels[x][y].red() << 16) |
                        (pixels[x][y].green() << 8) | pixels[x][y].blue();
                    int actualColor = savedImage.getRGB(x, y) & 0xFFFFFF;  // убрать альфа канал
                    assertThat(expectedColor).isEqualTo(actualColor);
                }
            }
        } catch (Exception e) {
            fail("Ошибка при сохранении или проверке изображения: " + e.getMessage());
        } finally {
            new File(filename).delete();
        }
    }

    static Stream<Arguments> imageDataProvider() {
        return Stream.of(
            Arguments.of(10, 10, "src/test/resources/test_image_10.png", generatePixelArray(10, 10)),
            Arguments.of(20, 20, "src/test/resources/test_image_20.png", generatePixelArray(20, 20)),
            Arguments.of(5, 5, "src/test/resources/test_image_5.png", generatePixelArray(5, 5))
        );
    }

    private static Pixel[][] generatePixelArray(int xRes, int yRes) {
        Pixel[][] pixels = new Pixel[xRes][yRes];
        for (int x = 0; x < xRes; x++) {
            for (int y = 0; y < yRes; y++) {
                pixels[x][y] = new Pixel();
                pixels[x][y].red(x * 25 % 256);
                pixels[x][y].green(y * 25 % 256);
                pixels[x][y].blue((x + y) * 15 % 256);
            }
        }
        return pixels;
    }
}
