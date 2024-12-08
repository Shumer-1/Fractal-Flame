package backend.academy.transforms;

import static java.lang.Math.sin;

public class SinusoidalTransformer implements Transformer {

    private final double height;
    private final double width = 1.0;

    public SinusoidalTransformer(double height, double width) {
        this.height = height / width;
    }

    private double scale() {
        return this.width / this.height;
    }

    @Override
    public double applyX(double x, double y) {
        return sin(x) * scale();
    }

    @Override
    public double applyY(double x, double y) {
        return sin(y);
    }
}
