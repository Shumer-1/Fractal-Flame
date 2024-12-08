package backend.academy.transforms;

import static java.lang.Math.atan;
import static java.lang.Math.cos;
import static java.lang.Math.min;
import static java.lang.Math.pow;
import static java.lang.Math.sin;
import static java.lang.Math.sqrt;

public class HeartTransformer implements Transformer {

    private final double height;
    private final double width = 1.0;

    public HeartTransformer(double height, double width) {
        this.height = height / width;
    }

    private double scale() {
        return min(height, width) / 2;
    }

    @Override
    public double applyX(double x, double y) {
        double sum = pow(x, 2) + pow(y, 2);
        return sqrt(sum) * sin(sqrt(sum) * atan(y / x)) * scale();
    }

    @Override
    public double applyY(double x, double y) {
        double sum = pow(x, 2) + pow(y, 2);
        return -sqrt(sum) * cos(sqrt(sum) * atan(y / x)) * scale();
    }
}
