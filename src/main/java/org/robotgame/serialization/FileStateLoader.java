package org.robotgame.serialization;

import org.json.JSONObject;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.*;

/**
 * Класс для загрузки состояния из файла.
 * Реализует интерфейс StateLoader.
 */
public class FileStateLoader extends StateLoader {
    private static final String STORAGE_DIRECTORY = System.getProperty("user.home");
    private static final List<String> listNamesProfiles = new ArrayList<>();
    private final File storeFile;

    static {
        File storageDir = new File(STORAGE_DIRECTORY);
        if (storageDir.exists() && storageDir.isDirectory()) {
            File[] arr = storageDir.listFiles((dir, name) -> name.endsWith(".temp"));
            if (arr != null) {
                for (File fileProfile : arr) {
                    listNamesProfiles.add(fileProfile.getName().replaceFirst("\\.temp$", ""));
                }
            }
        }
    }

    /**
     * Конструктор по умолчанию.
     * Определяет путь к файлу хранения состояния.
     */
    public FileStateLoader(String id) {
        storeFile = new File(STORAGE_DIRECTORY + "/" + id + ".temp");
    }

    /**
     * Загружает состояние из файла.
     *
     * @return карта состояний объектов
     */
    @Override
    public Map<String, State> load() {
        if (!storeFile.exists() || !storeFile.isFile()) {
            System.out.println("Файл не найден: " + storeFile.getAbsolutePath());
            System.out.println("Загружен профиль по умолчанию");
            return null;
        }

        try (FileInputStream fis = new FileInputStream(storeFile)) {
            byte[] buffer = new byte[(int) storeFile.length()];
            fis.read(buffer);
            String storeData = new String(buffer, StandardCharsets.UTF_8);

            JSONObject jsonData = new JSONObject(storeData);
            Map<String, State> states = new HashMap<>();
            JSONObject jsonStates = jsonData.getJSONObject("states");

            for (String key : jsonStates.keySet()) {
                JSONObject jsonState = jsonStates.getJSONObject(key);
                states.put(key, new State(jsonState.toMap()));
            }

            return states;
        } catch (FileNotFoundException e) {
            System.out.println("Файл не найден: " + e.getMessage());
            System.out.println("Загружен профиль по умолчанию");
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static Object[] arrayNamesProfiles() {
        return listNamesProfiles.toArray();
    }
}
