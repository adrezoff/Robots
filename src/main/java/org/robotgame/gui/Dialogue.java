package org.robotgame.gui;

import org.robotgame.serialization.FileStateLoader;

import javax.swing.*;
import java.util.Objects;

public class Dialogue {
    public static int whatProfile(){
        Object[] options = {LocalizationManager.getString("profile.saved"),
                LocalizationManager.getString("profile.create")};
        return JOptionPane.showOptionDialog(null,
                    LocalizationManager.getString("window.closing.message"),
                    LocalizationManager.getString("confirmation"),
                    JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE,
                    null, options, options[0]);
    }
    public static String selectingProfile() {
        Object[] profiles = FileStateLoader.arrayNamesProfiles();
        if (profiles.length != 0) {
            int result2 = JOptionPane.showOptionDialog(null,
                    LocalizationManager.getString("profile.select"),
                    LocalizationManager.getString("profile.profiles"),
                    JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE,
                    null, profiles, profiles[0]);
            if (result2 != JOptionPane.CLOSED_OPTION) {
                return profiles[result2].toString();
            } else {
                return null;
            }
        }
        else {
            JOptionPane.showMessageDialog(null, LocalizationManager.getString("profile.noSavedProfiles"));
            return null;
        }

    }

    public static String createProfile() {
        Object[] options2 = {LocalizationManager.getString("ok")};
        JOptionPane pane = new JOptionPane(LocalizationManager.getString("profile.enterName"),
                JOptionPane.PLAIN_MESSAGE, JOptionPane.DEFAULT_OPTION,
                null, options2);

        pane.setWantsInput(true);
        JDialog dialog = pane.createDialog(null);

        pane.selectInitialValue();
        dialog.show();
        dialog.dispose();

        Object result2 = pane.getInputValue();

        if (result2 == JOptionPane.UNINITIALIZED_VALUE) {
            return null;
        }
        else if (Objects.equals(result2.toString(), "")){
            return createProfile();
        }
        else{
            return result2.toString();
        }
    }
}
