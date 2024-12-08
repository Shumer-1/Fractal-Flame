package backend.academy.transforms;

import static java.lang.Math.PI;
import static java.lang.Math.atan;
import static java.lang.Math.cos;
import static java.lang.Math.min;
import static java.lang.Math.pow;
import static java.lang.Math.sin;
import static java.lang.Math.sqrt;

public class DiscTransformer implements Transformer {

    private final double height;
    private final double width = 1.0;

    public DiscTransformer(double height, double width) {
        this.height = height / width;
    }

    private double scale() {
        return min(height, width) / 2;
    }

    @Override
    public double applyX(double x, double y) {
        return (1 / PI) * atan(y / x) * sin(PI * sqrt(pow(x, 2) + pow(y, 2))) / scale();
    }

    @Override
    public double applyY(double x, double y) {
        return (1 / PI) * atan(y / x) * cos(PI * sqrt(pow(x, 2) + pow(y, 2))) / scale();
    }
}
