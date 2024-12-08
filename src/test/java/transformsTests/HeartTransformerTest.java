package transformsTests;

import backend.academy.transforms.HeartTransformer;
import java.util.stream.Stream;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import static java.lang.Math.atan;
import static java.lang.Math.cos;
import static java.lang.Math.min;
import static java.lang.Math.pow;
import static java.lang.Math.sin;
import static java.lang.Math.sqrt;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.within;

class HeartTransformerTest {

    @ParameterizedTest
    @MethodSource("provideTestDataForApplyX")
    void testApplyX(double x, double y, double height) {
        HeartTransformer transformer = new HeartTransformer(height, 1.0);
        double scale = min(height, 1.0) / 2;
        double sum = pow(x, 2) + pow(y, 2);
        double expectedX = sqrt(sum) * sin(sqrt(sum) * atan(y / x)) * scale;
        double resultX = transformer.applyX(x, y);

        assertThat(resultX).isCloseTo(expectedX, within(1e-6));
    }

    @ParameterizedTest
    @MethodSource("provideTestDataForApplyY")
    void testApplyY(double x, double y, double height) {
        HeartTransformer transformer = new HeartTransformer(height, 1.0);
        double scale = min(height, 1.0) / 2;
        double sum = pow(x, 2) + pow(y, 2);
        double expectedY = -sqrt(sum) * cos(sqrt(sum) * atan(y / x)) * scale;
        double resultY = transformer.applyY(x, y);

        assertThat(resultY).isCloseTo(expectedY, within(1e-6));
    }

    static Stream<Arguments> provideTestDataForApplyX() {
        return Stream.of(
            Arguments.of(2.0, 1.0, 1.0),
            Arguments.of(1.0, 0.5, 1.0),
            Arguments.of(0.0, 0.0, 1.0),
            Arguments.of(1.0, 0.0, 2.0),
            Arguments.of(2.0, 1.0, 2.0)
        );
    }

    static Stream<Arguments> provideTestDataForApplyY() {
        return Stream.of(
            Arguments.of(2.0, 1.0, 1.0),
            Arguments.of(1.0, 0.5, 1.0),
            Arguments.of(0.0, 0.0, 1.0),
            Arguments.of(1.0, 0.0, 2.0),
            Arguments.of(2.0, 1.0, 2.0)
        );
    }
}
