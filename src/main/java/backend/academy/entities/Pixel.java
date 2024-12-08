package backend.academy.entities;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Pixel {
    private int red;
    private int green;
    private int blue;
    private int counter = 0;
    private double normal;
}
