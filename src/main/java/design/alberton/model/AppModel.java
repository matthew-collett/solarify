package design.alberton.model;

import java.util.List;

public interface AppModel {

    void setSeed(final int seed);

    int getSeed();

    void setMapping(final List<String> mapping);

    List<String> getMapping();



}
