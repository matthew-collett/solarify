package design.alberton.model;

public class AppModelImpl implements AppModel {

    private Integer seed = 0;

    @Override
    public void setSeed(Integer seed) {
        this.seed = seed;
    }

    @Override
    public Integer getSeed() {
        return seed;
    }
}
