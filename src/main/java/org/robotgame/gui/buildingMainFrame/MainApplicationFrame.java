package org.robotgame.gui.buildingMainFrame;

import org.robotgame.gui.LocalizationManager;
import org.robotgame.gui.buildingInternalFrame.AbstractWindow;
import org.robotgame.gui.buildingInternalFrame.GameWindow;
import org.robotgame.gui.buildingInternalFrame.LogWindow;
import org.robotgame.log.Logger;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class MainApplicationFrame extends JFrame {
    private final JDesktopPane desktopPane = new JDesktopPane();
    private AbstractWindow logWindow = createLogWindow();
    private AbstractWindow gameWindow = createGameWindow();

    public MainApplicationFrame() {
        int inset = 50;
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        setBounds(inset, inset, screenSize.width - inset * 2, screenSize.height - inset * 2);

        setContentPane(desktopPane);

        addWindow(logWindow);
        addWindow(gameWindow);

        setJMenuBar(MenuBarBuilder.buildMenuBar());

        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                int result = JOptionPane.showConfirmDialog(MainApplicationFrame.this,
                        LocalizationManager.getString("window.closing.message"),
                        LocalizationManager.getString("confirmation"),
                        JOptionPane.YES_NO_OPTION);
                if (result == JOptionPane.YES_OPTION) {
                    closeAllWindows();
                    dispose();
                    System.exit(0);
                }
            }
        });
    }

    protected AbstractWindow createLogWindow() {
        AbstractWindow logWindow = new LogWindow(Logger.getDefaultLogSource());
        logWindow.setLocation(10, 10);
        logWindow.setSize(300, 800);
        logWindow.pack();
        Logger.debug(LocalizationManager.getString("logger.logsAreRunning"));
        return logWindow;
    }

    protected AbstractWindow createGameWindow() {
        AbstractWindow gameWindow = new GameWindow();
        gameWindow.setSize(500, 500);
        return gameWindow;
    }

    protected void addWindow(JInternalFrame frame) {
        desktopPane.add(frame);
        frame.setVisible(true);
    }

    private void closeAllWindows() {
        for (JInternalFrame frame : desktopPane.getAllFrames()) {
            if (frame instanceof AbstractWindow) {
                frame.dispose();
            }
        }
    }
}