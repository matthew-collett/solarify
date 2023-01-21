package design.alberton.view.components;

import com.formdev.flatlaf.extras.FlatSVGIcon;

import javax.swing.*;
import java.awt.*;

import static java.awt.Font.BOLD;
import static java.awt.Font.SANS_SERIF;
import static java.lang.String.format;
import static javax.swing.Box.createHorizontalGlue;
import static javax.swing.Box.createVerticalStrut;
import static javax.swing.BoxLayout.X_AXIS;
import static javax.swing.BoxLayout.Y_AXIS;

public class HeaderPanel extends JPanel {

    public HeaderPanel(final String title) {

        setLayout(new BoxLayout(this, Y_AXIS));

        final JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new BoxLayout(contentPanel, X_AXIS));

        final JLabel icon = new JLabel();
        icon.setIcon(new FlatSVGIcon("assets/logo.svg", 150, 150));
        contentPanel.add(icon);
        contentPanel.add(createHorizontalGlue());

        final JLabel text = new JLabel(format("Solarify - %s", title));
        text.setFont(new Font(SANS_SERIF, BOLD, 14));
        contentPanel.add(text);

        add(contentPanel);
        add(createVerticalStrut(16));
    }
}
