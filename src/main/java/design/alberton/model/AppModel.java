package design.alberton.model;

import java.util.List;

public interface AppModel {

    void setSeed(final String seed);

    String getSeed();

    void setMapping(final List<String> mapping);

    List<String> getMapping();



}
