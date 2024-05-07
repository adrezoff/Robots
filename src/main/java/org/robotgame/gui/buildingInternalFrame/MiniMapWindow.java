package org.robotgame.gui.buildingInternalFrame;

import org.robotgame.controller.Minimap;
import org.robotgame.controller.GameVisualizer;
import org.robotgame.gui.LocalizationManager;
import org.robotgame.serialization.State;

import javax.swing.*;
import java.awt.*;
import java.beans.PropertyVetoException;

public class MiniMapWindow extends AbstractWindow{
    private final Minimap m_minimap;
    public MiniMapWindow(AbstractWindow gameWindow) {
        super(LocalizationManager.getString("minimap.title"), false, true, false, false);
        m_minimap = new Minimap(gameWindow.get_visualizer());
        JPanel panel = new JPanel(new BorderLayout());
        panel.add(m_minimap, BorderLayout.CENTER);
        getContentPane().add(panel);
        pack();
    }

    @Override
    protected void closeWindow() {
    }
    /**
     * Обновление окошка MiniMapWindow.
     */
    @Override
    public void updateLabels() {
        setTitle(LocalizationManager.getString("minimap.title"));
        revalidate();
        repaint();
    }

    @Override
    public GameVisualizer get_visualizer() {
        return null;
    }

    /**
     * Получить состояние окошка MiniMapWindow.
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
            final boolean hidden = isIcon();
            state.setProperty("hidden", hidden);
        }

        return state;
    }

    /**
     * Уникальное имя окна.
     */
    @Override
    public String getName() {
        return "MiniMapWindow";
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

        if ((boolean)state.getProperty("hidden")) {
            try {
                setIcon(true);
            } catch (PropertyVetoException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
