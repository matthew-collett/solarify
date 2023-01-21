package design.alberton.view;

import design.alberton.view.components.HeaderPanel;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.Random;

import static java.awt.BorderLayout.*;
import static java.lang.String.format;
import static java.util.stream.IntStream.range;

public class ResultView extends JPanel {

    public ResultView(final SolarApp app) {

        final List<String> mapping = app.getModel().getMapping();

        setLayout(new BorderLayout(0, 16));

        add(new HeaderPanel("Solar Energy Cost Optimization"), NORTH);

        final JPanel infoPanel = new JPanel();
        infoPanel.setLayout(new GridLayout(2, 3, 0, 16));
        final JLabel costLabel = new JLabel("Total Cost: ");
        infoPanel.add(costLabel);

        final JLabel panelsLabel = new JLabel(format("Solar Panels Used: %d", mapping.size() + 1));
        infoPanel.add(panelsLabel);

        final JLabel sLegendLabel = new JLabel("x - Solar Panels");
        infoPanel.add(sLegendLabel);

        final JLabel percentLabel = new JLabel("Percentage of Green Energy: 100%");
        infoPanel.add(percentLabel);

        final JLabel batteryLabel = new JLabel("Batteries Used: 9");
        infoPanel.add(batteryLabel);

        final JLabel bLegendLabel = new JLabel("o - Batteries");
        infoPanel.add(bLegendLabel);

        add(infoPanel, CENTER);

        final JTable solarTable = new JTable(64, 64);
        solarTable.setShowGrid(true);
        solarTable.setEnabled(false);
        solarTable.setTableHeader(null);
        solarTable.setBackground(new Color(0xfaf9f6));
        solarTable.setRowHeight(9);
        range(0, solarTable.getColumnModel().getColumnCount()).forEach(i -> solarTable.getColumnModel().getColumn(i).setMaxWidth(2));


        int i = 0;
        while (i < 9) {
            final Random rand = new Random();
            int x = rand.nextInt(63) + 1;
            int y = rand.nextInt(63) + 1;

            if (solarTable.getValueAt(x, y) == null) {
                solarTable.setValueAt('o', x, y);
                i++;
            }

        }

        add(solarTable, SOUTH);
    }

    @Override
    public Insets getInsets() {
        return new Insets(16, 32, 32, 32);
    }
}
