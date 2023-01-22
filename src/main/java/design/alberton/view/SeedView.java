package design.alberton.view;

import design.alberton.controller.SeedAction;
import design.alberton.view.components.HeaderPanel;

import javax.swing.*;
import java.awt.*;
import java.util.Random;

import static java.awt.BorderLayout.NORTH;
import static java.awt.BorderLayout.SOUTH;
import static java.lang.String.format;
import static javax.swing.SwingConstants.CENTER;

public class SeedView extends JPanel {

    public SeedView(final SolarApp app) {

        setLayout(new BorderLayout(0, 32));

        add(new HeaderPanel("Solar Energy Cost Optimization"), NORTH);

        final JPanel seedPanel = new JPanel();
        seedPanel.setLayout(new GridLayout(2, 1));

        final JLabel seedLabel = new JLabel("Seed");
        seedPanel.add(seedLabel);

        final JTextField seedField = new JTextField();
        seedField.putClientProperty("JTextField.placeholderText", format("Ex: %d", new Random().nextInt(1000) + 1));
        seedPanel.add(seedField);

        add(seedPanel, CENTER);

        final JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BorderLayout());

        final JButton seedButton = new JButton("Submit");
        seedButton.addActionListener(new SeedAction(app, seedField));
        app.getRootPane().setDefaultButton(seedButton);
        buttonPanel.add(seedButton, CENTER);

        final JLabel errLabel = new JLabel();
        errLabel.setHorizontalAlignment(CENTER);
        buttonPanel.add(errLabel, SOUTH);

        add(buttonPanel, SOUTH);
    }

    @Override
    public Insets getInsets() {
        return new Insets(16, 32, 32, 32);
    }
}


