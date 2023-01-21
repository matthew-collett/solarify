package design.alberton.controller;

import design.alberton.controller.helper.ExcelHelper;
import design.alberton.model.AppModel;
import design.alberton.view.ResultView;
import design.alberton.view.SolarApp;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import static design.alberton.controller.helper.PathHelper.getDirectoryPath;
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
        model.setSeed(parseInt(seedField.getText()));

        final List<String> mapping = ExcelHelper.readFile(getDirectoryPath(getClass()).resolve("data.xlsx"));

        model.setMapping(mapping);

        mapping.forEach(System.out::println);

        app.setView(new ResultView(app));
    }
}
