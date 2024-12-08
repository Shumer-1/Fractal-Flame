package inputTests;

import backend.academy.entities.ImageParams;
import backend.academy.input.ConsoleInputReader;
import backend.academy.transforms.DiscTransformer;
import backend.academy.transforms.ExponentialTransformer;
import backend.academy.transforms.HeartTransformer;
import backend.academy.transforms.PolarTransformer;
import backend.academy.transforms.Transformer;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Stream;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class ConsoleInputReaderTest {

    private static final Scanner MOCK_SCANNER = mock(Scanner.class);

    @ParameterizedTest
    @MethodSource("imageParamsProvider")
    void testGetImageParams(String input, ImageParams expectedParams) {
        when(MOCK_SCANNER.nextLine()).thenReturn(input);

        ConsoleInputReader reader = new ConsoleInputReader(MOCK_SCANNER);
        ImageParams result = reader.getImageParams();

        assertThat(expectedParams.width()).isEqualTo(result.width());
        assertThat(expectedParams.height()).isEqualTo(result.height());
    }

    @ParameterizedTest
    @MethodSource("iterationCountProvider")
    void testGetIterationCount(String input, int expectedIterations) {
        when(MOCK_SCANNER.nextLine()).thenReturn(input);

        ConsoleInputReader reader = new ConsoleInputReader(MOCK_SCANNER);
        int result = reader.getIterationCount();

        assertThat(expectedIterations).isEqualTo(result);
    }

    @ParameterizedTest
    @MethodSource("symmetricNumberProvider")
    void testGetSymmetricNumber(String input, int expectedSymmetry) {
        when(MOCK_SCANNER.nextLine()).thenReturn(input);

        ConsoleInputReader reader = new ConsoleInputReader(MOCK_SCANNER);
        int result = reader.getSymmetricNumber();

        assertThat(expectedSymmetry).isEqualTo(result);
    }

    @ParameterizedTest
    @MethodSource("transformerListProvider")
    void testGetTransformerList(String input, List<Transformer> expectedTransformers) {
        when(MOCK_SCANNER.nextLine()).thenReturn(input);

        ConsoleInputReader reader = new ConsoleInputReader(MOCK_SCANNER);
        ImageParams imageParams = new ImageParams(100, 100);
        List<Transformer> result = reader.getTransformerList(imageParams);

        assertThat(expectedTransformers).hasSize(result.size());
        for (int i = 0; i < result.size(); i++) {
            assertThat(result.get(i).getClass()).isEqualTo(expectedTransformers.get(i).getClass());
        }
    }

    static Stream<Arguments> imageParamsProvider() {
        return Stream.of(
            Arguments.of("800 600", new ImageParams(600, 800)),
            Arguments.of("-1000 -1000", new ImageParams(ImageParams.DEFAULT_HEIGHT, ImageParams.DEFAULT_WIDTH)),
            Arguments.of("20000 800", new ImageParams(800, ImageParams.DEFAULT_WIDTH)),
            Arguments.of("1000 abc", new ImageParams(ImageParams.DEFAULT_HEIGHT, ImageParams.DEFAULT_WIDTH)),
            Arguments.of("", new ImageParams(ImageParams.DEFAULT_HEIGHT, ImageParams.DEFAULT_WIDTH))
        );
    }

    static Stream<Arguments> iterationCountProvider() {
        return Stream.of(
            Arguments.of("100", 100),
            Arguments.of("abc", ImageParams.DEFAULT_ITERATIONS),
            Arguments.of("0", 1),
            Arguments.of("", ImageParams.DEFAULT_ITERATIONS)
        );
    }

    static Stream<Arguments> symmetricNumberProvider() {
        return Stream.of(
            Arguments.of("4", 4),
            Arguments.of("8", 8),
            Arguments.of("abc", ImageParams.DEFAULT_SYMMETRY),
            Arguments.of("0", 1),
            Arguments.of("", ImageParams.DEFAULT_SYMMETRY)
        );
    }

    static Stream<Arguments> transformerListProvider() {
        return Stream.of(
            Arguments.of("1 2", List.of(new DiscTransformer(1, 1), new ExponentialTransformer())),
            Arguments.of("3 4", List.of(new HeartTransformer(1, 1), new PolarTransformer())),
            Arguments.of("1 2 abc", List.of(new DiscTransformer(1, 1), new ExponentialTransformer())),
            Arguments.of("", List.of()),
            Arguments.of("89 9121 11", List.of())
        );
    }
}

