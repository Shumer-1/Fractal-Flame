package transformsTests;

import backend.academy.transforms.ExponentialTransformer;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import static java.lang.Math.PI;
import static java.lang.Math.cos;
import static java.lang.Math.exp;
import static java.lang.Math.sin;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.within;
import org.junit.jupiter.params.provider.Arguments;

import java.util.stream.Stream;

class ExponentialTransformerTest {

    @ParameterizedTest
    @MethodSource("provideTestDataForApplyX")
    void testApplyX(double x, double y) {
        ExponentialTransformer transformer = new ExponentialTransformer();
        double expectedX = exp(x - 1) * cos(PI * y);
        double resultX = transformer.applyX(x, y);

        assertThat(resultX).isCloseTo(expectedX, within(1e-6));
    }

    @ParameterizedTest
    @MethodSource("provideTestDataForApplyY")
    void testApplyY(double x, double y) {
        ExponentialTransformer transformer = new ExponentialTransformer();
        double expectedY = exp(x - 1) * sin(PI * y);
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
