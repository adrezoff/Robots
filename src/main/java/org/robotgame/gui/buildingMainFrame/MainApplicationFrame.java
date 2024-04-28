package org.robotgame.gui.buildingMainFrame;

import org.robotgame.gui.LocalizationManager;
import org.robotgame.gui.buildingInternalFrame.AbstractWindow;
import org.robotgame.gui.buildingInternalFrame.GameWindow;
import org.robotgame.gui.buildingInternalFrame.LogWindow;
import org.robotgame.gui.buildingInternalFrame.MiniMapWindow;
import org.robotgame.log.Logger;
import org.robotgame.serialization.FileStateLoader;
import org.robotgame.serialization.Saveable;
import org.robotgame.serialization.State;
import org.robotgame.serialization.StateSaver;
import org.robotgame.serialization.FileStateSaver;
import org.robotgame.serialization.StateLoader;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.*;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class MainApplicationFrame extends JFrame implements Saveable {
    private JDesktopPane desktopPane;
    private AbstractWindow logWindow;
    private AbstractWindow gameWindow;
    private AbstractWindow minimapWindow;


    public MainApplicationFrame() {

        desktopPane = new JDesktopPane();

        logWindow = createLogWindow();
        gameWindow = createGameWindow();
        minimapWindow = createMiniMapWindow();

        setContentPane(desktopPane);

        addWindow(logWindow);
        addWindow(gameWindow);
        addWindow(minimapWindow);

        setJMenuBar(MenuBarBuilder.buildMenuBar(this));

        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                Object[] options = {LocalizationManager.getString("yes"),
                        LocalizationManager.getString("no")};

                int result = JOptionPane.showOptionDialog(null,
                        LocalizationManager.getString("window.closing.message"),
                        LocalizationManager.getString("confirmation"),
                        JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE,
                        null, options, options[0]);
                if (result == JOptionPane.YES_OPTION) {
                    final StateSaver saver = new FileStateSaver();
                    saver.save(getAllToSave());
                    closeAllWindows();

                    dispose();
                    System.exit(0);
                }
            }
        });
        setStates();
    }

    protected AbstractWindow createLogWindow() {
        AbstractWindow logWindow = new LogWindow(Logger.getDefaultLogSource());
        logWindow.setLocation(10, 10);
        logWindow.setSize(300, 800);
        Logger.debug(LocalizationManager.getString("logger.logsAreRunning"));
        return logWindow;
    }

    protected AbstractWindow createGameWindow() {
        int gameWindowWidth = 600;
        int gameWindowHeight = 600;
        AbstractWindow gameWindow = new GameWindow(gameWindowWidth, gameWindowHeight);
        gameWindow.setSize(gameWindowWidth, gameWindowHeight);
        gameWindow.setLocation(320, 10);
        return gameWindow;
    }

    protected AbstractWindow createMiniMapWindow() {
        int minimapWindowWidth = 200;
        int minimapWindowHeight = 200;
        AbstractWindow minimapWindow = new MiniMapWindow(this.gameWindow);
        minimapWindow.setSize(minimapWindowWidth, minimapWindowHeight);
        minimapWindow.setLocation(930, 10);
        return minimapWindow;
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

    public void updateDesktopPane() {
        for (JInternalFrame frame : desktopPane.getAllFrames()) {
            if (frame instanceof AbstractWindow) {
                ((AbstractWindow) frame).updateLabels();
            }
        }
        updateMainFrame();
    }

    public void updateMainFrame() {
        setJMenuBar(MenuBarBuilder.buildMenuBar(this));
        desktopPane.revalidate();
        desktopPane.repaint();
    }

    public void restoreAllWindows() {
    }
    @Serial
    private void writeObject(ObjectOutputStream out) throws IOException {
        out.defaultWriteObject();
    }

    @Serial
    private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
        in.defaultReadObject();
        desktopPane = new JDesktopPane();

        logWindow = createLogWindow();
        gameWindow = createGameWindow();
        minimapWindow = createMiniMapWindow();
    }

    /**
     * Получить состояние окошка MainApplicationFrame.
     */
    @Override
    public State state() {
        final State state = new State();

        {
            state.setProperty("name", getName());
        }

        {
            final Point location = getLocation();
            state.setProperty("X", location.x);
            state.setProperty("Y", location.y);
        }

        {
            final Dimension dimension = getSize();
            state.setProperty("width", dimension.width);
            state.setProperty("height", dimension.height);
        }

        {
            final boolean hidden = (JFrame.ICONIFIED == getState());
            state.setProperty("hidden", hidden);
        }

        return state;
    }

    /**
     * Уникальное имя окошка.
     */
    @Override
    public String getName() {
        return "MainApplicationFrame";
    }

    /**
     * Восстановить состояния объекта по переданному состоянию.
     */
    @Override
    public void setState(State state) {
        if (null == state) {
            return;
        }

        setSize(
                (int)state.getProperty("width"),
                (int)state.getProperty("height"));

        setLocation(
                (int)state.getProperty("X"),
                (int)state.getProperty("Y"));
    }

    private void setStates() {
        final StateLoader loader = new FileStateLoader();
        final Map<String, State> states = loader.load();

        if (null == states) {
            return;
        }

        for (final JInternalFrame frame : desktopPane.getAllFrames()) {
            if (frame instanceof Saveable) {
                final Saveable saveable = (Saveable)frame;
                final String name = saveable.getName();

                if (states.containsKey(name)) {
                    saveable.setState(states.get(name));
                }
            }
        }

        final String name = getName();

        if (states.containsKey(name)) {
            setState(states.get(name));
        }
    }
    java.util.List<Saveable> getAllToSave() {
        List<Saveable> objectsToSave = new LinkedList<>();
        for (final var frame : desktopPane.getAllFrames()) {
            if (frame instanceof Saveable) {
                objectsToSave.add((Saveable)frame);
            }
        }
        objectsToSave.add(this);
        return objectsToSave;
    }
}
