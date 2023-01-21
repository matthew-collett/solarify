package design.alberton.view.helper;

import javax.swing.*;

import java.awt.*;
import java.net.URL;

import static java.awt.Image.SCALE_DEFAULT;
import static java.lang.String.format;
import static java.util.Objects.nonNull;

public class IconHelper {

    public static ImageIcon getIcon(final Class<?> source, final String path, final int width, final int height) {
        final URL url = source.getResource(path);
        if(nonNull(url)) {
            final Image img = new ImageIcon(url).getImage().getScaledInstance(width, height, SCALE_DEFAULT);
            return new ImageIcon(img);
        } else {
            throw new IllegalArgumentException(format("Invalid icon path: %s", path));
        }
    }
}
