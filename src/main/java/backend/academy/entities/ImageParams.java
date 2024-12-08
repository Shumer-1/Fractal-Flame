package backend.academy.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ImageParams {

    public static final int DEFAULT_HEIGHT = 1080;
    public static final int DEFAULT_WIDTH = 1920;

    public static final int MAX_HEIGHT = 1920;
    public static final int MAX_WIDTH = 1920;
    public static final int MIN_HEIGHT = 400;
    public static final int MIN_WIDTH = 400;
    public static final int DEFAULT_ITERATIONS = 1500;
    public static final int DEFAULT_SYMMETRY = 1;
    public static final int POINT_COUNT = 200_000;
    public static final int AFFINE_COUNT = 8;

    private int height;
    private int width;

}
