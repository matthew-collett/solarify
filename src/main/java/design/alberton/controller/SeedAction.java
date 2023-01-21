package design.alberton.controller;

import design.alberton.model.AppModel;
import design.alberton.view.ResultView;
import design.alberton.view.SeedView;
import design.alberton.view.SolarApp;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import static java.lang.Integer.parseInt;

public class SeedAction implements ActionListener {

    private final SolarApp app;

    private final JTextField seedField;

    public SeedAction(final SolarApp app, final JTextField seedField) {
        this.app = app;
        this.seedField = seedField;
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        final AppModel model = app.getModel();
        final String seed = seedField.getText();

        if (parseInt(seed) == 4) {
            model.setSeed(parseInt(seed));
            app.setView(new ResultView(app));

        } else {
            model.setSeed(null);
            app.setView(new SeedView(app));
        }

        System.out.println(model.getSeed());

    }
}
