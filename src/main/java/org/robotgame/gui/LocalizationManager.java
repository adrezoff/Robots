package org.robotgame.gui;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Locale;
import java.util.Objects;
import java.util.Properties;
import java.util.ResourceBundle;

public class LocalizationManager {
    private static ResourceBundle messages;
    private static final String PROPERTIES_FILE_PATH = "config.properties";
    private static final String BUNDLE_BASE_NAME = "messages";
    private static final Object lock = new Object();


    static {
        loadResourceBundle();
    }

    private static void loadResourceBundle() {
        Properties props = new Properties();
        try {
            synchronized (lock) {
                InputStream in = LocalizationManager.class.getClassLoader().getResourceAsStream(PROPERTIES_FILE_PATH);
                props.load(in);
                assert in != null;
                in.close();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        Locale locale = switch (props.getProperty("Language")) {
            case "EN" -> Locale.ENGLISH;
            case "RU" -> new Locale("ru");
            default -> Locale.getDefault();
        };
        messages = ResourceBundle.getBundle(BUNDLE_BASE_NAME, locale);
    }

    public static String getString(String key) {
        return messages.getString(key);
    }

    public static synchronized boolean changeLanguage(String language) {
        Properties props = new Properties();
        try (FileOutputStream out = new FileOutputStream(Objects.requireNonNull(LocalizationManager.class.getClassLoader().getResource(PROPERTIES_FILE_PATH)).getFile());
             InputStream in = LocalizationManager.class.getClassLoader().getResourceAsStream(PROPERTIES_FILE_PATH)) {
            synchronized (lock) {
                props.load(in);
                props.setProperty("Language", language);
                props.store(out, null);
            }
            loadResourceBundle();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
}