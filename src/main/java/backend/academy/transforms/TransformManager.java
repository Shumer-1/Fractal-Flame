package backend.academy.transforms;

import backend.academy.entities.ImageParams;
import java.util.HashMap;
import java.util.Map;

public class TransformManager {

    private final ImageParams imageParams;
    private Map<Integer, Transformer> transformerMap;

    @SuppressWarnings("MagicNumber")
    public TransformManager(ImageParams imageParams) {
        this.imageParams = imageParams;
        this.transformerMap = new HashMap<>() {{
            put(1, new DiscTransformer(imageParams.height(), imageParams.width()));
            put(2, new ExponentialTransformer());
            put(3, new HeartTransformer(imageParams.height(), imageParams.width()));
            put(4, new PolarTransformer());
            put(5, new SinusoidalTransformer(imageParams.height(), imageParams.width()));
            put(6, new SwirlTransformer());
            put(7, new SphericalTransformer());
        }};
    }

    public Transformer getTransformerByInt(int numb) throws ClassCastException, NullPointerException {
        return transformerMap.get(numb);
    }

}
