package org.robotgame.serialization;

import org.json.JSONObject;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.*;


/**
 * Класс для загрузки состояния из файла.
 * Реализует интерфейс StateLoader.
 */
public class FileStateLoader extends StateLoader {
    private static final String STORAGE_FILE_PATH = "profiles";

    private static final List<String>  listNamesProfiles;

    private final File storeFile;

    static {
        listNamesProfiles = new ArrayList<>();
        URL url = FileStateLoader.class.getClassLoader().getResource(STORAGE_FILE_PATH);
        if (url != null) {
            File[] arr = new File(url.getFile()).listFiles();
            assert arr != null;
            for (File fileProfile : arr) {
                listNamesProfiles.add(fileProfile.getName().replaceFirst(".temp", ""));
            }
        }
    }
    /**
     * Конструктор по умолчанию.
     * Определяет путь к файлу хранения состояния.
     */
    public FileStateLoader(String id) {
        String projectDir = new File("src/main/resources").getAbsolutePath();
        storeFile = new File(projectDir, STORAGE_FILE_PATH+"/"+id+".temp");
    }
    /**
     * Загружает состояние из файла.
     *
     * @return карта состояний объектов
     */
    public Map<String, State> load() {
        storeFile.setReadable(true);

        try (FileInputStream fis = new FileInputStream(storeFile)) {
            byte[] buffer = new byte[(int) storeFile.length()];
            fis.read(buffer);
            String storeData = new String(buffer, "UTF-8");

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
