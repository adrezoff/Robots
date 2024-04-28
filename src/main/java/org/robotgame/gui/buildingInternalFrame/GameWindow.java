package org.robotgame.gui.buildingInternalFrame;


import org.robotgame.gui.LocalizationManager;
import org.robotgame.controller.GameVisualizer;
import org.robotgame.serialization.State;

import java.awt.*;
import java.beans.PropertyVetoException;

import javax.swing.JPanel;

public class GameWindow extends AbstractWindow
{
    private final GameVisualizer m_visualizer;

    public GameWindow(int width, int height) {
        super(LocalizationManager.getString("gameWindow.thePlayingField"), true, true, true, true);

        m_visualizer = new GameVisualizer(width, height);
        JPanel panel = new JPanel(new BorderLayout());
        panel.add(m_visualizer, BorderLayout.CENTER);
        getContentPane().add(panel);
        pack();
        getName();
    }

    @Override
    protected void closeWindow() {
    }

    public void updateLabels() {
        setTitle(LocalizationManager.getString("gameWindow.thePlayingField"));
        revalidate();
        repaint();
    }
    public GameVisualizer get_visualizer(){
        return m_visualizer;
    }


    /**
     * Получить состояние окошка GameWindow.
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
        return "GameWindow";
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
