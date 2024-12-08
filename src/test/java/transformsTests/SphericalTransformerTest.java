package transformsTests;

import backend.academy.transforms.SphericalTransformer;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import static java.lang.Math.pow;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.within;
import org.junit.jupiter.params.provider.Arguments;
import java.util.stream.Stream;

class SphericalTransformerTest {

    @ParameterizedTest
    @MethodSource("provideTestDataForApplyX")
    void testApplyX(double x, double y) {
        SphericalTransformer transformer = new SphericalTransformer();
        double expectedX = x / (pow(x, 2) + pow(y, 2));
        double resultX = transformer.applyX(x, y);

        assertThat(resultX).isCloseTo(expectedX, within(1e-6));
    }

    @ParameterizedTest
    @MethodSource("provideTestDataForApplyY")
    void testApplyY(double x, double y) {
        SphericalTransformer transformer = new SphericalTransformer();
        double expectedY = y / (pow(x, 2) + pow(y, 2));
        double resultY = transformer.applyY(x, y);

        assertThat(resultY).isCloseTo(expectedY, within(1e-6));
    }

    static Stream<Arguments> provideTestDataForApplyX() {
        return Stream.of(
            Arguments.of(2.0, 1.0),
            Arguments.of(1.0, 0.5),
            Arguments.of(0.0, 0.0),
            Arguments.of(1.0, 0.0),
            Arguments.of(2.0, 1.0)
        );
    }

    static Stream<Arguments> provideTestDataForApplyY() {
        return Stream.of(
            Arguments.of(2.0, 1.0),
            Arguments.of(1.0, 0.5),
            Arguments.of(0.0, 0.0),
            Arguments.of(1.0, 0.0),
            Arguments.of(2.0, 1.0)
        );
    }
}
