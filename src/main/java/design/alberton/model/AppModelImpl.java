package design.alberton.model;

import java.util.List;

public class AppModelImpl implements AppModel {

    private String seed = "";
    private List<String> mapping;

    @Override
    public String getSeed() {
        return seed;
    }

    @Override
    public void setSeed(String seed) {
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
