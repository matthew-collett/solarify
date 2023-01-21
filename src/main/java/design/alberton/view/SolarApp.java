package design.alberton.view;

import design.alberton.model.AppModel;

import javax.swing.*;

public abstract class SolarApp extends JFrame {

    public SolarApp(final String title) {
        super(title);
    }

    public abstract void setView(final JPanel view);

    public abstract AppModel getModel();
}
