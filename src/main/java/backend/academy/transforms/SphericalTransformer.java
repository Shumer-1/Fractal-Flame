package backend.academy.transforms;

import static java.lang.Math.pow;

public class SphericalTransformer implements Transformer {

    @Override
    public double applyX(double x, double y) {
        return x / (pow(x, 2) + pow(y, 2));
    }

    @Override
    public double applyY(double x, double y) {
        return y / (pow(x, 2) + pow(y, 2));
    }
}
