package design.alberton.view;

import design.alberton.view.components.HeaderPanel;

import javax.swing.*;
import java.awt.*;

import static java.awt.BorderLayout.CENTER;
import static java.awt.BorderLayout.NORTH;
import static java.util.stream.IntStream.range;

public class ResultView extends JPanel {

    public ResultView(final SolarApp app) {
        setLayout(new BorderLayout(0, 32));

        add(new HeaderPanel("Solar Energy Cost Optimization"), NORTH);

        final JTable solarTable = new JTable(64, 64);
        solarTable.setShowGrid(true);
        solarTable.setEnabled(false);
        solarTable.setTableHeader(null);
        solarTable.setBackground(new Color(0xfaf9f6));
        solarTable.setRowHeight(9);
        range(0, solarTable.getColumnModel().getColumnCount()).forEach(i -> solarTable.getColumnModel().getColumn(i).setMaxWidth(2));
        System.out.println(solarTable.getColumnCount());
        add(solarTable, CENTER);
    }

    @Override
    public Insets getInsets() {
        return new Insets(16, 32, 32, 32);
    }
}
