package backend.academy.input;

import backend.academy.entities.ImageParams;
import backend.academy.transforms.Transformer;
import java.util.List;

public interface InputReader {

    ImageParams getImageParams();

    int getIterationCount();

    int getSymmetricNumber();

    List<Transformer> getTransformerList(ImageParams imageParams);

}
