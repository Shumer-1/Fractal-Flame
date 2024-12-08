package backend.academy.generateFractalFlame;

import backend.academy.config.Config;
import backend.academy.entities.Pixel;

public interface FlameGenerator {

    Pixel[][] render(Config config);
}
