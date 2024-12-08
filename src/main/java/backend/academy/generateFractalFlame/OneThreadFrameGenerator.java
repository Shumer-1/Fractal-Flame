package backend.academy.generateFractalFlame;

import backend.academy.config.Config;
import backend.academy.entities.Coeff;
import backend.academy.entities.Pixel;
import backend.academy.transforms.Transformer;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class OneThreadFrameGenerator implements FlameGenerator {

    private double xMin;
    private double xMax;
    private double yMin;
    private double yMax;
    private final Random random;
    private final int initialIterations = -20;

    public OneThreadFrameGenerator(Random random) {
        this.random = random;
    }

    private int truncate(double value) {
        return (int) Math.floor(value);
    }

    private Transformer chooseTransform(List<Transformer> transforms) {
        List<Integer> chooseList = new ArrayList<>();
        for (int i = 0; i < transforms.size(); i++) {
            chooseList.add(i);
        }
        int number = random.nextInt(chooseList.size());

        return transforms.get(chooseList.get(number));
    }

    public Pixel[][] render(Config config) {

        int n = config.points();
        int eqCount = config.eqCount();
        int it = config.iterations();
        int xRes = config.imageParams().width();
        int yRes = config.imageParams().height();
        int symmetric = config.symmetry();

        yMin = -1.0;
        yMax = 1.0;
        xMin = -((double) xRes) / yRes;
        xMax = ((double) xRes) / yRes;

        Coeff[] coeff = new Coeff[eqCount];
        Pixel[][] pixels = new Pixel[xRes][yRes];
        for (int i = 0; i < xRes; i++) {
            for (int j = 0; j < yRes; j++) {
                pixels[i][j] = new Pixel();
            }
        }

        for (int i = 0; i < eqCount; i++) {
            coeff[i] = new Coeff(random);
        }

        for (int num = 0; num < n; num++) {
            double newX = random.nextDouble(xMin, xMax);
            double newY = random.nextDouble(yMin, yMax);

            for (int step = initialIterations; step < it; step++) {
                int i = random.nextInt(0, eqCount);
                double x = coeff[i].a() * newX + coeff[i].b() * newY + coeff[i].c();
                double y = coeff[i].d() * newX + coeff[i].e() * newY + coeff[i].f();

                var transforms = chooseTransform(config.transforms());
                newX = transforms.applyX(x, y);
                newY = transforms.applyY(x, y);

                if (step >= 0 && newX >= xMin && newX <= xMax && newY >= yMin && newY <= yMax) {
                    int x1 = xRes - truncate(((xMax - newX) / (xMax - xMin)) * xRes);
                    int y1 = yRes - truncate(((yMax - newY) / (yMax - yMin)) * yRes);

                    if (x1 < xRes && y1 < yRes) {
                        Pixel pixel = pixels[x1][y1];

                        if (pixel.counter() == 0) {
                            pixel.red(coeff[i].red());
                            pixel.green(coeff[i].green());
                            pixel.blue(coeff[i].blue());
                        } else {
                            pixel.red((pixel.red() + coeff[i].red()) / 2);
                            pixel.green((pixel.green() + coeff[i].green()) / 2);
                            pixel.blue((pixel.blue() + coeff[i].blue()) / 2);
                        }
                        pixel.counter(pixel.counter() + 1);
                    }
                }

                newX = x;
                newY = y;
            }
        }
        return SymmetryCorrector.applySymmetry(pixels, xRes, yRes, symmetric, 1);
    }
}
