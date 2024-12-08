package backend.academy.transforms;

import static java.lang.Math.cos;
import static java.lang.Math.pow;
import static java.lang.Math.sin;

public class SwirlTransformer implements Transformer {

    @Override
    public double applyX(double x, double y) {
        double sum = pow(x, 2) + pow(y, 2);
        return x * sin(sum) - y * cos(sum);
    }

    @Override
    public double applyY(double x, double y) {
        double sum = pow(x, 2) + pow(y, 2);
        return x * cos(sum) + y * sin(sum);
    }
}
