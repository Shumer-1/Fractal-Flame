package backend.academy.config;

import backend.academy.entities.ImageParams;
import backend.academy.transforms.Transformer;
import java.util.List;

public record Config(int points, int eqCount, int iterations, ImageParams imageParams,
                     int symmetry, List<Transformer> transforms) {
}
