package backend.academy.generateFractalFlame;

import backend.academy.config.Config;
import backend.academy.entities.Coeff;
import backend.academy.entities.Pixel;
import backend.academy.transforms.Transformer;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

public class MultiThreadFrameGenerator implements FlameGenerator {

    private double xMin;
    private double xMax;
    private double yMin;
    private double yMax;
    private final Random random;
    private int threadCount;
    private final int initialIterations = -20;
    private int eqCount;
    private int it;
    private Coeff[] coeff;
    private AtomicInteger completedTasks = new AtomicInteger(0);

    public MultiThreadFrameGenerator(Random random, int threadCount) {
        this.random = random;
        this.threadCount = threadCount;
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
        eqCount = config.eqCount();
        it = config.iterations();
        int xRes = config.imageParams().width();
        int yRes = config.imageParams().height();
        int symmetry = config.symmetry();

        yMin = -1.0;
        yMax = 1.0;
        xMin = -((double) xRes) / yRes;
        xMax = ((double) xRes) / yRes;

        coeff = new Coeff[eqCount];
        Pixel[][] pixels = new Pixel[xRes][yRes];
        for (int i = 0; i < xRes; i++) {
            for (int j = 0; j < yRes; j++) {
                pixels[i][j] = new Pixel();
            }
        }

        for (int i = 0; i < eqCount; i++) {
            coeff[i] = new Coeff(random);
        }

        ExecutorService executor = Executors.newFixedThreadPool(threadCount);

        for (int threadIndex = 0; threadIndex < threadCount; threadIndex++) {
            int pointsPerThread = n / threadCount;
            int startPoint = threadIndex * pointsPerThread;
            int endPoint = (threadIndex == threadCount - 1) ? n : startPoint + pointsPerThread;

            executor.submit(() -> threadTask(startPoint, endPoint, config, pixels));
        }

        executor.shutdown();
        while (!executor.isTerminated()) {
        }

        return SymmetryCorrector.applySymmetry(pixels, xRes, yRes, symmetry, threadCount);
    }

    private void threadTask(int startPoint, int endPoint, Config config, Pixel[][] pixels) {
        Random threadRandom = new Random(random.nextLong());
        for (int num = startPoint; num < endPoint; num++) {
            double newX = threadRandom.nextDouble(xMin, xMax);
            double newY = threadRandom.nextDouble(yMin, yMax);
            for (int step = initialIterations; step < it; step++) {
                int i = threadRandom.nextInt(0, eqCount);
                double x = coeff[i].a() * newX + coeff[i].b() * newY + coeff[i].c();
                double y = coeff[i].d() * newX + coeff[i].e() * newY + coeff[i].f();
                var transforms = chooseTransform(config.transforms());
                newX = transforms.applyX(x, y);
                newY = transforms.applyY(x, y);
                if (step >= 0 && newX >= xMin && newX <= xMax && newY >= yMin && newY <= yMax) {
                    int x1 = config.imageParams().width() - truncate(((xMax - newX) / (xMax - xMin))
                        * config.imageParams().width());
                    int y1 = config.imageParams().height() - truncate(((yMax - newY) / (yMax - yMin))
                        * config.imageParams().height());
                    if (x1 < config.imageParams().width() && y1 < config.imageParams().height()) {
                        synchronized (pixels[x1][y1]) {
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
                }
                newX = x;
                newY = y;
            }
        }
        completedTasks.incrementAndGet();
    }
}
