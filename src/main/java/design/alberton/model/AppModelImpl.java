package design.alberton.model;

import java.util.List;

public class AppModelImpl implements AppModel {

    private int seed = -1;
    private List<String> mapping;

    @Override
    public int getSeed() {
        return seed;
    }

    @Override
    public void setSeed(int seed) {
        this.seed = seed;
    }

    @Override
    public List<String> getMapping() {
        return mapping;
    }

    @Override
    public void setMapping(List<String> mapping) {
        this.mapping = mapping;
    }
}
