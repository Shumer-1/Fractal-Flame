package backend.academy.transforms;

import static java.lang.Math.PI;
import static java.lang.Math.atan;
import static java.lang.Math.pow;
import static java.lang.Math.sqrt;

public class PolarTransformer implements Transformer {

    @Override
    public double applyX(double x, double y) {
        return atan(y / x) / PI;
    }

    @Override
    public double applyY(double x, double y) {
        return sqrt(pow(x, 2) + pow(y, 2) - 1);
    }
}
