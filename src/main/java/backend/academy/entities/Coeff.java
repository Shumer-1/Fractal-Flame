package backend.academy.entities;

import java.util.Random;
import lombok.Getter;
import static java.lang.Math.pow;

@Getter
public class Coeff {
    private double a;
    private double b;
    private double c;
    private double d;
    private double e;
    private double f;
    private int red;
    private int green;
    private int blue;

    private static final int MAX_COLOR = 256;

    public Coeff(Random random) {
        do {
            a = random.nextDouble(-1, 1);
            b = random.nextDouble(-1, 1);
            c = random.nextDouble(-1, 1);
            d = random.nextDouble(-1, 1);
            e = random.nextDouble(-1, 1);
            f = random.nextDouble(-1, 1);
            red = random.nextInt(0, MAX_COLOR);
            green = random.nextInt(0, MAX_COLOR);
            blue = random.nextInt(0, MAX_COLOR);
        }
        while (pow(a, 2) + pow(d, 2) < 1 && pow(b, 2) + pow(e, 2) < 1
            && pow(a, 2) + pow(b, 2) + pow(d, 2) + pow(e, 2) < 1 + pow(a * e - b * d, 2));
    }
}
