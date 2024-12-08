package backend.academy.transforms;

import static java.lang.Math.PI;
import static java.lang.Math.cos;
import static java.lang.Math.exp;
import static java.lang.Math.sin;

public class ExponentialTransformer implements Transformer {

    @Override
    public double applyX(double x, double y) {
        return exp((x - 1)) * cos(PI * y);
    }

    @Override
    public double applyY(double x, double y) {
        return exp(x - 1) * sin(PI * y);
    }
}

