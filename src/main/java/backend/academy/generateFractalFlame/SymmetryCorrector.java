package backend.academy.generateFractalFlame;

import backend.academy.entities.Pixel;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import lombok.experimental.UtilityClass;

@UtilityClass
@SuppressWarnings("NestedForDepth")
public class SymmetryCorrector {
    public Pixel[][] applySymmetry(Pixel[][] pixels, int xRes, int yRes, int symmetry, int threadCount) {
        Pixel[][] symmetricPixels = new Pixel[xRes][yRes];
        for (int i = 0; i < xRes; i++) {
            for (int j = 0; j < yRes; j++) {
                symmetricPixels[i][j] = new Pixel();
            }
        }

        double angleStep = 2 * Math.PI / symmetry;
        double centerX = xRes / 2.0;
        double centerY = yRes / 2.0;

        ExecutorService executor = Executors.newFixedThreadPool(threadCount);

        for (int threadIndex = 0; threadIndex < threadCount; threadIndex++) {
            int startRow = threadIndex * yRes / threadCount;
            int endRow = (threadIndex == threadCount - 1) ? yRes : (threadIndex + 1) * yRes / threadCount;

            executor.submit(() -> {
                for (int x = 0; x < xRes; x++) {
                    for (int y = startRow; y < endRow; y++) {
                        Pixel originalPixel = pixels[x][y];
                        if (originalPixel.counter() == 0) {
                            continue;
                        }

                        for (int s = 0; s < symmetry; s++) {
                            double angle = s * angleStep;

                            int symX = (int) Math.round(
                                Math.cos(angle) * (x - centerX) - Math.sin(angle) * (y - centerY) + centerX
                            );
                            int symY = (int) Math.round(
                                Math.sin(angle) * (x - centerX) + Math.cos(angle) * (y - centerY) + centerY
                            );

                            if (symX >= 0 && symX < xRes && symY >= 0 && symY < yRes) {
                                synchronized (symmetricPixels[symX][symY]) {
                                    Pixel symPixel = symmetricPixels[symX][symY];

                                    symPixel.red((symPixel.red() + originalPixel.red()) / 2);
                                    symPixel.green((symPixel.green() + originalPixel.green()) / 2);
                                    symPixel.blue((symPixel.blue() + originalPixel.blue()) / 2);
                                    symPixel.counter(symPixel.counter() + originalPixel.counter());
                                }
                            }
                        }
                    }
                }
            });
        }

        executor.shutdown();
        while (!executor.isTerminated()) {
        }
        return symmetricPixels;
    }
}
