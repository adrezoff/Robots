package org.robotgame.gui.buildingMainFrame;

import org.robotgame.gui.LocalizationManager;
import org.robotgame.log.Logger;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.Dimension;

public class MenuBarBuilder {
    public static JMenuBar buildMenuBar(MainApplicationFrame desktop) {

        JMenuBar menuBar = new JMenuBar();

        JMenu lookAndFeelMenu = buildLookAndFeelMenu(menuBar);
        JMenu languageMenu = buildLanguageMenu(desktop);
        JMenu testMenu = buildTestMenu();
        JMenuItem exitMenuItem = buildExitMenuItem(desktop);

        menuBar.add(lookAndFeelMenu);
        menuBar.add(languageMenu);
        menuBar.add(testMenu);
        menuBar.add(exitMenuItem);

        return menuBar;
    }

    private static JMenu buildLookAndFeelMenu(JMenuBar menuBar) {
        JMenu lookAndFeelMenu = new JMenu(LocalizationManager.getString("menu.view.lookAndFeel"));
        lookAndFeelMenu.setMnemonic(KeyEvent.VK_V);
        lookAndFeelMenu.getAccessibleContext().setAccessibleDescription(
                "Управление режимом отображения приложения");

        JMenuItem systemLookAndFeel = new JMenuItem(LocalizationManager.getString("menu.view.systemLookAndFeel"), KeyEvent.VK_S);
        systemLookAndFeel.addActionListener((event) -> {
            setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            SwingUtilities.updateComponentTreeUI(SwingUtilities.getRootPane(menuBar));
        });
        lookAndFeelMenu.add(systemLookAndFeel);

        JMenuItem crossplatformLookAndFeel = new JMenuItem(LocalizationManager.getString("menu.view.crossplatformLookAndFeel"), KeyEvent.VK_S);
        crossplatformLookAndFeel.addActionListener((event) -> {
            setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
            SwingUtilities.updateComponentTreeUI(SwingUtilities.getRootPane(menuBar));
        });
        lookAndFeelMenu.add(crossplatformLookAndFeel);

        return lookAndFeelMenu;
    }

    private static JMenu buildTestMenu() {
        JMenu testMenu = new JMenu(LocalizationManager.getString("menu.tests"));
        testMenu.setMnemonic(KeyEvent.VK_T);
        testMenu.getAccessibleContext().setAccessibleDescription(
                "Тестовые команды");

        JMenuItem addLogMessageItem = new JMenuItem(LocalizationManager.getString("menu.tests.messageInTheLog"), KeyEvent.VK_S);
        addLogMessageItem.addActionListener((event) -> {
            Logger.debug(LocalizationManager.getString("logger.newLine"));
        });
        testMenu.add(addLogMessageItem);

        return testMenu;
    }

    private static JMenuItem buildExitMenuItem(MainApplicationFrame desktop) {
        JMenuItem exitMenuItem = new JMenuItem(LocalizationManager.getString("menu.exit"));
        exitMenuItem.setMnemonic(KeyEvent.VK_T);

        exitMenuItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Object[] options = {LocalizationManager.getString("yes"),
                        LocalizationManager.getString("no")};

                int result = JOptionPane.showOptionDialog(null,
                        LocalizationManager.getString("window.closing.message"),
                        LocalizationManager.getString("confirmation"),
                        JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE,
                        null, options, options[0]);
                if (result == JOptionPane.YES_OPTION) {
                    desktop.saving();
                    System.exit(0);
                }
            }
        });
        exitMenuItem.setPreferredSize(new Dimension(62, 20));
        exitMenuItem.setMaximumSize(new Dimension(62, 20));
        return exitMenuItem;
    }

    private static JMenu buildLanguageMenu(MainApplicationFrame desktop){
        JMenu languageMenu = new JMenu(LocalizationManager.getString("menu.language"));
        languageMenu.getAccessibleContext().setAccessibleDescription(
                "Настройки языка");

        JMenuItem languageRu = new JMenuItem("Русский", KeyEvent.VK_S);
        languageRu.addActionListener((event) -> {
            LocalizationManager.changeLanguage("RU");
            desktop.updateDesktopPane();
        });
        languageMenu.add(languageRu);

        JMenuItem languageEn = new JMenuItem("English", KeyEvent.VK_S);
        languageEn.addActionListener((event) -> {
            LocalizationManager.changeLanguage("EN");
            desktop.updateDesktopPane();
        });
        languageMenu.add(languageEn);

        JMenuItem languageDefault = new JMenuItem(LocalizationManager.getString("menu.language.default"), KeyEvent.VK_S);
        languageDefault.addActionListener((event) -> {
            LocalizationManager.changeLanguage("");
            desktop.updateDesktopPane();
        });
        languageMenu.add(languageDefault);

        return languageMenu;
    }

    private static void setLookAndFeel(String className) {
        try {
            UIManager.setLookAndFeel(className);
        } catch (ClassNotFoundException | InstantiationException
                 | IllegalAccessException | UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }
    }
}
