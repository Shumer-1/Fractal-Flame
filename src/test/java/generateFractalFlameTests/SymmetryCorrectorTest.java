package generateFractalFlameTests;

import backend.academy.entities.Pixel;
import backend.academy.generateFractalFlame.SymmetryCorrector;
import java.util.stream.Stream;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.fail;

class SymmetryCorrectorTest {

    @ParameterizedTest
    @MethodSource("providePixelArrays")
    void testSymmetry(Pixel[][] pixels, int xRes, int yRes, int symmetry, int threadCount) {
        Pixel[][] symmetricPixels = SymmetryCorrector.applySymmetry(pixels, xRes, yRes, symmetry, threadCount);
        double angleStep = 2 * Math.PI / symmetry;
        int centerX = xRes / 2;
        int centerY = yRes / 2;
        for (int x = 0; x < xRes; x++) {
            for (int y = 0; y < yRes; y++) {
                Pixel originalPixel = symmetricPixels[x][y];

                if (originalPixel.counter() > 0) {
                    for (int s = 1; s < symmetry; s++) {
                        double angle = s * angleStep;

                        int symX = (int) Math.round(
                            Math.cos(angle) * (x - centerX) - Math.sin(angle) * (y - centerY) + centerX
                        );
                        int symY = (int) Math.round(
                            Math.sin(angle) * (x - centerX) + Math.cos(angle) * (y - centerY) + centerY
                        );

                        if (symX >= 0 && symX < xRes && symY >= 0 && symY < yRes) {
                            Pixel symPixel = symmetricPixels[symX][symY];
                            assertThat(originalPixel.red()).isEqualTo(symPixel.red());
                            assertThat(originalPixel.red()).isEqualTo(symPixel.red());
                            assertThat(originalPixel.red()).isEqualTo(symPixel.red());
                            assertThat(originalPixel.red()).isEqualTo(symPixel.red());
                        } else {
                            fail("Вышли за границы");
                        }
                    }
                }
            }
        }
    }

    private static Stream<Arguments> providePixelArrays() {
        int xRes = 100;
        int yRes = 100;

        Pixel[][] pixels1 = new Pixel[xRes][yRes];
        for (int i = 0; i < xRes; i++) {
            for (int j = 0; j < yRes; j++) {
                pixels1[i][j] = new Pixel();
            }
        }
        int centerX = xRes / 2;
        int centerY = yRes / 2;
        pixels1[centerX][centerY].red(100);
        pixels1[centerX][centerY].green(150);
        pixels1[centerX][centerY].blue(200);
        pixels1[centerX][centerY].counter(1);

        Pixel[][] pixels2 = new Pixel[xRes][yRes];
        for (int i = 0; i < xRes; i++) {
            for (int j = 0; j < yRes; j++) {
                pixels2[i][j] = new Pixel();
            }
        }
        pixels2[20][30].red(50);
        pixels2[20][30].green(75);
        pixels2[20][30].blue(100);
        pixels2[20][30].counter(1);

        return Stream.of(
            Arguments.of(pixels1, xRes, yRes, 4, 4),
            Arguments.of(pixels1, xRes, yRes, 1, 1),
            Arguments.of(pixels1, xRes, yRes, 2, 1),
            Arguments.of(pixels2, xRes, yRes, 3, 12),
            Arguments.of(pixels2, xRes, yRes, 6, 1),
            Arguments.of(pixels2, xRes, yRes, 1, 12)
        );
    }
}
