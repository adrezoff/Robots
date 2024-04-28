package org.robotgame.gui.buildingInternalFrame;

import java.awt.*;
import java.beans.PropertyVetoException;

import javax.swing.JPanel;

import org.robotgame.controller.GameVisualizer;
import org.robotgame.serialization.State;
import org.robotgame.gui.LocalizationManager;
import org.robotgame.log.LogChangeListener;
import org.robotgame.log.LogEntry;
import org.robotgame.log.LogWindowSource;

public class LogWindow extends AbstractWindow implements LogChangeListener {
    private LogWindowSource m_logSource;
    private TextArea m_logContent;

    public LogWindow(LogWindowSource logSource)
    {
        super(LocalizationManager.getString("logWindow.theProtocolOfWork"), true, true, true, true);
        m_logSource = logSource;
        m_logSource.registerListener(this);
        m_logContent = new TextArea("");
        m_logContent.setSize(200, 500);

        JPanel panel = new JPanel(new BorderLayout());
        panel.add(m_logContent, BorderLayout.CENTER);
        getContentPane().add(panel);
        pack();
        updateLogContent();
    }

    private void updateLogContent()
    {
        StringBuilder content = new StringBuilder();
        for (LogEntry entry : m_logSource.all())
        {
            content.append(entry.getMessage()).append("\n");
        }
        m_logContent.setText(content.toString());
        m_logContent.invalidate();
    }

    @Override
    public void onLogChanged()
    {
        EventQueue.invokeLater(this::updateLogContent);
    }

    @Override
    public void closeWindow() {
        m_logSource.unregisterListener(this);
    }
    @Override
    public void updateLabels() {
        setTitle(LocalizationManager.getString("logWindow.theProtocolOfWork"));
        revalidate();
        repaint();
    }

    @Override
    public GameVisualizer get_visualizer() {
        return null;
    }

    /**
     * Получить состояние окошка LogWindow.
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
        return "LogWindow";
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

