package design.alberton;

import com.formdev.flatlaf.FlatIntelliJLaf;
import com.formdev.flatlaf.FlatLaf;
import design.alberton.view.SolarAppImpl;

import javax.swing.*;
import java.awt.*;
import java.io.PrintWriter;
import java.io.StringWriter;

import static java.lang.Thread.setDefaultUncaughtExceptionHandler;
import static javax.swing.JOptionPane.ERROR_MESSAGE;
import static javax.swing.JOptionPane.showMessageDialog;

public class Start {

    public static void main(String[] args) {
        setDefaultUncaughtExceptionHandler((t, e) -> handleException(e));
        createLookAndFeel();
        new SolarAppImpl().start();
    }

    public static void createLookAndFeel() {
        FlatIntelliJLaf.setup();
        FlatLaf.setUseNativeWindowDecorations(true);
        UIManager.put("TitlePane.unifiedBackground", false);
        UIManager.put("TitlePane.background", new Color(0xdddddd));
        UIManager.put("TextField.margin", new Insets(4, 8, 4, 8));
        UIManager.put("Button.margin", new Insets(4, 8, 4, 8));
        UIManager.put("PasswordField.showCapsLock", true);
        UIManager.put("PasswordField.showRevealButton", true);
        UIManager.put("Component.focusWidth", 0.8);
        UIManager.put("TextComponent.arc", 5);
        UIManager.put("Table.selectionBackground", UIManager.get("Table.background"));
        UIManager.put("Table.selectionForeground", UIManager.get("Table.foreground"));
    }

    private static void handleException(final Throwable error) {
        final StringWriter writer = new StringWriter();
        error.printStackTrace(new PrintWriter(writer));

        final JTextArea message = new JTextArea(writer.toString());
        message.setEditable(false);

        final JScrollPane errorPane = new JScrollPane(message);
        errorPane.setPreferredSize(new Dimension(450, 300));
        errorPane.setFocusable(false);

        showMessageDialog(null, errorPane, "Error Encountered", ERROR_MESSAGE);
    }
}
