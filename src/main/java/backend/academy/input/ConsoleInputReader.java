package backend.academy.input;

import backend.academy.entities.ImageParams;
import backend.academy.transforms.TransformManager;
import backend.academy.transforms.Transformer;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Pattern;

public class ConsoleInputReader implements InputReader {

    private Scanner scanner;
    private static String digitPatternString = "\\d+";

    public ConsoleInputReader(Scanner scanner) {
        this.scanner = scanner;
    }

    @Override
    public ImageParams getImageParams() {
        int height;
        int width;

        String input = scanner.nextLine();

        if (!Pattern.matches("\\d+ \\d+", input)) {
            return new ImageParams(ImageParams.DEFAULT_HEIGHT, ImageParams.DEFAULT_WIDTH);
        } else {
            String[] strings = input.split(" ");
            try {
                width = Math.max(ImageParams.MIN_WIDTH, Math.min(Integer.parseInt(strings[0]),
                    ImageParams.MAX_WIDTH));
                height = Math.max(ImageParams.MIN_HEIGHT, Math.min(Integer.parseInt(strings[1]),
                    ImageParams.MAX_HEIGHT));
            } catch (NumberFormatException e) {
                return new ImageParams(ImageParams.DEFAULT_HEIGHT, ImageParams.DEFAULT_WIDTH);
            }
        }
        return new ImageParams(height, width);
    }

    @Override
    public int getIterationCount() {
        int iterations;

        String input = scanner.nextLine();

        if (!Pattern.matches(digitPatternString, input)) {
            return ImageParams.DEFAULT_ITERATIONS;
        } else {
            try {
                iterations = Math.max(1, Integer.parseInt(input));
            } catch (NumberFormatException e) {
                return ImageParams.DEFAULT_ITERATIONS;
            }
        }
        return iterations;
    }

    @Override
    public int getSymmetricNumber() {
        int symmetry;

        String input = scanner.nextLine();

        if (!Pattern.matches(digitPatternString, input)) {
            return ImageParams.DEFAULT_SYMMETRY;
        } else {
            try {
                symmetry = Math.max(1, Integer.parseInt(input));
            } catch (NumberFormatException e) {
                return ImageParams.DEFAULT_SYMMETRY;
            }
        }
        return symmetry;
    }

    @Override
    public List<Transformer> getTransformerList(ImageParams imageParams) {
        List<Transformer> transformerList = new ArrayList<>();
        TransformManager manager = new TransformManager(imageParams);

        String input = scanner.nextLine();
        String[] parts = input.split(" ");
        for (String part : parts) {
            if (!Pattern.matches(digitPatternString, part)) {
                continue;
            }
            try {
                Transformer transformer = manager.getTransformerByInt(Integer.parseInt(part));
                if (transformer != null) {
                    transformerList.add(transformer);
                }

            } catch (Exception e) {
            }
        }
        return transformerList;
    }
}
