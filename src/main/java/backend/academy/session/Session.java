package backend.academy.session;

import backend.academy.config.Config;
import backend.academy.entities.ImageParams;
import backend.academy.entities.Pixel;
import backend.academy.exceptions.ImageSaveException;
import backend.academy.generateFractalFlame.FlameGenerator;
import backend.academy.generateFractalFlame.ImageCorrector;
import backend.academy.input.InputReader;
import backend.academy.painter.Painter;
import backend.academy.transforms.Transformer;
import backend.academy.writer.Writer;
import java.util.List;

public class Session {

    private FlameGenerator flameGenerator;
    private Writer writer;
    private InputReader inputReader;

    public Session(
        FlameGenerator flameGenerator, Writer writer,
        InputReader inputReader
    ) {
        this.flameGenerator = flameGenerator;
        this.writer = writer;
        this.inputReader = inputReader;
    }

    public void startSession() {
        writer.printStartInfo();

        ImageParams imageParams = inputReader.getImageParams();
        writer.print("Параметры изображения: " + imageParams.width() + " " + imageParams.height());
        int iterations = inputReader.getIterationCount();
        writer.print("Количество итераций: " + iterations);
        int symmetry = inputReader.getSymmetricNumber();
        writer.print("Показатели симметрии: " + symmetry);
        List<Transformer> transformerList = inputReader.getTransformerList(imageParams);
        while (transformerList.isEmpty()) {
            writer.printFailedInputParams();
            transformerList = inputReader.getTransformerList(imageParams);
        }
        writer.printStartRendering();

        Config config = new Config(ImageParams.POINT_COUNT, ImageParams.AFFINE_COUNT, iterations,
            imageParams, symmetry, transformerList);

        long startTime = System.currentTimeMillis();
        Pixel[][] pixels = flameGenerator.render(config);
        writer.print("Время рендеринга: " + (System.currentTimeMillis() - startTime));
        ImageCorrector.correction(imageParams.width(), imageParams.height(), pixels);

        try {
            Painter.saveImage(pixels, imageParams.width(), imageParams.height(), "results/output.png");
        } catch (ImageSaveException e) {
            writer.print(e.getMessage());
        }
    }
}
