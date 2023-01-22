package design.alberton.view;

import design.alberton.model.AppModel;
import design.alberton.model.AppModelImpl;

import javax.swing.*;
import java.awt.*;
import java.net.URL;

import static java.util.Objects.nonNull;

public class
SolarAppImpl extends SolarApp {

    private final AppModel model;

    public SolarAppImpl() {
        super("Solarify");

        model = new AppModelImpl();

        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);

        setView(new SeedView(this));

        final URL url = getClass().getResource("/assets/icon.png");

        if (nonNull(url)) {
            setIconImage(new ImageIcon(url).getImage());
        }
    }

    public void start() {
        setMinimumSize(new Dimension(600, 0));
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }


    @Override
    public void setView(JPanel view) {
        setContentPane(view);
        pack();
        revalidate();
        repaint();
    }

    @Override
    public AppModel getModel() {
        return model;
    }
}
