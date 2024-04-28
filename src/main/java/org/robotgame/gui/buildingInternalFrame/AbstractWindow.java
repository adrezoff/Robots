package org.robotgame.gui.buildingInternalFrame;

import org.robotgame.controller.GameVisualizer;
import org.robotgame.gui.LocalizationManager;
import org.robotgame.serialization.Saveable;

import javax.swing.*;
import javax.swing.event.InternalFrameAdapter;
import javax.swing.event.InternalFrameEvent;

public abstract class AbstractWindow extends JInternalFrame implements Saveable {

    public AbstractWindow(String title, boolean resizable, boolean closable, boolean maximizable, boolean iconifiable) {
        super(title, resizable, closable, maximizable, iconifiable);
        setDefaultCloseOperation(JInternalFrame.DO_NOTHING_ON_CLOSE);

        addInternalFrameListener(new InternalFrameAdapter() {
            @Override
            public void internalFrameClosing(InternalFrameEvent e) {
                Object[] options = {LocalizationManager.getString("yes"),
                        LocalizationManager.getString("no")};

                int result = JOptionPane.showOptionDialog(AbstractWindow.this,
                        LocalizationManager.getString("window.closing.message"),
                        LocalizationManager.getString("confirmation"),
                        JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE,
                        null, options, options[0]);
                if (result == JOptionPane.YES_OPTION) {
                    dispose();
                }
            }
        });
    }

    protected abstract void closeWindow();

    public abstract GameVisualizer get_visualizer();

    @Override
    public void dispose() {
        closeWindow();
        super.dispose();
    }

    public abstract void updateLabels();
}